package dev.ng5m.packet.play.s2c

import dev.ng5m.player.Player
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.BitMask
import dev.ng5m.util.math.Vector3d

data class PlayerPosS2CPacket(
    val teleportID: Int,
    val xyz: Vector3d,
    val velocity: Vector3d,
    val yaw: Float,
    val pitch: Float,
    val flags: Flags
) : Packet {
    companion object {
        val CODEC: Codec<PlayerPosS2CPacket> = Codec.of(
            Codec.VARINT, { it.teleportID },
            Vector3d.CODEC_3_DOUBLES, { it.xyz },
            Vector3d.CODEC_3_DOUBLES, { it.velocity },
            Codec.FLOAT, { it.yaw },
            Codec.FLOAT, { it.pitch },
            Flags.CODEC, { it.flags },
            ::PlayerPosS2CPacket
        ).forType(PlayerPosS2CPacket::class.java)
    }

    constructor(player: Player, flags: Flags, callback: () -> Unit) : this(
        player.teleportIdTracker.next(callback),
        player.location.xyz,
        player.velocity,
        player.location.yaw,
        player.location.pitch,
        flags
    )

    constructor(player: Player, flags: Flags) : this(player, flags, {})

    data class Flags(
        @field:BitMask(0x01) val relativeX: Boolean = false,
        @field:BitMask(0x02) val relativeY: Boolean = false,
        @field:BitMask(0x04) val relativeZ: Boolean = false,
        @field:BitMask(0x08) val relativeYaw: Boolean = false,
        @field:BitMask(0x10) val relativePitch: Boolean = false,
        @field:BitMask(0x20) val relativeVX: Boolean = false,
        @field:BitMask(0x40) val relativeVY: Boolean = false,
        @field:BitMask(0x80) val relativeVZ: Boolean = false,
        @field:BitMask(0x100) val something: Boolean = false,
    ) {
        companion object {
            val CODEC: Codec<Flags> = Codec.intBitField(Flags::class.java) { Flags() }

            val ABSOLUTE: Flags = Flags()
        }
    }

}