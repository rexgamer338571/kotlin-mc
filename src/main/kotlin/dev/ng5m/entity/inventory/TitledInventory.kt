package dev.ng5m.entity.inventory

import net.kyori.adventure.text.Component

interface TitledInventory : Inventory {

    fun title(): Component

}