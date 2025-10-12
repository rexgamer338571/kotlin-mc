package dev.ng5m.item

import dev.ng5m.block.Block
import dev.ng5m.block.Face
import dev.ng5m.packet.play.s2c.BlockUpdateS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.util.copy
import dev.ng5m.util.toInts
import dev.ng5m.world.Location
import net.kyori.adventure.key.Key

open class BlockItem(key: Key, val block: Block) : Item(key) {

    override fun onInteractBlock(
        stack: ItemStack,
        player: Player,
        location: Location,
        hand: Hand.Relative,
        blockFace: Face
    ) {
        val newPos = location.xyz.copy().toInts().add(blockFace.direction)
        if (!player.getWorld().getBlockAt(newPos.x, newPos.y, newPos.z).isEmpty()) return

        val state = block.defaultBlockState()
        player.getWorld().setBlockStateAt(newPos, state)
        player.connection.sendPacket(BlockUpdateS2CPacket(newPos, state))
    }

}