package dev.ng5m.packet.common.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.forType
import dev.ng5m.util.nullable
import net.kyori.adventure.text.Component
import java.util.UUID

data class ResourcePackPushS2CPacket(
    val uuid: UUID,
    val url: String,
    val sha1: String,
    val forced: Boolean,
    val promptMessage: Component? = null
) : Packet {
    companion object {
        val CODEC: Codec<ResourcePackPushS2CPacket> = Codec.of(
            Codec.UUID, { it.uuid },
            Codec.STRING, { it.url },
            Codec.STRING, { it.sha1 },
            Codec.BOOLEAN, { it.forced },
            Codec.TEXT_COMPONENT.nullable(), { it.promptMessage },
            ::ResourcePackPushS2CPacket
        ).forType(ResourcePackPushS2CPacket::class)
    }
}