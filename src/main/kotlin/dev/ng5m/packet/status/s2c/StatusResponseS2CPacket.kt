package dev.ng5m.packet.status.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class StatusResponseS2CPacket(val json: String) : Packet {
    companion object {
        val CODEC: Codec<StatusResponseS2CPacket> = Codec.of(
            Codec.STRING, StatusResponseS2CPacket::json,
            ::StatusResponseS2CPacket
        ).forType(StatusResponseS2CPacket::class.java)
    }
}