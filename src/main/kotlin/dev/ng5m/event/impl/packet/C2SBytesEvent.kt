package dev.ng5m.event.impl.packet

import dev.ng5m.MinecraftConnection
import dev.ng5m.event.Cancellable
import dev.ng5m.event.Event
import io.netty.buffer.ByteBuf

data class C2SBytesEvent(val connection: MinecraftConnection, val bytes: ByteBuf) : Event, Cancellable {
    var cancelled = false
    override fun cancelled(): Boolean = cancelled
}