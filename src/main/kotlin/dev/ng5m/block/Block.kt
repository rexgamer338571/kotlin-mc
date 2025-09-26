package dev.ng5m.block

import net.kyori.adventure.key.Key

class Block(val id: Key, val allowedStateProperties: Collection<BlockStateProperty<*>>) {

    fun getBlockState(): BlockState {
        return BlockState(id)
    }

}