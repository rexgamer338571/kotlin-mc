package dev.ng5m.packet.play.s2c

import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetContainerContentsS2CPacket(
    val windowId: Int,
    val syncId: Int,
    val slots: List<ItemStack>,
    val carriedItem: ItemStack
) : Packet {
    companion object {
        val CODEC: Codec<SetContainerContentsS2CPacket> = Codec.of(
            Codec.VARINT, { it.windowId },
            Codec.VARINT, { it.syncId },
            ItemStack.CODEC.list(), { it.slots },
            ItemStack.CODEC, { it.carriedItem },
            ::SetContainerContentsS2CPacket
        ).forType(SetContainerContentsS2CPacket::class.java)
    }
}