package dev.ng5m.packet.handshake.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState
import dev.ng5m.packet.PacketHandlerContext
import net.kyori.adventure.text.Component

object HandshakeC2SHandlers {


    fun handshake(connection: MinecraftConnection, packet: HandshakeC2SPacket, ctx: PacketHandlerContext) {
        if (packet.protocolVersion != MinecraftServer.PROTOCOL) {
            val msg = "Received unknown protocol version: ${packet.protocolVersion}"
            if (MinecraftServer.getInstance().strictDisconnect)
                ctx.disconnect(Component.text(msg))

            println(msg)
        }

        ctx.switchState(when (packet.intention) {
            HandshakeC2SPacket.Intention.STATUS -> ProtocolState.STATUS
            HandshakeC2SPacket.Intention.LOGIN -> ProtocolState.LOGIN

            HandshakeC2SPacket.Intention.TRANSFER -> TODO("future")
        })
    }
}