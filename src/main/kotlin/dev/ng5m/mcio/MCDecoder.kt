package dev.ng5m.mcio

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState
import dev.ng5m.mcio.MCEncoder.Companion
import dev.ng5m.packet.play.c2s.SetCreativeModeSlotC2SPacket
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.util.Util
import dev.ng5m.util.NetworkFlow
import dev.ng5m.util.decompressZL
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MCDecoder : ReplayingDecoder<MCDecoder.State>() {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MCDecoder::class.java)
    }

    private var tmp: ByteBuf? = null

    enum class State {
        READING_LENGTH,
        READING_PAYLOAD
    }

    init {
        checkpoint(State.READING_LENGTH)
    }

    override fun handlerRemoved0(ctx: ChannelHandlerContext) {
        LOGGER.debug("Client disconnected, cleaning up")
        tmp?.release()
        tmp = null
        MinecraftServer.getInstance().removeConnection(ctx.channel())
    }

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        when (state()) {
            State.READING_LENGTH -> decodeLength(ctx, buf, out)
            State.READING_PAYLOAD -> decodePayload(ctx, buf, out)

            null -> throw IllegalStateException("what?")
        }
    }

    private fun decodeLength(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val length = try {
            Codec.VARINT.read(buf)
        } catch (e: Exception) {
            return
        }

        if (buf.readableBytes() >= length) {
            parsePacket(buf.readSlice(length), ctx, out)
            checkpoint(State.READING_LENGTH)
        } else {
            tmp = ctx.alloc().buffer(length)
            tmp!!.writeBytes(buf, buf.readerIndex(), buf.readableBytes())
            buf.skipBytes(buf.readableBytes())
            checkpoint(State.READING_PAYLOAD)
        }
    }

    private fun decodePayload(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val partial = tmp ?: run {
            checkpoint(State.READING_LENGTH)
            return
        }

        val remaining = partial.capacity() - partial.writerIndex()
        val toRead = minOf(remaining, buf.readableBytes())

        if (toRead > 0) {
            partial.writeBytes(buf, toRead)
        }

        if (partial.writerIndex() >= partial.capacity()) {
            parsePacket(partial, ctx, out)
            tmp = null
            checkpoint(State.READING_LENGTH)
        } else {
            checkpoint(State.READING_PAYLOAD)
        }
    }

    private fun parsePacket(buf: ByteBuf, ctx: ChannelHandlerContext, out: MutableList<Any>) {

        val readerIndex = buf.readerIndex()

        try {
            val connection: MinecraftConnection =
                MinecraftServer.getInstance().getOrRegisterConnection(ctx.channel())

            val compress = connection.compression != null

            val decompressed = if (compress) {
                val decompressed = ctx.alloc().buffer()

                val dataLength = Codec.VARINT.read(buf)
                if (buf.readableBytes() >= connection.compression!!.threshold) {
                    val compressed = Unpooled.wrappedBuffer(Codec.REMAINING.read(buf))

                    decompressZL(compressed, decompressed)
                }

                decompressed
            } else buf

            val packetId: Int = Codec.VARINT.read(decompressed)
            val protocolState: ProtocolState = connection.protocolState

            val type: Class<out Packet>? = try {
                protocolState.typeForId(NetworkFlow.SERVERBOUND, packetId)
            } catch (x: Throwable) {
                connection.close()
                throw x
            }

            /*
            if (type == SetCreativeModeSlotC2SPacket::class.java) {
                val arr = ByteArray(buf.writerIndex()) { buf.getByte(it) }
                println(Util.bytesToHex(arr))
            }

             */

            val codec = Codec.codecFor(type) ?: run {
                LOGGER.debug("wow....,")
                return
            }

            val packet: Packet = codec.read(decompressed)

            if (protocolState.shouldLog(packet::class.java)) LOGGER.debug("C -> S: {}", packet)
            out.add(packet)

        } catch (e: Exception) {
            buf.readerIndex(readerIndex)
            throw e
        }
    }
}