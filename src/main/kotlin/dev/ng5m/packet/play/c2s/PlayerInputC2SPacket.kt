package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.BitMask

data class PlayerInputC2SPacket(val flags: Flags) : Packet {
    companion object {
        val CODEC: Codec<PlayerInputC2SPacket> = Codec.of(
            Codec.bitField(Flags::class.java, ::Flags), { it.flags }, ::PlayerInputC2SPacket
        ).forType(PlayerInputC2SPacket::class.java)
    }

    data class Flags(
        @field:BitMask(0x01) val forward: Boolean = false,
        @field:BitMask(0x02) val backward: Boolean = false,
        @field:BitMask(0x04) val left: Boolean = false,
        @field:BitMask(0x08) val right: Boolean = false,
        @field:BitMask(0x10) val jump: Boolean = false,
        @field:BitMask(0x20) val sneak: Boolean = false,
        @field:BitMask(0x40) val sprint: Boolean = false,
    )

}