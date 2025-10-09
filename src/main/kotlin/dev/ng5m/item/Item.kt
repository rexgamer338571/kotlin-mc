package dev.ng5m.item

import net.kyori.adventure.key.Key

class Item(val key: Key) {
    companion object {
        val UNDEFINED = Item(Key.key("undefined"))
    }

    enum class Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC
    }

}