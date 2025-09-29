package dev.ng5m.packet.common

import dev.ng5m.MinecraftConnection
import dev.ng5m.util.PluginMessageManager

object CommonHandlers {

    fun pluginMessage(connection: MinecraftConnection, packet: PluginMessagePacket) {
        println("plugin message on ${packet.channel}")

        PluginMessageManager.fire(connection, packet.channel, packet.data)
    }

}