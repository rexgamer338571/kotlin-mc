package dev.ng5m.server

import dev.ng5m.MinecraftConnection
import java.util.concurrent.ConcurrentHashMap

abstract class TCPServer<C>(private val connectionFactory: (C) -> MinecraftConnection) {
    internal val connections: MutableMap<C, MinecraftConnection> = ConcurrentHashMap()

    abstract fun start(port: Int)
    abstract fun stop()

    internal open fun getOrRegisterConnection(key: C): MinecraftConnection {
        return connections.computeIfAbsent(key) { _ -> connectionFactory(key) }
    }

    internal fun removeConnection(key: C) {
        val connection: MinecraftConnection = connections.remove(key) ?: return
        connection.close()
    }

    internal fun removeConnection(connection: MinecraftConnection) {
        for (entry in connections) {
            if (entry.value == connection) {
                removeConnection(entry.key)
                return
            }
        }
    }

}