package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.CODEC_VECTOR3DS
import org.joml.Vector3d

sealed interface MoveEntityPacket : Packet {

    data class Pos(val eid: Int, val delta: Vector3d, val onGround: Boolean) : MoveEntityPacket {
        companion object {
            val CODEC: Codec<Pos> = Codec.of(
                Codec.VARINT, { it.eid },
                CODEC_VECTOR3DS, { it.delta },
                Codec.BOOLEAN, { it.onGround },
                ::Pos
            ).forType(Pos::class.java)
        }
    }

    data class PosRot(val eid: Int, val delta: Vector3d, val yaw: Float, val pitch: Float, val onGround: Boolean) : MoveEntityPacket {
        companion object {
            val CODEC: Codec<PosRot> = Codec.of(
                Codec.VARINT, { it.eid },
                CODEC_VECTOR3DS, { it.delta },
                Codec.ANGLE, { it.yaw },
                Codec.ANGLE, { it.pitch },
                Codec.BOOLEAN, { it.onGround },
                ::PosRot
            ).forType(PosRot::class.java)
        }
    }

}