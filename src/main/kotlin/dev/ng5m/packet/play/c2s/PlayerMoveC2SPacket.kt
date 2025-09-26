package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.BitMask
import dev.ng5m.util.math.Vector3d

sealed interface PlayerMoveC2SPacket : Packet {
    companion object {
        private val FLAGS_CODEC: Codec<Flags> = Codec.bitField(Flags::class.java) { Flags() }
    }

    data class Pos(val xyz: Vector3d, val flags: Flags) : PlayerMoveC2SPacket {
        companion object {
            val CODEC: Codec<Pos> = Codec.of(
                Vector3d.CODEC_3_DOUBLES, { it.xyz },
                Codec.bitField(Flags::class.java) { Flags() }, { it.flags },
                ::Pos
            ).forType(Pos::class.java)
        }
    }

    data class PosRot(val xyz: Vector3d, val yaw: Float, val pitch: Float, val flags: Flags) : PlayerMoveC2SPacket {
        companion object {
            val CODEC: Codec<PosRot> = Codec.of(
                Vector3d.CODEC_3_DOUBLES, { it.xyz },
                Codec.FLOAT, { it.yaw },
                Codec.FLOAT, { it.pitch },
                FLAGS_CODEC, { it.flags },
                ::PosRot
            ).forType(PosRot::class.java)
        }
    }

    data class Rot(val yaw: Float, val pitch: Float, val flags: Flags) : PlayerMoveC2SPacket {
        companion object {
            val CODEC: Codec<Rot> = Codec.of(
                Codec.FLOAT, { it.yaw },
                Codec.FLOAT, { it.pitch },
                FLAGS_CODEC, { it.flags },
                ::Rot
            ).forType(Rot::class.java)
        }
    }

    data class Status(val flags: Flags) : PlayerMoveC2SPacket {
        companion object {
            val CODEC: Codec<Status> = FLAGS_CODEC.xmap(::Status) { it.flags }.forType(Status::class.java)
        }
    }

    data class Flags(
        @field:BitMask(0x01) var onGround: Boolean = false,
        @field:BitMask(0x02) var pushingAgainstWall: Boolean = false
    )

}