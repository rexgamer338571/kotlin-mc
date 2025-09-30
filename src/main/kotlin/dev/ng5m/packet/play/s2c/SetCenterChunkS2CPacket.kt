package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetCenterChunkS2CPacket(val cx: Int, val cz: Int) : Packet {
    companion object {
        val CODEC: Codec<SetCenterChunkS2CPacket> = Codec.of(
            Codec.VARINT, { it.cx },
            Codec.VARINT, { it.cz },
            ::SetCenterChunkS2CPacket
        ).forType(SetCenterChunkS2CPacket::class.java)
    }
}