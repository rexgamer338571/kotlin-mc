package dev.ng5m.world

import dev.ng5m.block.Block
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.util.bitsToRepresent
import net.kyori.adventure.key.Key
import java.util.*
import kotlin.math.abs
import kotlin.math.max

class ChunkSection {
    companion object {
        private const val INDIRECT_MIN_BLOCKS = 4
        private const val INDIRECT_MAX_BLOCKS = 8
        private const val INDIRECT_MIN_BIOMES = 1
        private const val INDIRECT_MAX_BIOMES = 3
        private const val DIRECT_BLOCKS = 15
        private const val DIRECT_BIOMES = 6

        val nonNonAirBlocks: Collection<ResourceKey<Block>> =
            Registries.BLOCK.tags[Key.key("non_non_air")]!!
        private val nonNonAirBlocksRaw: Collection<Int> =
            nonNonAirBlocks.map { Registries.BLOCK.idByKey(it) }.toHashSet()

        var times = 0
        var totalTime: Long = 0

        val CODEC: Codec<ChunkSection> = Codec.of(
            { buf ->
                TODO()
            },
            { buf, section ->
                val start = System.nanoTime()

                buf.writeShort(section.calculateBlockCount())

                section.blocks.write(buf)
                section.biomes.write(buf)

                totalTime += (System.nanoTime() - start)
                times++
            }
        )

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

        fun unpackDataArray(size: Int, data: LongArray, bitsPerEntry: Int): IntArray {
            val entriesPerLong = 64 / bitsPerEntry
            return IntArray(size) { index ->
                val longIndex = index / entriesPerLong
                val bitOffset = (index - longIndex * entriesPerLong) * bitsPerEntry

                ((data[longIndex] shr bitOffset) and ((1L shl bitsPerEntry) - 1L)).toInt()
            }
        }

        fun packDataArray(size: Int, input: IntArray, bitsPerEntry: Int): LongArray {
            val entriesPerLong = 64 / bitsPerEntry
            val longs = (size + entriesPerLong - 1) / entriesPerLong
            val array = LongArray(longs)

            val mask = (1L shl bitsPerEntry) - 1

            for (index in input.indices) {
                val longIndex = index / entriesPerLong
                val bitOffset = (index - longIndex * entriesPerLong) * bitsPerEntry
                val block = array[longIndex]
                val clear = (1L shl bitsPerEntry) - 1L
                array[longIndex] = block and (clear shl bitOffset).inv() or (input[index].toLong() shl bitOffset)
            }

            return array
        }


    }

    internal var blocks = PalettedContainer(16 * 16 * 16, 4, 8, 15)
    internal var biomes = PalettedContainer(4 * 4 * 4, 1, 3, 6)

    fun setBlock(x: Int, y: Int, z: Int, value: Int) {
        val index = getBlockIndex(x, y, z)
        if (blocks.get(index) != value) {
            blocks.set(index, value)
        }
    }

    fun getBlock(x: Int, y: Int, z: Int): Int {
        return blocks[getBlockIndex(abs(x), y, abs(z))]
    }

    fun setBiome(x: Int, y: Int, z: Int, value: Int) {
        biomes.set(getBiomeIndex(x, y, z), value)
    }

    private fun calculateBlockCount(): Int {
        return 4096
    }
}