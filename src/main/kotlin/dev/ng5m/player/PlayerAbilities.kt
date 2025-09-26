package dev.ng5m.player

import dev.ng5m.packet.play.c2s.PlayerAbilitiesC2SPacket
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.annotation.BitMask

data class PlayerAbilities(
    val flags: Flags = Flags(),
    var flyingSpeed: Float = 0.05f,
    var fovModifier: Float = 0.1f
) {
    companion object {
        val CODEC: Codec<PlayerAbilities> = Codec.of(
            Codec.bitField(Flags::class.java, ::Flags), { it.flags },
            Codec.FLOAT, { it.flyingSpeed },
            Codec.FLOAT, { it.fovModifier },
            ::PlayerAbilities
        )
    }

    data class Flags(
        @field:BitMask(0x01) var invulnerable: Boolean = false,
        @field:BitMask(0x02) var flying: Boolean = false,
        @field:BitMask(0x04) var allowFlying: Boolean = false,
        @field:BitMask(0x08) var instantBreak: Boolean = false,
    )

}