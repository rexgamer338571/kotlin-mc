package dev.ng5m.packet.login.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState
import dev.ng5m.packet.common.PluginMessagePacket
import dev.ng5m.packet.login.s2c.LoginSuccessS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.serialization.Codec
import io.netty.buffer.Unpooled
import net.kyori.adventure.key.Key

object LoginC2SHandlers {

    fun hello(connection: MinecraftConnection, packet: HelloC2SPacket) {
        connection.sendPacket(LoginSuccessS2CPacket(packet.identity))

        val player = Player().makeConnected(packet.identity)
        player.connection = connection
        connection.player = player
    }

    fun loginAck(connection: MinecraftConnection, packet: LoginAckC2SPacket) {
        connection.protocolState = ProtocolState.CONFIGURATION

        val brandLength = MinecraftServer.getInstance().brand.length
        val buf = Unpooled.buffer(Codec.VARINT.varintSize(brandLength) + brandLength)
        Codec.STRING.write(buf, MinecraftServer.getInstance().brand)

        connection.sendPacket(PluginMessagePacket(Key.key("brand"), buf.array())).onFinish {
            buf.release()
        }

        connection.updateKnownPacks()
    }




}