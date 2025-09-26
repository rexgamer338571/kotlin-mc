package dev.ng5m.packet.login.c2s

import dev.ng5m.player.Identity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class HelloC2SPacket(val identity: Identity) : Packet {
    companion object {
        val CODEC: Codec<HelloC2SPacket> = Codec.of(
            Identity.CODEC_UNSIGNED, HelloC2SPacket::identity,
            ::HelloC2SPacket
        ).forType(HelloC2SPacket::class.java)
    }
}