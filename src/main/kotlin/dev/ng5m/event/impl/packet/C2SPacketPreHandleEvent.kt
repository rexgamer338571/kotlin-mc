package dev.ng5m.event.impl.packet

import dev.ng5m.MinecraftConnection
import dev.ng5m.event.Cancellable
import dev.ng5m.event.Event
import dev.ng5m.serialization.Packet

data class C2SPacketPreHandleEvent(val connection: MinecraftConnection, val packet: Packet) : Event, Cancellable {
    var cancelled = false
    override fun cancelled(): Boolean = cancelled
}