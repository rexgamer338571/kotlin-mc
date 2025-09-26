package dev.ng5m.packet.play.c2s

import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetCreativeModeSlotC2SPacket(
    val slot: Short,
    val itemStack: ItemStack
) : Packet {
    companion object {
        val CODEC: Codec<SetCreativeModeSlotC2SPacket> = Codec.of(
            Codec.SHORT, { it.slot },
            ItemStack.CODEC, { it.itemStack },
            ::SetCreativeModeSlotC2SPacket
        ).forType(SetCreativeModeSlotC2SPacket::class.java)
    }
}