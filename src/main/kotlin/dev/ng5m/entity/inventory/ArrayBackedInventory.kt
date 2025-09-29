package dev.ng5m.entity.inventory

import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetContainerContentsS2CPacket
import dev.ng5m.util.IntTracker

abstract class ArrayBackedInventory(val id: Int) : Inventory {
    companion object {
        private val ID_TRACKER = IntTracker()
    }

    private var active = false
    protected val array: Array<ItemStack?> = Array(46) { null }
    protected val syncIdTracker = IntTracker()

    var carriedItem: ItemStack? = ItemStack.AIR

    constructor() : this(ID_TRACKER.next())

    override fun setItem(slot: Int, stack: ItemStack) {
        array[slot] = stack

        if (active)
            updateSlot(syncIdTracker.next(), slot.toShort(), stack)
    }

    abstract fun updateSlot(syncId: Int, slot: Short, itemStack: ItemStack)

    override fun getItem(slot: Int): ItemStack? = array[slot]

    fun clearItem(slot: Int) {
        array[slot] = null
    }

    override fun getContentsPacket(): SetContainerContentsS2CPacket {
        return SetContainerContentsS2CPacket(
            0, syncIdTracker.current(), array.map { it ?: ItemStack.AIR }, carriedItem ?: ItemStack.AIR
        )
    }

    fun activate() {
        active = true
    }

}