package dev.ng5m.event.impl

import dev.ng5m.MinecraftConnection
import dev.ng5m.event.Event
import dev.ng5m.serialization.Packet

data class S2CPacketEvent(val connection: MinecraftConnection, val packet: Packet) : Event