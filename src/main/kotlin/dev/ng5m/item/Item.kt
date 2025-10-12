package dev.ng5m.item

import dev.ng5m.block.Face
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.util.math.Vector3i
import dev.ng5m.world.Location
import net.kyori.adventure.key.Key

open class Item(val key: Key) {
    companion object {
        val UNDEFINED = Item(Key.key("undefined"))
    }

    open fun onInteractBlock(stack: ItemStack, player: Player, location: Location, hand: Hand.Relative, blockFace: Face) {
    }

    enum class Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC
    }

}