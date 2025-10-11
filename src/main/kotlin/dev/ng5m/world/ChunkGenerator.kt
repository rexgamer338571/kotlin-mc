package dev.ng5m.world

fun interface ChunkGenerator {
    companion object {
        val EMPTY = ChunkGenerator { }
    }

    fun generate(context: ChunkGenerationContext)

}