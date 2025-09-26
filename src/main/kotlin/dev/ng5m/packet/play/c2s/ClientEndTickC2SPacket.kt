package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

object ClientEndTickC2SPacket : Packet {
    val CODEC: Codec<ClientEndTickC2SPacket> = Codec.empty(ClientEndTickC2SPacket)
        .forType(ClientEndTickC2SPacket::class.java)
}