package dev.ng5m.packet.status.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data object StatusRequestC2SPacket : Packet {
    val CODEC: Codec<StatusRequestC2SPacket> = Codec.empty(StatusRequestC2SPacket)
        .forType(StatusRequestC2SPacket.javaClass)
}
