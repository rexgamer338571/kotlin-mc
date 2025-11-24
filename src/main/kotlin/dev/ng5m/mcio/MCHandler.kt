package dev.ng5m.mcio

import dev.ng5m.MinecraftServer
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.packet.C2SPacketPreHandleEvent
import dev.ng5m.serialization.Packet
import dev.ng5m.server.TCPServer
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class MCHandler : ChannelInboundHandlerAdapter() {
    private val server: TCPServer<Channel> = MinecraftServer.getInstance().getServer()

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        val connection = server.getOrRegisterConnection(ctx.channel())
        connection.removePlayer()
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is Packet) return


        val connection = server.getOrRegisterConnection(ctx.channel())
        val evPre = C2SPacketPreHandleEvent(connection, msg)
        EventManager.fire(evPre)
        if (evPre.cancelled()) return

        val protocolState = connection.protocolState

        if (protocolState.shouldHandleImmediately(msg.javaClass))
            synchronized(MinecraftServer.LOCK) {
                connection.internalReceive(msg)
            }
        else
            synchronized(MinecraftServer.LOCK) {
                connection.receive(msg)
            }
    }
}