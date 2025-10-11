package dev.ng5m.item

import dev.ng5m.block.Block
import dev.ng5m.block.Face
import dev.ng5m.packet.play.s2c.BlockUpdateS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.util.math.Vector3i
import net.kyori.adventure.key.Key

class BlockItem(key: Key, val block: Block) : Item(key) {

    override fun onInteractBlock(
        stack: ItemStack,
        player: Player,
        blockPos: Vector3i,
        hand: Hand.Relative,
        blockFace: Face
    ) {
        if (!player.getWorld().getBlockAt(blockPos.x, blockPos.y, blockPos.z).isEmpty()) return

        val state = block.defaultBlockState()
        val newPos = blockPos + blockFace.direction
        player.getWorld().setBlockStateAt(newPos, state)
        player.connection.sendPacket(BlockUpdateS2CPacket(newPos, state))
    }

}