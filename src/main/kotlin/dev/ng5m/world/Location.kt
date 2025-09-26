package dev.ng5m.world

import dev.ng5m.serialization.Codec
import dev.ng5m.util.math.Vector2i
import dev.ng5m.util.math.Vector3d
import kotlin.math.floor

class Location(var world: World, var xyz: Vector3d, var yaw: Float, var pitch: Float) {
    companion object {
        val POSITION_CODEC: Codec<Vector3d> = Codec.of(
            { buf ->
                val l = buf.readLong()
                return@of Vector3d(l shr 38, l shl 52 shr 52, l shl 26 shr 38)
            },
            { buf, vec ->
                buf.writeLong(((vec.x.toBits() and 0x3FFFFFF) shl 38) or ((vec.z.toBits() and 0x3FFFFFF) shl 12) or (vec.y.toBits() and 0xFFF))
            }
        )
    }

    constructor(world: World, xyz: Vector3d) : this(world, xyz, 0f, 0f)
    constructor(world: World, x: Double, y: Double, z: Double) : this(world, Vector3d(x, y, z))
    constructor(world: World) : this(world, Vector3d.ZERO)

    fun toChunk(): Vector2i {
        return Vector2i(floor(xyz.x / 16).toInt(), floor(xyz.z / 16).toInt())
    }

    fun clone(): Location {
        return Location(world, Vector3d(xyz.x, xyz.y, xyz.z), yaw, pitch)
    }
}