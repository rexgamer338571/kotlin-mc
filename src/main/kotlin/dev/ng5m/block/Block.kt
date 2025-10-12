package dev.ng5m.block

import dev.ng5m.entity.BlockEntity
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import org.joml.Vector3f
import dev.ng5m.world.ChunkSection

open class Block() {
    private val allowedStateProperties: MutableSet<BlockStateProperty<*>> = mutableSetOf()

    fun defaultBlockState(): BlockState = BlockState.stateManager.getDefaultState(this)

    open fun createBlockEntity(x: Int, y: Int, z: Int, state: BlockState): BlockEntity? = null

    open fun onInteract(
        player: Player, hand: Hand.Relative, face: Face, cursorPos: Vector3f, blockEntity: BlockEntity?
    ) {}
    open fun onInteract(
        player: Player, hand: Hand.Relative, face: Face, cursorPos: Vector3f
    ) = onInteract(player, hand, face, cursorPos, null)

    fun isEmpty(): Boolean {
        return ChunkSection.nonNonAirBlocks.contains(Registries.BLOCK.resourceKeyByValue(this))
    }

}