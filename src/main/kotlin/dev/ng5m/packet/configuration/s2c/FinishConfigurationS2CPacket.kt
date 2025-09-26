package dev.ng5m.packet.configuration.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data object FinishConfigurationS2CPacket : Packet {
    val CODEC: Codec<FinishConfigurationS2CPacket> =
        Codec.empty(FinishConfigurationS2CPacket)
            .forType(FinishConfigurationS2CPacket::class.java)
}