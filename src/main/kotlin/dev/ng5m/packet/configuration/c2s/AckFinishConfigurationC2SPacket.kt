package dev.ng5m.packet.configuration.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

object AckFinishConfigurationC2SPacket : Packet {
    val CODEC: Codec<AckFinishConfigurationC2SPacket> = Codec.empty(AckFinishConfigurationC2SPacket)
        .forType(AckFinishConfigurationC2SPacket::class.java)
}