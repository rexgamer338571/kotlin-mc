package dev.ng5m.block

import dev.ng5m.registry.Registries
import dev.ng5m.util.Properties
import dev.ng5m.util.StateManager

data class BlockState(
    val block: Block,
    val properties: Properties
) {
    companion object {
        val stateManager = StateManager<Block, BlockState>()
    }

    constructor(block: Block) : this(block, Properties.ofMap())
//    constructor(block: Key) : this(block, Properties.ofMap())

    fun <T> withProperty(property: BlockStateProperty<T>, value: T): BlockState {
        property.setter[properties] = value

        return this
    }

    fun <T> getProperty(property: BlockStateProperty<T>): T {
        return property.getter[properties]
    }

    fun asBlock(): Block = stateManager.getParent(this)

    override fun toString(): String {
        return "${Registries.BLOCK.keyByValue(stateManager.getParent(this))}$properties"
    }
}
