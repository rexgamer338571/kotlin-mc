package dev.ng5m.block

import org.joml.Vector3i

typealias Direction = Face
enum class Face(val direction: Vector3i) {
    BOTTOM(Vector3i(0, -1, 0)),
    TOP(Vector3i(0, 1, 0)),
    NORTH(Vector3i(0, 0, -1)),
    SOUTH(Vector3i(0, 0, 1)),
    WEST(Vector3i(-1, 0, 0)),
    EAST(Vector3i(1, 0, 0))
}