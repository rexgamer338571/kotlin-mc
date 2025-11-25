package dev.ng5m.packet.common

import dev.ng5m.LOGGER
import dev.ng5m.MinecraftConnection
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.ResourcePackResponseEvent
import dev.ng5m.pack.ResourcePackManager
import dev.ng5m.packet.common.c2s.ResourcePackResponseC2SPacket
import dev.ng5m.util.PluginMessageManager
import net.kyori.adventure.text.Component
import java.util.LinkedList

object CommonHandlers {

    fun pluginMessage(connection: MinecraftConnection, packet: PluginMessagePacket) {
        println("plugin message on ${packet.channel}")

        PluginMessageManager.fire(connection, packet.channel, packet.data)
    }

    fun resourcePackResponse(connection: MinecraftConnection, packet: ResourcePackResponseC2SPacket) {
        if (EventManager.fireCancellable(ResourcePackResponseEvent(connection, packet))) return

        ResourcePackManager.getPack(packet.uuid) ?: run {
            LOGGER.warn { "Invalid resource pack in response" }
            return
        }

        val deferred = connection.awaitedPackResponses.remove(packet.uuid) ?: return
        deferred.complete(Unit)
    }

    fun keepAlive(connection: MinecraftConnection, packet: KeepAlivePacket) {
        if (connection.waitingForKeepAlive && packet.id == connection.keepAliveId) {
            connection.waitingForKeepAlive = false
        } else {
            connection.disconnect(Component.text("Slimed out"))
        }
    }

}