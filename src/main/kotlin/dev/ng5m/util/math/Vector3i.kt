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