package dev.ng5m.util.math

class Vector2i(var x: Int, var y: Int) {
    companion object {
        val ZERO = Vector2i(0, 0)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is Vector2i) return false

        return other.x == x && other.y == y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}