package dev.ng5m.packet.common

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.util.Util
import dev.ng5m.util.PEEKING_BYTE_ARRAY_CODEC
import dev.ng5m.util.PluginMessageManager
import dev.ng5m.util.or
import net.kyori.adventure.key.Key

data class PluginMessagePacket(
    val channel: Key,
    val data: ByteArray
) : Packet {
    companion object {
        val CODEC: Codec<PluginMessagePacket> = Codec.of(
            Codec.KEY, PluginMessagePacket::channel,
            Codec.of(
                { buf ->
                    val channel = Util.lastOfCodecRead as Key
                    val specialCodec: Codec<ByteArray> = or(PluginMessageManager.codecFor(channel),
                        Codec.REMAINING)!!

                    return@of specialCodec.read(buf)
                },
                { buf, arr ->
                    val channel = Util.lastOfCodecWrite as Key;
                    val specialCodec: Codec<ByteArray> = or(PluginMessageManager.codecFor(channel),
                        Codec.REMAINING)!!

                    specialCodec.write(buf, arr)
                }
            ), PluginMessagePacket::data,
            ::PluginMessagePacket
        ).forType(PluginMessagePacket::class.java)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginMessagePacket

        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
