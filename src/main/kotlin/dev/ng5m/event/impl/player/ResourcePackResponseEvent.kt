package dev.ng5m.event.impl.player

import dev.ng5m.MinecraftConnection
import dev.ng5m.event.Cancellable
import dev.ng5m.event.Event
import dev.ng5m.packet.common.c2s.ResourcePackResponseC2SPacket

data class ResourcePackResponseEvent(
    val connection: MinecraftConnection,
    val packet: ResourcePackResponseC2SPacket
) : Event, Cancellable {
    var cancelled = false
    override fun cancelled(): Boolean = cancelled

}