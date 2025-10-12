package dev.ng5m.packet.play.c2s

import dev.ng5m.block.Face
import dev.ng5m.player.Hand
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.CODEC_POSITION
import dev.ng5m.util.CODEC_VECTOR3F
import org.joml.Vector3f
import org.joml.Vector3i
import dev.ng5m.util.ofEnum
import dev.ng5m.util.forType

data class UseItemOnC2SPacket(
    val hand: Hand.Relative,
    val blockPos: Vector3i,
    val face: Face,
    val cursorPos: Vector3f,
    val playerInsideBlock: Boolean,
    val worldBorderHit: Boolean,
    val sequence: Int
) : Packet {
    companion object {
        val CODEC: Codec<UseItemOnC2SPacket> = Codec.of(
            ofEnum<Hand.Relative>(), { it.hand },
            CODEC_POSITION, { it.blockPos },
            ofEnum<Face>(), { it.face },
            CODEC_VECTOR3F, { it.cursorPos },
            Codec.BOOLEAN, { it.playerInsideBlock },
            Codec.BOOLEAN, { it.worldBorderHit },
            Codec.VARINT, { it.sequence },
            ::UseItemOnC2SPacket
        ).forType(UseItemOnC2SPacket::class)
    }
}