package dev.ng5m.util

class AABB(
    private var sizeX: Double = 0.0,
    private var sizeY: Double = 0.0,
    private var sizeZ: Double = 0.0
) {
    companion object {
        val ZERO = AABB()
    }

    fun sizeX(value: Double): AABB {
        sizeX = value
        return this
    }

    fun sizeY(value: Double): AABB {
        sizeY = value
        return this
    }

    fun sizeZ(value: Double): AABB {
        sizeZ = value
        return this
    }

}