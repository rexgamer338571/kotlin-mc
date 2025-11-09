package dev.ng5m.api

interface ChunkAccess {

    fun setBlock(x: Int, y: Int, z: Int, state: String)

}