package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.world.Chunk

data class ChunkS2CPacket(
    val chunk: Chunk
) : Packet {

    companion object {
        val CODEC: Codec<ChunkS2CPacket> = Chunk.CODEC.xmap(::ChunkS2CPacket) { it.chunk }.forType(ChunkS2CPacket::class.java)
    }

}