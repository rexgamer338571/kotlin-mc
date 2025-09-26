package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class AcceptTeleportationC2SPacket(
    val teleportId: Int
) : Packet {
    companion object {
        val CODEC: Codec<AcceptTeleportationC2SPacket> = Codec.VARINT
            .xmap(::AcceptTeleportationC2SPacket) { it.teleportId }
            .forType(AcceptTeleportationC2SPacket::class.java)
    }
}
