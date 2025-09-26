package dev.ng5m.packet.login.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data object LoginAckC2SPacket : Packet {
    val CODEC: Codec<LoginAckC2SPacket> = Codec.empty(LoginAckC2SPacket)
        .forType(LoginAckC2SPacket::class.java)
}