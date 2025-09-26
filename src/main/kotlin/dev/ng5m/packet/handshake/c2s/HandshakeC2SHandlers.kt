package dev.ng5m.packet.handshake.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState

object HandshakeC2SHandlers {


    fun handshake(connection: MinecraftConnection, packet: HandshakeC2SPacket) {
        if (packet.protocolVersion != MinecraftServer.PROTOCOL) {
            if (MinecraftServer.getInstance().strictDisconnect)
                connection.close()

            println("Received unknown protocol version: ${packet.protocolVersion}")
        }

        when (packet.intention) {
            HandshakeC2SPacket.Intention.STATUS -> connection.protocolState = ProtocolState.STATUS
            HandshakeC2SPacket.Intention.LOGIN -> connection.protocolState = ProtocolState.LOGIN

            HandshakeC2SPacket.Intention.TRANSFER -> TODO("future")
        }
    }
}