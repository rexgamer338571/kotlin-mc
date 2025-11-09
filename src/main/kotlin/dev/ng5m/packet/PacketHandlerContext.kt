package dev.ng5m.packet

import dev.ng5m.MinecraftConnection
import dev.ng5m.ProtocolState
import dev.ng5m.serialization.Packet
import net.kyori.adventure.text.Component

data class PacketHandlerContext(val connection: MinecraftConnection, val packet: Packet) {
    private var disconnectMessage: Component? = null
    private var newState: ProtocolState? = null

    fun disconnect(message: Component) {
        disconnectMessage = message
    }

    fun switchState(protocolState: ProtocolState) {
        newState = protocolState
    }

    fun afterHandled() {
        newState?.let { connection.protocolState = it }
        disconnectMessage?.let { connection.disconnect(it) }
    }

}