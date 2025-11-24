package dev.ng5m.world

import dev.ng5m.MinecraftServer
import dev.ng5m.serialization.Codec
import org.joml.Vector2i
import org.joml.Vector3d
import org.joml.Vector3i
import kotlin.math.floor

class Location(var world: World, var xyz: Vector3d, var yaw: Float, var pitch: Float) {
    companion object {
        val POSITION_CODEC: Codec<Vector3d> = Codec.of(
            { buf ->
                val l = buf.readLong()
                return@of Vector3d((l shr 38).toDouble(), (l shl 52 shr 52).toDouble(), (l shl 26 shr 38).toDouble())
            },
            { buf, vec ->
                buf.writeLong(((vec.x.toBits() and 0x3FFFFFF) shl 38) or ((vec.z.toBits() and 0x3FFFFFF) shl 12) or (vec.y.toBits() and 0xFFF))
            }
        )

        val GLOBAL_POS_CODEC: Codec<Location> = Codec.of(
            Codec.KEY, { it.world.id },
            POSITION_CODEC, { it.xyz },
            { world, xyz -> Location(MinecraftServer.getInstance().getWorld(world), xyz) }
        )
    }

    constructor(world: World, x: Double, y: Double, z: Double, yaw: Float, pitch: Float) : this(world, Vector3d(x, y, z), yaw, pitch)
    constructor(world: World, xyz: Vector3d) : this(world, xyz, 0f, 0f)
    constructor(world: World, x: Double, y: Double, z: Double) : this(world, Vector3d(x, y, z))
    constructor(world: World, xyz: Vector3i) : this(world, xyz.x.toDouble(), xyz.y.toDouble(), xyz.z.toDouble())
    constructor(world: World) : this(world, Vector3d(0.0, 0.0, 0.0))


    fun x(): Double = xyz.x
    fun y(): Double = xyz.y
    fun z(): Double = xyz.z

    fun withXYZ(xyz: Vector3d): Location = Location(world, xyz, yaw, pitch)

    fun toChunk(): Vector2i {
        return Vector2i(floor(xyz.x / 16).toInt(), floor(xyz.z / 16).toInt())
    }

    fun clone(): Location {
        return Location(world, xyz.clone() as Vector3d, yaw, pitch)
    }
}