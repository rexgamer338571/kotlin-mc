package dev.ng5m.world

import dev.ng5m.block.BlockState
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import io.netty.buffer.ByteBuf
import net.kyori.adventure.key.Key
import java.util.*
import kotlin.math.max

class ChunkSection {
    companion object {
        private const val INDIRECT_MIN_BLOCKS = 4
        private const val INDIRECT_MAX_BLOCKS = 8
        private const val INDIRECT_MIN_BIOMES = 1
        private const val INDIRECT_MAX_BIOMES = 3
        private const val DIRECT_BLOCKS = 15
        private const val DIRECT_BIOMES = 6

        private val nonNonAirBlocks: Collection<ResourceKey<BlockState>> =
            Registries.BLOCK.tags[Key.key("non_non_air")]!!
        private val nonNonAirBlocksRaw: Collection<Int> =
            nonNonAirBlocks.map { Registries.BLOCK.idByKey(it) }.toHashSet()

        var times = 0
        var totalTime: Long = 0

        val CODEC: Codec<ChunkSection> = Codec.of(
            { buf ->
                val section = ChunkSection()
                buf.readShort() // block count - unused

                fun readPalettedContainer(blocks: Boolean) {
                    when (val bpe = buf.readUnsignedByte().toInt()) {
                        0 -> {
                            val singleValue = Codec.VARINT.read(buf)
                            buf.readByte() // data array length - always zero in SingleValued
                            val array = if (blocks) section.blocks else section.biomes
                            array.fill(singleValue)
                        }

                        in (if (blocks) INDIRECT_MIN_BLOCKS else INDIRECT_MIN_BIOMES)..(if (blocks) INDIRECT_MAX_BLOCKS else INDIRECT_MAX_BIOMES) -> {
                            val palette = Codec.VARINT_LIST.read(buf)
                            val data = Codec.LONG_ARRAY.read(buf)
                            val array = if (blocks) section.blocks else section.biomes

                            val unpacked = unpackDataArray(blocks, data, bpe).map { palette[it] }.toIntArray()
                            unpacked.copyInto(array)
                        }

                        (if (blocks) DIRECT_BLOCKS else DIRECT_BIOMES) -> {
                            val data = Codec.LONG_ARRAY.read(buf)
                            val array = if (blocks) section.blocks else section.biomes

                            val unpacked = unpackDataArray(blocks, data, bpe)
                            unpacked.copyInto(array)
                        }
                    }
                }

                readPalettedContainer(true)
                readPalettedContainer(false)

                return@of section
            },
            { buf, section ->
                val start = System.nanoTime()

                buf.writeShort(section.calculateBlockCount())

                writePalettedContainer(buf, section, true)
                writePalettedContainer(buf, section, false)

                totalTime += (System.nanoTime() - start)
                times++
            }
        )



        private fun writePalettedContainer(buf: ByteBuf, section: ChunkSection, blocks: Boolean) {
            val array = if (blocks) section.blocks else section.biomes
            val bpe = section.bitsPerEntry(blocks)

            buf.writeByte(bpe)

            when (bpe) {
                0 -> {
                    Codec.VARINT.write(buf, array[0])
                    buf.writeByte(0)
                }
                in (if (blocks) INDIRECT_MIN_BLOCKS else INDIRECT_MIN_BIOMES)..(if (blocks) INDIRECT_MAX_BLOCKS else INDIRECT_MAX_BIOMES) -> {
                    val (unique, indexMap) = buildPaletteAndIndexMap(array)
                    Codec.VARINT_LIST.write(buf, unique)

                    val packedData = packDataArray2(blocks, array, bpe, indexMap)
                    Codec.LONG_ARRAY.write(buf, packedData)
                }
                if (blocks) DIRECT_BLOCKS else DIRECT_BIOMES -> {
                    Codec.LONG_ARRAY.write(buf, packDataArray2(blocks, array, bpe))
                }
            }
        }

        private fun buildPaletteAndIndexMap(array: IntArray): Pair<List<Int>, IntArray> {
            val unique = mutableListOf<Int>()
            val indexMap = IntArray(array.size)
            val valueToIndex = mutableMapOf<Int, Int>()

            for ((index, value) in array.withIndex()) {
                val paletteIndex = valueToIndex.getOrPut(value) {
                    unique.add(value)
                    unique.size - 1
                }
                indexMap[index] = paletteIndex
            }

            return Pair(unique, indexMap)
        }

        val NON_PREFIXED_LIST_CODEC: Codec<List<ChunkSection>> = Codec.of(
            { buf ->
                val list = mutableListOf<ChunkSection>()
                while (buf.readableBytes() > 0) {
                    list.add(CODEC.read(buf))
                }

                return@of list
            },
            { buf, list ->
                for (section in list) CODEC.write(buf, section)
            }
        )

        private fun getBlockIndex(x: Int, y: Int, z: Int): Int = x + (z shl 4) + (y shl 8)
        private fun getBiomeIndex(x: Int, y: Int, z: Int): Int = x + (z shl 2) + (y shl 4)

        fun unpackDataArray(blocks: Boolean, data: LongArray, bitsPerEntry: Int): IntArray {
            val size = if (blocks) 16 * 16 * 16 else 4 * 4 * 4
            val entriesPerLong = 64 / bitsPerEntry
            return IntArray(size) { index ->
                val longIndex = index / entriesPerLong
                val entryIndexInLong = index % entriesPerLong
                val bitOffset = entryIndexInLong * bitsPerEntry
                ((data[longIndex] shr bitOffset) and ((1L shl bitsPerEntry) - 1)).toInt()
            }
        }

        private fun packDataArray2(blocks: Boolean, input: IntArray, bitsPerEntry: Int,
                                   indexMap: IntArray? = null): LongArray {
            val size = input.size
            val entriesPerLong = 64 / bitsPerEntry
            val longs = (size + entriesPerLong - 1) / entriesPerLong
            val array = LongArray(longs)
            val mask = (1L shl bitsPerEntry) - 1

            for (index in input.indices) {
                val value = if (indexMap != null) indexMap[index] else input[index]
                val longIndex = index / entriesPerLong
                val bitOffset = (index % entriesPerLong) * bitsPerEntry
                array[longIndex] = array[longIndex] or ((value.toLong() and mask) shl bitOffset)
            }

            return array
        }

        private fun packDataArray(blocks: Boolean, input: IntArray, bitsPerEntry: Int): LongArray {
            val size = if (blocks) 16 * 16 * 16 else 4 * 4 * 4

            val entriesPerLong = 64 / bitsPerEntry
            val longs = (size + entriesPerLong - 1) / entriesPerLong
            val array = LongArray(longs)

            val mask = (1L shl bitsPerEntry) - 1

            for (index in input.indices) {
                val longIndex = index / entriesPerLong
                array[longIndex] = array[longIndex] or ((input[index].toLong() and mask) shl (index % entriesPerLong) * bitsPerEntry)
            }

            return array
        }

    }

