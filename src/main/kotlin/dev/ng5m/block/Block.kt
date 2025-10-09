package dev.ng5m.block

import dev.ng5m.entity.BlockEntity
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.util.math.Vector3f
open class Block() {
    private val allowedStateProperties: MutableSet<BlockStateProperty<*>> = mutableSetOf()

    fun defaultBlockState(): BlockState = BlockState.stateManager.getDefaultState(this)

    open fun getBlockEntity(x: Int, y: Int, z: Int, state: BlockState): BlockEntity? = null

    open fun onInteract(
        player: Player, hand: Hand, face: Face, cursorPos: Vector3f
    ) {}

}