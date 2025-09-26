package dev.ng5m.packet.status.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class PingRequestC2SPacket(
    val payload: Long
) : Packet {
    companion object {
        val CODEC: Codec<PingRequestC2SPacket> = Codec.of(
            Codec.LONG, PingRequestC2SPacket::payload, ::PingRequestC2SPacket
        ).forType(PingRequestC2SPacket::class.java)
    }
}
