package dev.ng5m

import dev.ng5m.packet.configuration.s2c.RegistryDataS2CPacket
import dev.ng5m.serialization.Packet
import dev.ng5m.util.PacketSendContext
import io.netty.channel.Channel

class NettyConnection(private val channel: Channel) : MinecraftConnection() {
    private var index = 0

    override fun internalSend(ctx: PacketSendContext) {
        channel.writeAndFlush(ctx.packet)
            .addListener {
                if (it.isSuccess) {
                    ctx.finish()
                }
            }
    }

    override fun close() {
        queuedPackets.clear()
        receivedPackets.clear()
        channel.close().syncUninterruptibly()
    }
}