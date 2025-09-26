package dev.ng5m.packet.login.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetCompressionS2CPacket(val threshold: Int) : Packet {
    companion object {
        val CODEC: Codec<SetCompressionS2CPacket> = Codec.of(
            Codec.VARINT, { it.threshold }, ::SetCompressionS2CPacket
        ).forType(SetCompressionS2CPacket::class.java)
    }
}