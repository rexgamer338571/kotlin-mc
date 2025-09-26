package dev.ng5m.entity.inventory

import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetContainerContentsS2CPacket

interface Inventory {

    fun setItem(slot: Int, stack: ItemStack)
    fun getItem(slot: Int): ItemStack?

    fun getContentsPacket(): SetContainerContentsS2CPacket

}