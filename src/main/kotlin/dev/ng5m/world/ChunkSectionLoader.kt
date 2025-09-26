package dev.ng5m.world

@FunctionalInterface
interface ChunkSectionLoader {
    companion object {
        val DEFAULT = object : ChunkSectionLoader {
            override fun get(y: Int): ChunkSection {
                return ChunkSection()
            }
        }
    }

    fun get(y: Int): ChunkSection

}