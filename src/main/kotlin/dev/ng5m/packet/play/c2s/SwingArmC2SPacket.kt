package dev.ng5m.packet.play.c2s

import dev.ng5m.player.Hand
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SwingArmC2SPacket(val hand: Hand.Relative) : Packet {
    companion object {
        val CODEC: Codec<SwingArmC2SPacket> = Codec.of(
            Codec.ofEnum(Hand.Relative::class.java), { it.hand }, ::SwingArmC2SPacket
        ).forType(SwingArmC2SPacket::class.java)
    }
}