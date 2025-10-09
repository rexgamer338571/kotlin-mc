package dev.ng5m.util.math

import dev.ng5m.serialization.Codec

class Vector3i(var x: Int, var y: Int, var z: Int) {
    companion object {
        val ZERO = Vector3i(0, 0, 0)

        val CODEC_3_INTEGERS: Codec<Vector3i> = Codec.of(
            Codec.INTEGER, { it.x },
            Codec.INTEGER, { it.y },
            Codec.INTEGER, { it.z },
            ::Vector3i
        )

        val POSITION: Codec<Vector3i> = Codec.of(
            { buf ->
                val l = buf.readLong()
                Vector3i((l shr 38).toInt(), (l shl 52 shr 52).toInt(), (l shl 26 shr 38).toInt())
            },
            { buf, vec ->
                buf.writeLong(((vec.x.toLong() and 0x3FFFFFFL) shl 38) or ((vec.z.toLong() and 0x3FFFFFFL) shl 12) or (vec.y.toLong() and 0xFFFL))
            }
        )
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is Vector3i) return false

        return other.x == x && other.y == y && other.z == z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

}