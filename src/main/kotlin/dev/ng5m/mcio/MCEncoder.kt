package dev.ng5m.mcio

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.packet.configuration.s2c.RegistryDataS2CPacket
import dev.ng5m.packet.configuration.s2c.UpdateTagsS2CPacket
import dev.ng5m.packet.play.s2c.*
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.util.Util
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.server.TCPServer
import dev.ng5m.util.NetworkFlow
import dev.ng5m.util.Task
import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.EncoderException
import io.netty.handler.codec.MessageToByteEncoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.LinkedList
import java.util.Queue

class MCEncoder : MessageToByteEncoder<Packet>() {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MCEncoder::class.java)
    }

    private val server: TCPServer<Channel> = MinecraftServer.getInstance().getServer()

    override fun encode(ctx: ChannelHandlerContext, packet: Packet, out: ByteBuf) {
        val connection = server.getOrRegisterConnection(ctx.channel())
        val codec = Codec.codecFor(packet)

        codec ?: run {
            error("no codec found for ${packet.javaClass.simpleName}")
        }

        val buf = ctx.alloc().buffer()
        val id = connection.protocolState.idForType(NetworkFlow.CLIENTBOUND, packet.javaClass)
        if (id == -1) {
            throw EncoderException("Type ${packet::class.simpleName} not registered")
        }

        Codec.VARINT.write(buf, id)

        try {
            codec.write(buf, packet)
        } catch (x: Throwable) {
            x.printStackTrace()
        }

        if (packet is SpawnEntityS2CPacket) {
            val arr: ByteArray = ByteArray(buf.writerIndex()) { buf.getByte(it) }
            LOGGER.debug(Util.bytesToHex(arr))
        }

        Codec.VARINT.write(out, buf.writerIndex())
//        if (packet !is ChunkS2CPacket)
            out.writeBytes(buf)
        buf.release()

        if (connection.protocolState.shouldLog(packet.javaClass))
            LOGGER.debug("S -> C: {}", packet)
    }

}