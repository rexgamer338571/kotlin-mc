package dev.ng5m.packet.play.c2s

import dev.ng5m.block.Face
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.CODEC_POSITION
import dev.ng5m.util.forType
import org.joml.Vector3i
import dev.ng5m.util.ofEnum

data class PlayerActionC2SPacket(
    val action: Action,
    val blockPos: Vector3i,
    val face: Face,
    val sequence: Int
) : Packet {
    companion object {
        val CODEC: Codec<PlayerActionC2SPacket> = Codec.of(
            ofEnum<Action>(), { it.action },
            CODEC_POSITION, { it.blockPos },
            ofEnum<Face>(), { it.face },
            Codec.VARINT, { it.sequence },
            ::PlayerActionC2SPacket
        ).forType(PlayerActionC2SPacket::class)
    }

    enum class Action {
        STARTED_DIGGING,
        CANCELLED_DIGGING,
        FINISHED_DIGGING,
        DROP_ITEM_STACK,
        DROP_ITEM,
        FINISH_USING_ITEM,
        SWAP_ITEM_IN_HAND
    }
}