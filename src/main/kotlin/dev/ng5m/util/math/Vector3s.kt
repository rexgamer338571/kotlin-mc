package dev.ng5m.util.math

import dev.ng5m.serialization.Codec

class Vector3s(var x: Short, var y: Short, var z: Short) {
    companion object {
        val ZERO = Vector3s(0.toShort(), 0, 0)

        val CODEC_3_SHORTS: Codec<Vector3s> = Codec.of(
            Codec.SHORT, { it.x },
            Codec.SHORT, { it.y },
            Codec.SHORT, { it.z },
            ::Vector3s
        )
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is Vector3s) return false

        return other.x == x && other.y == y && other.z == z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

}