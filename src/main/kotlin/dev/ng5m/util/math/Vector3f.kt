package dev.ng5m.util.math

import dev.ng5m.serialization.Codec
import java.util.function.UnaryOperator

class Vector3f(var x: Float, var y: Float, var z: Float) {
    companion object {
        val ZERO = Vector3f(0.0f, 0.0f, 0.0f)

        val CODEC_3_FLOATS: Codec<Vector3f> = Codec.of(
            Codec.FLOAT, { it.x },
            Codec.FLOAT, { it.y },
            Codec.FLOAT, { it.z },
            ::Vector3f
        )
    }

    fun transform(op: UnaryOperator<Float>): Vector3f {
        x = op.apply(x)
        y = op.apply(y)
        z = op.apply(z)
        return this
    }

    operator fun minus(vec: Vector3f): Vector3f {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        return this
    }

    fun mul(scalar: Float): Vector3f {
        x *= scalar
        y *= scalar
        z *= scalar
        return this
    }

    fun div(scalar: Float): Vector3f {
        x /= scalar
        y /= scalar
        z /= scalar
        return this
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is Vector3f) return false

        return other.x == x && other.y == y && other.z == z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    fun clone(): Vector3f {
        return Vector3f(x, y, z)
    }

}