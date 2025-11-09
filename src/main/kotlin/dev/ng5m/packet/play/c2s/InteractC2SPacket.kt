package dev.ng5m.packet.play.c2s

import dev.ng5m.player.Hand
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.CODEC_VECTOR3F
import dev.ng5m.util.forType
import org.joml.Vector3f

data class InteractC2SPacket(
    val entityId: Int,
    val type: Type,
    val relativePos: Vector3f?,
    val hand: Hand.Relative?,
    val sneaking: Boolean
) : Packet {
    companion object {
        val CODEC: Codec<InteractC2SPacket> = Codec.of(
            { buf ->
                val eid = Codec.VARINT.read(buf)
                val type = Type.entries[Codec.VARINT.read(buf)]
                val targetXYZ = if (type == Type.INTERACT_AT)
                    CODEC_VECTOR3F.read(buf) else null
                val hand = if (type != Type.ATTACK)
                    Hand.Relative.entries[Codec.VARINT.read(buf)] else null
                val sneaking = buf.readBoolean()

                InteractC2SPacket(eid, type, targetXYZ, hand, sneaking)
            },
            { buf, packet ->
                Codec.VARINT.write(buf, packet.entityId)
                Codec.VARINT.write(buf, packet.type.ordinal)
                if (packet.type == Type.INTERACT_AT)
                    CODEC_VECTOR3F.write(buf, packet.relativePos!!)
                if (packet.type != Type.ATTACK)
                    Codec.VARINT.write(buf, packet.type.ordinal)
                buf.writeBoolean(packet.sneaking)
            }
        ).forType(InteractC2SPacket::class)
    }

    enum class Type {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }

}