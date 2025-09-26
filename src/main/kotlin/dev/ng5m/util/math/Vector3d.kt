package dev.ng5m.util.math

import dev.ng5m.serialization.Codec
import java.util.function.UnaryOperator

class Vector3d(var x: Double, var y: Double, var z: Double) {
    companion object {
        val ZERO = Vector3d(0.0, 0.0, 0.0)

        val CODEC_3_DOUBLES: Codec<Vector3d> = Codec.of(
            Codec.DOUBLE, { it.x },
            Codec.DOUBLE, { it.y },
            Codec.DOUBLE, { it.z },
            ::Vector3d
        )
    }

    constructor(x: Long, y: Long, z: Long) : this(x.toDouble(), y.toDouble(), z.toDouble())

    fun transform(op: UnaryOperator<Double>): Vector3d {
        x = op.apply(x)
        y = op.apply(y)
        z = op.apply(z)
        return this
    }

    operator fun minus(vec: Vector3d): Vector3d {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        return this
    }

    fun mul(scalar: Double): Vector3d {
        x *= scalar
        y *= scalar
        z *= scalar
        return this
    }

    fun div(scalar: Double): Vector3d {
        x /= scalar
        y /= scalar
        z /= scalar
        return this
    }

    fun toShorts(): Vector3s {
        return Vector3s(x.toInt().toShort(), y.toInt().toShort(), z.toInt().toShort())
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is Vector3d) return false

        return other.x == x && other.y == y && other.z == z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    fun clone(): Vector3d {
        return Vector3d(x, y, z)
    }

}