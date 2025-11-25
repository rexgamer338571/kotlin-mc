package dev.ng5m

import dev.ng5m.util.PacketSendContext
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener

class NettyConnection(private val channel: Channel) : MinecraftConnection() {
    private var index = 0

    override fun internalSend(ctx: PacketSendContext) {
        channel.writeAndFlush(ctx.packet)
            .addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE)
            .addListener {
                if (it.isSuccess) {
                    ctx.finish()
                }
            }
    }

    override fun close() {
        MinecraftServer.getInstance().removeConnection(this)
        queuedPackets.clear()
        receivedPackets.clear()
        channel.close().syncUninterruptibly()
    }

    override fun closed(): Boolean = !channel.isOpen
}