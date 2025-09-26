package dev.ng5m.mcio

import dev.ng5m.MinecraftServer
import dev.ng5m.serialization.Packet
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class MCHandler : ChannelInboundHandlerAdapter() {
    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        val connection = MinecraftServer.getInstance().getOrRegisterConnection(ctx.channel())
        connection.removePlayer()
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        ctx ?: return; msg ?: return

        val packet: Packet = msg as Packet

        val connection = MinecraftServer.getInstance().getOrRegisterConnection(ctx.channel())
        val protocolState = connection.protocolState

        if (protocolState.shouldHandleImmediately(packet.javaClass))
            synchronized(MinecraftServer.LOCK) {
                connection.internalReceive(packet)
            }
        else
            synchronized(MinecraftServer.LOCK) {
                connection.receive(packet)
            }
    }
}