package dev.ng5m.packet.play.c2s

import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class ContainerClickC2SPacket(
    val windowId: Int,
    val revision: Int,
    val slot: Short,
    val button: Byte,
    val mode: Int,
    val changedSlots: List<Pair<Short, ItemStack>>,
    val carriedItem: ItemStack
) : Packet {
    companion object {
        val CODEC: Codec<ContainerClickC2SPacket> = Codec.of(
            Codec.VARINT, { it.windowId },
            Codec.VARINT, { it.revision },
            Codec.SHORT, { it.slot },
            Codec.BYTE, { it.button },
            Codec.VARINT, { it.mode },
            Codec.of(
                Codec.SHORT, { it.first },
                ItemStack.CODEC, { it.second },
                ::Pair
            ).list(), { it.changedSlots },
            ItemStack.CODEC, { it.carriedItem },
            ::ContainerClickC2SPacket
        ).forType(ContainerClickC2SPacket::class.java)
    }
}