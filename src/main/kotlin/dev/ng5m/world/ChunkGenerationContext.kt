package dev.ng5m.world

import dev.ng5m.block.BlockState
import dev.ng5m.registry.Biome
import dev.ng5m.registry.ResourceKey

interface ChunkGenerationContext {

    fun chunkX(): Int
    fun chunkZ(): Int

    fun fillHeight(x: Int, z: Int, yRange: IntRange, state: BlockState)

    fun setBiomeAt(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>)
    fun setBiomeAtCell(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>)

}