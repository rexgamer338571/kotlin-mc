package dev.ng5m.packet.status.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.packet.status.s2c.PongResponseS2CPacket
import dev.ng5m.packet.status.s2c.StatusResponseS2CPacket

object StatusC2SHandlers {

    fun statusRequest(connection: MinecraftConnection, packet: StatusRequestC2SPacket) {
        connection.sendPacket(StatusResponseS2CPacket(MinecraftServer.getInstance().getStatusResponse()))
    }

    fun pingRequest(connection: MinecraftConnection, packet: PingRequestC2SPacket) {
        connection.sendPacket(PongResponseS2CPacket(packet.payload)).onFinish { connection.close() }
    }

}