package dev.ng5m.packet.handshake.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.Shift

data class HandshakeC2SPacket(
    val protocolVersion: Int, val serverHost: String,
    val serverPort: Short, val intention: Intention
) : Packet {
    companion object{
        val CODEC: Codec<HandshakeC2SPacket> = Codec.of(
            Codec.VARINT, HandshakeC2SPacket::protocolVersion,
            Codec.STRING, HandshakeC2SPacket::serverHost,
            Codec.SHORT, HandshakeC2SPacket::serverPort,
            Codec.ofEnum(Intention::class.java), HandshakeC2SPacket::intention,
            ::HandshakeC2SPacket
        ).forType(HandshakeC2SPacket::class.java)
    }

    @Shift(-1)
    enum class Intention {
        STATUS,
        LOGIN,
        TRANSFER
    }

}