    internal val blocks: IntArray = IntArray(16 * 16 * 16)
    internal val biomes: IntArray = IntArray(4 * 4 * 4)

    private var blockCountCache: Int? = null
    private var blocksUniqueCache: Int? = null
    private var blocksDirty = true

    fun setBlock(x: Int, y: Int, z: Int, value: Int) {
        val index = getBlockIndex(x, y, z)
        if (blocks[index] != value) {
            blocks[index] = value
            blocksDirty = true
        }
    }

    fun getBlock(x: Int, y: Int, z: Int): Int {
        return blocks[getBlockIndex(x, y, z)]
    }

    fun setBiome(x: Int, y: Int, z: Int, value: Int) {
        biomes[getBiomeIndex(x, y, z)] = value
    }

    private fun calculateBlockCount(): Int {
        if (!blocksDirty && blockCountCache != null) {
            return blockCountCache!!
        }

        var count = 0
        for (i in blocks.indices) {
            if (!nonNonAirBlocksRaw.contains(blocks[i])) {
                count++
            }
        }

        blockCountCache = count
        return count
    }

    private fun bitsPerEntry(isBlocks: Boolean): Int {
        if (!isBlocks) {
            val uniqueCount = unique(biomes)
            if (uniqueCount == 1) return 0

            val bpe = max(1, 32 - Integer.numberOfLeadingZeros(uniqueCount - 1))
            return when {
                bpe == DIRECT_BIOMES -> DIRECT_BIOMES
                bpe in INDIRECT_MIN_BIOMES..INDIRECT_MAX_BIOMES -> bpe
                bpe > INDIRECT_MAX_BIOMES -> DIRECT_BIOMES
                else -> INDIRECT_MIN_BIOMES
            }
        }

        if (!blocksDirty && blocksUniqueCache != null) {
            return calculateBpeFromUniqueCount(blocksUniqueCache!!, true)
        }

        val uniqueCount = unique(blocks)
        blocksUniqueCache = uniqueCount

        return calculateBpeFromUniqueCount(uniqueCount, true)
    }

    private fun unique(array: IntArray): Int {
        return Arrays.stream(array).distinct().count().toInt()
    }

    private fun calculateBpeFromUniqueCount(uniqueCount: Int, isBlocks: Boolean): Int {
        if (uniqueCount == 1) return 0

        val bpe = max(1, 32 - Integer.numberOfLeadingZeros(uniqueCount - 1))
        val (direct, indirectMin, indirectMax) = if (isBlocks) {
            Triple(DIRECT_BLOCKS, INDIRECT_MIN_BLOCKS, INDIRECT_MAX_BIOMES)
        } else {
            Triple(DIRECT_BIOMES, INDIRECT_MIN_BIOMES, INDIRECT_MAX_BIOMES)
        }

        return when {
            bpe == direct -> direct
            bpe in indirectMin..indirectMax -> bpe
            bpe > indirectMax -> direct
            else -> indirectMin
        }
    }

    fun markDirty() {
        blocksDirty = true
    }
}