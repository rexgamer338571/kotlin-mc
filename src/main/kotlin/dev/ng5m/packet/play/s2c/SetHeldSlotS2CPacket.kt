package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import org.jetbrains.annotations.Range

data class SetHeldSlotS2CPacket(val slot: @Range(from = 0, to = 8) Int) : Packet {
    companion object {
        val CODEC: Codec<SetHeldSlotS2CPacket> = Codec.of(
            Codec.VARINT, { it.slot }, ::SetHeldSlotS2CPacket
        ).forType(SetHeldSlotS2CPacket::class.java)
    }
}