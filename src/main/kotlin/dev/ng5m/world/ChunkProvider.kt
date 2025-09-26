package dev.ng5m.world

import dev.ng5m.block.BlockState

@FunctionalInterface
interface ChunkProvider {
    companion object {
        val EMPTY: ChunkProvider = object : ChunkProvider {
            override fun get(world: World, x: Int, z: Int): Chunk {
                return Chunk(x, z, world.typeKey)
            }
        }
    }

    fun get(world: World, x: Int, z: Int): Chunk



}