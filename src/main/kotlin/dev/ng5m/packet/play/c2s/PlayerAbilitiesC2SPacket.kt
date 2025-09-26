package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.BitMask

data class PlayerAbilitiesC2SPacket(val flags: Flags) : Packet {
    companion object {
        val CODEC: Codec<PlayerAbilitiesC2SPacket> = Codec.of(
            Codec.bitField(Flags::class.java, ::Flags), { it.flags }, ::PlayerAbilitiesC2SPacket
        ).forType(PlayerAbilitiesC2SPacket::class.java)
    }

    data class Flags(
        @field:BitMask(0x02) val flying: Boolean = false
    )

}