package dev.ng5m.entity.inventory

import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetContainerContentsS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.IntTracker
import net.kyori.adventure.text.Component
import org.slf4j.helpers.Util

interface Inventory {
    companion object {
        val ID_TRACKER: IntTracker = IntTracker(start = 1, max = 100)

        fun createGeneric9x3(title: Component): Inventory = object : ArrayBackedInventory(), TitledInventory {
            override fun title(): Component = title
            override fun type(): ResourceKey<InventoryType> = InventoryType.GENERIC_9X3

            override fun setItem(slot: Int, stack: ItemStack) {
                super.setItem(slot, stack)
                println("setting $slot to ${stack.item.key} from ${Util.getCallingClass().name}")
            }
        }
    }

    fun setItem(slot: Int, stack: ItemStack)
    fun getItem(slot: Int): ItemStack

    fun id(): Int
    fun revision(): Int
    fun incrementRevision(): Int
    fun slots(): List<ItemStack>

    fun type(): ResourceKey<InventoryType> = throw UnsupportedOperationException()

}