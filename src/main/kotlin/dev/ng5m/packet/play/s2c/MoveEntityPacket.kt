package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.math.Vector3s

sealed interface MoveEntityPacket : Packet {

    data class Pos(val eid: Int, val delta: Vector3s, val onGround: Boolean) : MoveEntityPacket {
        companion object {
            val CODEC: Codec<Pos> = Codec.of(
                Codec.VARINT, { it.eid },
                Vector3s.CODEC_3_SHORTS, { it.delta },
                Codec.BOOLEAN, { it.onGround },
                ::Pos
            ).forType(Pos::class.java)
        }
    }

    data class PosRot(val eid: Int, val delta: Vector3s, val yaw: Float, val pitch: Float, val onGround: Boolean) : MoveEntityPacket {
        companion object {
            val CODEC: Codec<PosRot> = Codec.of(
                Codec.VARINT, { it.eid },
                Vector3s.CODEC_3_SHORTS, { it.delta },
                Codec.ANGLE, { it.yaw },
                Codec.ANGLE, { it.pitch },
                Codec.BOOLEAN, { it.onGround },
                ::PosRot
            ).forType(PosRot::class.java)
        }
    }

}