package dev.ng5m.packet.common.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Transcoder
import net.kyori.adventure.text.Component

data class DisconnectS2CPacket(val reason: Component) : Packet {
    companion object {
        val CODEC: Codec<DisconnectS2CPacket> = Codec.STRING
            .xmap(Transcoder.COMPONENT_JSON)
            .xmap(::DisconnectS2CPacket) { it.reason }
            .forType(DisconnectS2CPacket::class.java)
    }
}