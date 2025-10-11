package dev.ng5m.world

import dev.ng5m.serialization.Codec
import dev.ng5m.util.Getter
import dev.ng5m.util.Setter
import dev.ng5m.util.bitsToRepresent
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
import it.unimi.dsi.fastutil.longs.LongArrayList
import kotlin.math.max

class Palette(
    val size: Int, var values: MutableList<Int>, @Volatile var data: LongArrayList,
    val minBits: Int, val maxBits: Int, val directBits: Int
) : Getter<Int, Int>, Setter<Int, Int> {


    companion object {
        fun read(size: Int, buf: ByteBuf, minBits: Int, maxBits: Int, directBits: Int): Palette {
            val palette = Codec.VARINT_LIST.read(buf)
            val data = Codec.LONG_ARRAY.read(buf)

            return Palette(size, palette, LongArrayList.wrap(data), minBits, maxBits, directBits)
        }
    }

    constructor(size: Int, values: MutableList<Int>, minBits: Int, maxBits: Int, directBits: Int) : this(
        size,
        values,
        LongArrayList(0),
        minBits,
        maxBits,
        directBits
    )

    constructor(
        size: Int,
        values: MutableList<Int>,
        data: LongArray,
        minBits: Int,
        maxBits: Int,
        directBits: Int
    ) : this(size, values, LongArrayList.wrap(data), minBits, maxBits, directBits)

    internal var bits = minBits

    private var entriesPerLong = 64 / bits
    private val reverse = Int2IntOpenHashMap()

    init {
        init(clampBits(bitsToRepresent(values.size - 1)))

        for (i in values.indices)
            reverse[values[i]] = i
    }

    private fun init(bits: Int) {
        this.bits = bits
        this.entriesPerLong = Long.SIZE_BITS / bits
        val longs = (size + entriesPerLong - 1) / entriesPerLong

        this.data = LongArrayList(longs)
        data.size(longs)
    }

    // max(minBits, bits > maxBits ? direct : bits)

    private fun clampBits(bits: Int): Int {
        return max(minBits, if (bits > maxBits) directBits else bits)
    }

    private fun grow(newBits: Int) {
        require(newBits > bits)

        val entries = IntArray(size) { index -> get(index) }

        init(newBits)

        for (i in 0 until size) {
            val entry = entries[i]
            set(i, entry)
        }
    }

    override fun set(index: Int, value: Int) {
        val toSet = if (bits == directBits) value else {
            var paletteIndex = reverse.getOrDefault(value, -1)
            if (paletteIndex == -1) {
                paletteIndex = values.size
                values.add(value)
                reverse[value] = paletteIndex
            }
            paletteIndex
        }

        val btr = clampBits(bitsToRepresent(toSet))
        if (btr > bits) grow(btr)

        val mask = (1L shl bits) - 1L
        val longIndex = index / entriesPerLong

        val block = this.data.getLong(longIndex)
        val offset: Int = (index - longIndex * entriesPerLong) * bits
        this.data[longIndex] = block and (mask shl offset).inv() or ((toSet.toLong() and mask) shl offset)
//        println(get(index))
    }

    override fun get(index: Int): Int {
        val mask = (1L shl bits) - 1L
        val longIndex = index / entriesPerLong
        val block = data.getLong(longIndex)
        val offset = (index - longIndex * entriesPerLong) * bits
        val value = (block shr offset and mask).toInt()

        return values[value]
    }

    fun write(buf: ByteBuf) {
        if (values.size == 1) {
            buf.writeByte(0)
            Codec.VARINT.write(buf, values[0])
            buf.writeByte(0)
        } else if (bits != directBits) {
            buf.writeByte(bits)
            Codec.VARINT_LIST.write(buf, values)
            Codec.LONG_ARRAY.write(buf, data.elements())
        } else {
            buf.writeByte(directBits)
            Codec.LONG_ARRAY.write(buf, data.elements())
        }

//        println(bits)
//        println(ChunkSection.unpackDataArray(size, data.elements(), bits).map { values[it] })
    }
}