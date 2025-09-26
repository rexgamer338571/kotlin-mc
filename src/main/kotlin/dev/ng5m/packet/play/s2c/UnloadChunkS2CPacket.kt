package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

class UnloadChunkS2CPacket(val cx: Int, val cz: Int) : Packet {
    companion object {
        val CODEC: Codec<UnloadChunkS2CPacket> = Codec.of(
            // yes this is correct, mojang is stupid
            Codec.INTEGER, { it.cz },
            Codec.INTEGER, { it.cx },
            ::UnloadChunkS2CPacket
        ).forType(UnloadChunkS2CPacket::class.java)
    }
}