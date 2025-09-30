package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data object PlayerLoadedC2SPacket : Packet {
    val CODEC: Codec<PlayerLoadedC2SPacket> = Codec.empty(PlayerLoadedC2SPacket)
        .forType(PlayerLoadedC2SPacket::class.java)
}