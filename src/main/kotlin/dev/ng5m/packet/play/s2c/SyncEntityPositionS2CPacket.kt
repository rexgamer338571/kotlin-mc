package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.Entity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.math.Vector3d

data class SyncEntityPositionS2CPacket(
    val eid: Int,
    val xyz: Vector3d,
    val velocity: Vector3d,
    val yaw: Float, val pitch: Float, val onGround: Boolean
) : Packet {
    companion object {
        val CODEC: Codec<SyncEntityPositionS2CPacket> = Codec.of(
            Codec.VARINT, { it.eid },
            Vector3d.CODEC_3_DOUBLES, { it.xyz },
            Vector3d.CODEC_3_DOUBLES, { it.velocity },
            Codec.FLOAT, { it.yaw },
            Codec.FLOAT, { it.pitch },
            Codec.BOOLEAN, { it.onGround },
            ::SyncEntityPositionS2CPacket
        ).forType(SyncEntityPositionS2CPacket::class.java)
    }

    constructor(
        eid: Int,
        x: Double, y: Double, z: Double,
        velocityX: Double, velocityY: Double, velocityZ: Double,
        yaw: Float, pitch: Float, onGround: Boolean
    ) : this(eid, Vector3d(x, y, z), Vector3d(velocityX, velocityY, velocityZ), yaw, pitch, onGround)

    constructor(entity: Entity) : this(
        entity.getEntityId(), entity.location.xyz, entity.velocity, entity.location.yaw,
        entity.location.pitch, entity.onGround
    )

}