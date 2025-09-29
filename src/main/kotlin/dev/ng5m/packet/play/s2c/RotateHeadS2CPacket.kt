package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.Entity
import dev.ng5m.entity.LivingEntity
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Codec

data class RotateHeadS2CPacket(
    val eid: Int,
    val headYaw: Float
) : Packet {
    companion object {
        val CODEC: Codec<RotateHeadS2CPacket> = Codec.of(
            Codec.VARINT, { it.eid },
            Codec.ANGLE, { it.headYaw },
            ::RotateHeadS2CPacket
        ).forType(RotateHeadS2CPacket::class.java)
    }

    constructor(entity: LivingEntity) : this(entity.getEntityId(), entity.headYaw)
}