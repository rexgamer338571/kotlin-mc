package dev.ng5m.packet.play.s2c

import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetContainerSlotS2CPacket(
    val windowId: Int,
    val syncId: Int,
    val slot: Short,
    val stack: ItemStack
) : Packet {
    companion object {
        val CODEC: Codec<SetContainerSlotS2CPacket> = Codec.of(
            Codec.VARINT, { it.windowId },
            Codec.VARINT, { it.syncId },
            Codec.SHORT, { it.slot },
            ItemStack.CODEC, { it.stack },
            ::SetContainerSlotS2CPacket
        ).forType(SetContainerSlotS2CPacket::class.java)
    }

}