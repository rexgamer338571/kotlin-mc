package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetEntityMotionS2CPacket(
    val entityId: Int,
    val vx: Short,
    val vy: Short,
    val vz: Short
) : Packet {
    companion object {
        val CODEC: Codec<SetEntityMotionS2CPacket> = Codec.of(
            Codec.VARINT, { it.entityId },
            Codec.SHORT, { it.vx },
            Codec.SHORT, { it.vy },
            Codec.SHORT, { it.vz },
            ::SetEntityMotionS2CPacket
        ).forType(SetEntityMotionS2CPacket::class.java)
    }
}