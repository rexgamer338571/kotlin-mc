package dev.ng5m.world

fun interface ChunkSectionLoader {
    companion object {
        val DEFAULT = ChunkSectionLoader { ChunkSection() }
    }

    fun get(y: Int): ChunkSection

}