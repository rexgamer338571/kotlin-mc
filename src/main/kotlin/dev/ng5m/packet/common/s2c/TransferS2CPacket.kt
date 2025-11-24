package dev.ng5m.packet.common.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class TransferS2CPacket(
    val host: String,
    val port: Int
) : Packet {
    companion object {
        val CODEC: Codec<TransferS2CPacket> = Codec.of(
            Codec.STRING, { it.host },
            Codec.VARINT, { it.port },
            ::TransferS2CPacket
        ).forType(TransferS2CPacket::class.java)
    }
}