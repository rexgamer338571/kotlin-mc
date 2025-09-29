package dev.ng5m.event

import dev.ng5m.MinecraftServer
import dev.ng5m.event.impl.lifecycle.ServerShutdownEvent

object LifecycleEvents : EventListeners {

    @EventHandler
    fun onShutdown(event: ServerShutdownEvent) {
        MinecraftServer.getInstance().shutdown()
    }

}