package dev.ng5m.world

import dev.ng5m.block.BlockState
import dev.ng5m.entity.BlockEntity
import dev.ng5m.registry.Biome
import dev.ng5m.registry.DimensionType
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.util.bitsToRepresent
import dev.ng5m.util.mapToValueList
import java.util.*

class Chunk(
    val x: Int,
    val z: Int,
    private val typeKey: ResourceKey<DimensionType>,
    private val sectionLoader: ChunkSectionLoader = ChunkSectionLoader.DEFAULT,
    private val heightmaps: CompoundTag = CompoundTag()
) {
    private val dimensionType = Registries.DIMENSION_TYPE.getOrThrow(typeKey)
    private val height = dimensionType.minY + dimensionType.height - 1
    internal val sections: MutableMap<Int, ChunkSection> = mutableMapOf()

    private val blockEntities: MutableMap<Int, BlockEntity> = mutableMapOf()

    companion object {
        val CODEC: Codec<Chunk> = Codec.of(
            Codec.INTEGER, { it.x },
            Codec.INTEGER, { it.z },
            NBT.UNNAMED_TAG_CODEC, { it.heightmaps },
            Codec.BYTE_ARRAY.xmap(
                { ChunkSection.NON_PREFIXED_LIST_CODEC.read(it) },
                { ChunkSection.NON_PREFIXED_LIST_CODEC.writeToByteArray(it) }
            ), { mapToValueList(it.sections) },
            BlockEntity.LIST_CODEC, { it.blockEntities.values.toList() },
            Codec.BIT_SET, { BitSet(it.sections.size + 2) },
            Codec.BIT_SET, { BitSet(it.sections.size + 2) },
            Codec.BIT_SET, { BitSet(it.sections.size + 2) },
            Codec.BIT_SET, { BitSet(it.sections.size + 2) },
            Codec.BYTE_ARRAY, { ByteArray(2048) },
            Codec.BYTE_ARRAY, { ByteArray(2048) },
            { _, _, _, _, _, _, _, _, _, _, _ -> TODO() }
        )

        fun packChunkCoordinates(x: Int, y: Int, z: Int, height: Int): Int {
            return (y shl (bitsToRepresent(height) + 8)) or ((x and 15) shl 4) or (z and 15)
        }

        fun unpackChunkCoordinates(v: Int, height: Int): Triple<Int, Int, Int> {
            return Triple(
                (v shr 4) and 15,
                (v shr (bitsToRepresent(height) + 8)),
                v and 15
            )
        }

    }

    init {
        val lowestSectionY: Int = dimensionType.minY / 16
        val highestSectionY: Int = height / 16

        for (i in lowestSectionY..highestSectionY) {
            sections[i] = sectionLoader.get(i)
        }
    }

    fun addBlockEntity(x: Int, y: Int, z: Int, blockEntity: BlockEntity) {
        blockEntities[packChunkCoordinates(x, y, z, height)] = blockEntity
    }

    fun addBlockEntity(blockEntity: BlockEntity) {
        addBlockEntity(blockEntity.x, blockEntity.y, blockEntity.z, blockEntity)
    }

    fun getBlockEntity(x: Int, y: Int, z: Int): BlockEntity? = blockEntities[packChunkCoordinates(x, y, z, height)]

    fun getBlockIdAt(x: Int, y: Int, z: Int): Int {
        val section: ChunkSection = sections[getSectionY(y)] ?: return -1

        return section.getBlock(x % 16, (y % 16 + 16) % 16, z % 16)
    }

    fun getBlockStateAt(x: Int, y: Int, z: Int): BlockState {
        return BlockState.stateManager.byId(getBlockIdAt(x % 16, y, z % 16))
    }

    fun setBlockStateAt(x: Int, y: Int, z: Int, state: BlockState) {
        val section: ChunkSection = sections[getSectionY(y)] ?: return

        val blockEntity = state.block.createBlockEntity(x, y, z, state)
        if (blockEntity != null) addBlockEntity(x, y, z, blockEntity)

        section.setBlock(x % 16, (y % 16 + 16) % 16, z % 16, BlockState.stateManager.idBy(state))
    }

    fun setBiomeAt(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
        setBiomeAtCell(x / 4, y / 4, z / 4, biome)
    }

    fun setBiomeAtCell(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
        val sectionY = getSectionY(y * 4)
        if (sectionY < 0 || sectionY >= sections.size) return

        val section: ChunkSection = sections[sectionY] ?: return

        val localY = (y * 4 + 64) % 16 / 4
        section.setBiome(x, localY, z, Registries.BIOME.idByKey(biome))
    }

    private fun getSectionY(y: Int): Int =
        if (y >= 0) y / 16 else ((y - 15) / 16)
//        (y + 64) / 16

}