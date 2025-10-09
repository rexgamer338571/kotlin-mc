package dev.ng5m.packet.play.s2c

import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.Codec

data class SetCursorItemS2CPacket(
    val stack: ItemStack
) : Packet {
    companion object {
        val CODEC: Codec<SetCursorItemS2CPacket> = ItemStack.CODEC.xmap(::SetCursorItemS2CPacket) { it.stack }
            .forType(SetCursorItemS2CPacket::class.java)
    }
}