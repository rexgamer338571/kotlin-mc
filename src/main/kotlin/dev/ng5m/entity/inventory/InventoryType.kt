package dev.ng5m.entity.inventory

import dev.ng5m.registry.Registries
import net.kyori.adventure.key.Key

data class InventoryType(val size: Int) {
    companion object {
        val GENERIC_9X1 = Registries.MENU.register(Key.key("generic_9x1"), InventoryType(9))
        val GENERIC_9X2 = Registries.MENU.register(Key.key("generic_9x2"), InventoryType(18))
        val GENERIC_9X3 = Registries.MENU.register(Key.key("generic_9x3"), InventoryType(27))
        val GENERIC_9X4 = Registries.MENU.register(Key.key("generic_9x4"), InventoryType(36))
        val GENERIC_9X5 = Registries.MENU.register(Key.key("generic_9x5"), InventoryType(45))
        val GENERIC_9X6 = Registries.MENU.register(Key.key("generic_9x6"), InventoryType(54))
        val GENERIC_3X3 = Registries.MENU.register(Key.key("generic_3x3"), InventoryType(9))
        val CRAFTER_3X3 = Registries.MENU.register(Key.key("crafter_3x3"), InventoryType(10))
        val ANVIL = Registries.MENU.register(Key.key("anvil"), InventoryType(3))
        val BEACON = Registries.MENU.register(Key.key("beacon"), InventoryType(1))
        val BLAST_FURNACE = Registries.MENU.register(Key.key("blast_furnace"), InventoryType(3))
        val BREWING_STAND = Registries.MENU.register(Key.key("brewing_stand"), InventoryType(4))
        val CRAFTING = Registries.MENU.register(Key.key("crafting"), InventoryType(10))
        val ENCHANTMENT = Registries.MENU.register(Key.key("enchantment"), InventoryType(2))
        val FURNACE = Registries.MENU.register(Key.key("furnace"), InventoryType(3))
        val GRINDSTONE = Registries.MENU.register(Key.key("grindstone"), InventoryType(3))
        val HOPPER = Registries.MENU.register(Key.key("hopper"), InventoryType(5))
        val LECTERN = Registries.MENU.register(Key.key("lectern"), InventoryType(1))
        val LOOM = Registries.MENU.register(Key.key("loom"), InventoryType(2))
        val MERCHANT = Registries.MENU.register(Key.key("merchant"), InventoryType(3))
        val SHULKER_BOX = Registries.MENU.register(Key.key("shulker_box"), InventoryType(27))
        val SMITHING = Registries.MENU.register(Key.key("smithing"), InventoryType(3))
        val SMOKER = Registries.MENU.register(Key.key("smoker"), InventoryType(3))
        val CARTOGRAPHY_TABLE = Registries.MENU.register(Key.key("cartography_table"), InventoryType(3))
        val STONECUTTER = Registries.MENU.register(Key.key("stonecutter"), InventoryType(3))
    }
}