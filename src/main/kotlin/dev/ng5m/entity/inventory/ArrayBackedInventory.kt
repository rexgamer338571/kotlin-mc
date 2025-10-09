package dev.ng5m.entity.inventory

import dev.ng5m.item.ItemStack
import dev.ng5m.registry.Registries
import dev.ng5m.util.DefaultedList

abstract class ArrayBackedInventory(val id: Int) : Inventory {
    private var revision: Int = 0
    protected val stacks: DefaultedList<ItemStack> = DefaultedList(size(), ItemStack.AIR)


    constructor() : this(Inventory.ID_TRACKER.next())

    override fun setItem(slot: Int, stack: ItemStack) {
        stacks[slot] = stack
        revision++
    }

    open fun size(): Int = Registries.MENU.getOrThrow(type()).size

    override fun getItem(slot: Int): ItemStack = stacks[slot]

    fun clearItem(slot: Int) {
        stacks.clear(slot)
    }

    override fun id(): Int = id
    override fun revision(): Int = revision
    override fun incrementRevision(): Int = ++revision
    override fun slots(): List<ItemStack> = stacks
}