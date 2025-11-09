package dev.ng5m.packet

import dev.ng5m.MinecraftConnection
import dev.ng5m.serialization.Packet

fun interface PacketHandler<T : Packet> {

    fun handle(connection: MinecraftConnection, packet: T)

}