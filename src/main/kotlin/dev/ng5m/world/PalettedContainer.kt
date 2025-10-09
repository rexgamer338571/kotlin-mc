package dev.ng5m.world

import dev.ng5m.util.Getter
import dev.ng5m.util.Setter
import io.netty.buffer.ByteBuf
import kotlin.math.min

class PalettedContainer(val size: Int, minBits: Int, maxBits: Int, directBits: Int, defaultValue: Int = 0) : Getter<Int, Int>, Setter<Int, Int> {
    internal var palette: Palette = Palette(size, mutableListOf(defaultValue), minBits, maxBits, directBits)

    override fun set(index: Int, value: Int) {
        palette.set(index, value)
    }

    override fun get(index: Int): Int {
        return palette.get(index)
    }

    fun unique(): Set<Int> {
        return palette.values.toSet()
    }

    fun uniqueSize(): Int {
        return palette.values.size
    }


    fun write(buf: ByteBuf) {
        palette.write(buf)
    }

}