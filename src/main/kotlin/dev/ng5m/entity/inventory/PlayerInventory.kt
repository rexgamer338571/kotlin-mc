package dev.ng5m.entity.inventory

import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetContainerContentsS2CPacket
import dev.ng5m.packet.play.s2c.SetContainerSlotS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.util.IntTracker

class PlayerInventory(val player: Player) : Inventory {
    private val array: Array<ItemStack?> = Array(46) { null }
    private val syncIdTracker = IntTracker()
    private var active = false

    var carriedItem: ItemStack? = ItemStack.AIR

    override fun setItem(slot: Int, stack: ItemStack) {
        array[slot] = stack

        if (active)
            player.connection.sendPacket(
                SetContainerSlotS2CPacket(
                    0, syncIdTracker.next(), slot.toShort(), stack
                )
            )
    }

    override fun getItem(slot: Int): ItemStack? = array[slot]

    fun clearItem(slot: Int) {
        array[slot] = null
    }

    override fun getContentsPacket(): SetContainerContentsS2CPacket {
        return SetContainerContentsS2CPacket(
            0, syncIdTracker.current(), array.map { it ?: ItemStack.AIR }, carriedItem ?: ItemStack.AIR
        )
    }

    fun craftingOutput(): ItemStack? = getItem(0)
    fun craftingOutput(stack: ItemStack) = setItem(0, stack)

    fun craftingSlot(x: Int, y: Int): ItemStack? = getItem(1 + x + 2 * y)
    fun craftingSlot(x: Int, y: Int, stack: ItemStack) = setItem(1 + x + 2 * y, stack)

    fun head(): ItemStack? = getItem(5)
    fun head(stack: ItemStack) = setItem(5, stack)

    fun chest(): ItemStack? = getItem(6)
    fun chest(stack: ItemStack) = setItem(6, stack)

    fun legs(): ItemStack? = getItem(7)
    fun legs(stack: ItemStack) = setItem(7, stack)

    fun feet(): ItemStack? = getItem(8)
    fun feet(stack: ItemStack) = setItem(8, stack)

    fun inventorySlot(relative: Int): ItemStack? = getItem(9 + relative)
    fun inventorySlot(relative: Int, stack: ItemStack) = setItem(9 + relative, stack)

    fun hotbar(relative: Int): ItemStack? = getItem(36 + relative)
    fun hotbar(relative: Int, stack: ItemStack) = setItem(36 + relative, stack)

    fun offhand(): ItemStack? = getItem(45)
    fun offhand(stack: ItemStack) = setItem(45, stack)

    fun activate() {
        active = true
    }

}