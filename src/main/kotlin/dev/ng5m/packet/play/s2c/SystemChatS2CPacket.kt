package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Codec
import dev.ng5m.util.forType
import net.kyori.adventure.text.Component

data class SystemChatS2CPacket(
    val content: Component,
    val actionBar: Boolean
) : Packet {
    companion object {
        val CODEC: Codec<SystemChatS2CPacket> = Codec.of(
            Codec.TEXT_COMPONENT, { it.content },
            Codec.BOOLEAN, { it.actionBar },
            ::SystemChatS2CPacket
        ).forType(SystemChatS2CPacket::class)
    }
}
