package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Codec
import net.kyori.adventure.text.Component

data class TabListS2CPacket(
    val header: Component,
    val footer: Component
) : Packet {
    companion object {
        val CODEC: Codec<TabListS2CPacket> = Codec.of(
            Codec.TEXT_COMPONENT, { it.header },
            Codec.TEXT_COMPONENT, { it.footer },
            ::TabListS2CPacket
        ).forType(TabListS2CPacket::class.java)

        fun header(header: Component): TabListS2CPacket = TabListS2CPacket(header = header, footer = Component.empty())
        fun footer(footer: Component): TabListS2CPacket = TabListS2CPacket(footer = footer, header = Component.empty())
    }
}