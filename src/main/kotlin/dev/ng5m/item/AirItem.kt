package dev.ng5m.item

import dev.ng5m.block.Blocks
import dev.ng5m.block.Face
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.world.Location
import net.kyori.adventure.key.Key

object AirItem : BlockItem(Key.key("air"), Blocks.AIR) {

    override fun onInteractBlock(
        stack: ItemStack,
        player: Player,
        location: Location,
        hand: Hand.Relative,
        blockFace: Face
    ) {
    }

}