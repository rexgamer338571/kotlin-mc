package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Packet
import dev.ng5m.serialization_kt.Codec

data class PlayerRotationS2CPacket(
    val yaw: Float,
    val pitch: Float
) : Packet {
    companion object {
        val CODEC: Codec<PlayerRotationS2CPacket> = Codec.of(
            Codec.FLOAT, { it.yaw },
            Codec.FLOAT, { it.pitch },
            ::PlayerRotationS2CPacket
        ).forType(PlayerRotationS2CPacket::class.java)
    }
}