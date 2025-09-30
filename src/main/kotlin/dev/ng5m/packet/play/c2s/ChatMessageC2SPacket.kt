package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Codec
import dev.ng5m.util.nullable
import java.util.BitSet

data class ChatMessageC2SPacket(
    val message: String,
    val timestamp: Long,
    val salt: Long,
    val signature: ByteArray?,
    val messageCount: Int,
    val acknowledged: BitSet
) : Packet {
    companion object {
        val CODEC: Codec<ChatMessageC2SPacket> = Codec.of(
            Codec.STRING.limit(256), { it.message },
            Codec.LONG, { it.timestamp },
            Codec.LONG, { it.salt },
            Codec.fixedLengthByteArray(256).nullable(), { it.signature },
            Codec.VARINT, { it.messageCount },
            Codec.fixedBitSet(20), { it.acknowledged },
            ::ChatMessageC2SPacket
        ).forType(ChatMessageC2SPacket::class.java)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatMessageC2SPacket

        if (timestamp != other.timestamp) return false
        if (salt != other.salt) return false
        if (messageCount != other.messageCount) return false
        if (message != other.message) return false
        if (!signature.contentEquals(other.signature)) return false
        if (acknowledged != other.acknowledged) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + salt.hashCode()
        result = 31 * result + messageCount
        result = 31 * result + message.hashCode()
        result = 31 * result + (signature?.contentHashCode() ?: 0)
        result = 31 * result + acknowledged.hashCode()
        return result
    }
}