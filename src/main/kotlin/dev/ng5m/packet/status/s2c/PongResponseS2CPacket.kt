package dev.ng5m.packet.status.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class PongResponseS2CPacket(
    val payload: Long
) : Packet {
    companion object {
        val CODEC: Codec<PongResponseS2CPacket> = Codec.of(
            Codec.LONG, PongResponseS2CPacket::payload,
            ::PongResponseS2CPacket
        ).forType(PongResponseS2CPacket::class.java)
    }
}
