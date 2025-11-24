package dev.ng5m.world

import dev.ng5m.block.BlockState

fun interface ChunkProvider {
    companion object {
        val EMPTY: ChunkProvider = ChunkProvider { world, x, z -> Chunk(x, z, world.typeKey) }
    }

    fun get(world: World, x: Int, z: Int): Chunk



}