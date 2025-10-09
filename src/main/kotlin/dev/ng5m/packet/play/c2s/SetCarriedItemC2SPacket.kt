package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import org.jetbrains.annotations.Range

data class SetCarriedItemC2SPacket(val slot: @Range(from = 0, to = 8) Short) : Packet {
    companion object {
        val CODEC: Codec<SetCarriedItemC2SPacket> = Codec.of(
            Codec.SHORT, { it.slot }, ::SetCarriedItemC2SPacket
        ).forType(SetCarriedItemC2SPacket::class.java)
    }
}