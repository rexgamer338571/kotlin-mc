package dev.ng5m.item

class Item {
    companion object {
        val UNDEFINED = Item()
    }

    enum class Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC
    }

}