package dev.ng5m.packet.common

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.forType

data class KeepAlivePacket(val id: Long) : Packet {
    companion object {
        val CODEC: Codec<KeepAlivePacket> = Codec.LONG.xmap(::KeepAlivePacket) { it.id }
            .forType(KeepAlivePacket::class)
    }
}