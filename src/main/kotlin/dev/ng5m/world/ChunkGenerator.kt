package dev.ng5m.world

interface ChunkGenerator {
    companion object {
        val EMPTY = object : ChunkGenerator {
            override fun generate(context: ChunkGenerationContext) {
            }
        }
    }

    fun generate(context: ChunkGenerationContext)

}