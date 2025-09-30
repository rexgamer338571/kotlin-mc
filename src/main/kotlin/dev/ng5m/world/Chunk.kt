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
import dev.ng5m.util.mapToValueList
import dev.ng5m.util.math.Vector3i
import java.util.*

class Chunk(
    val x: Int,
    val z: Int,
    private val typeKey: ResourceKey<DimensionType>,
    private val sectionLoader: ChunkSectionLoader = ChunkSectionLoader.DEFAULT,
    private val heightmaps: CompoundTag = CompoundTag()
) {
    internal val sections: MutableMap<Int, ChunkSection> = mutableMapOf()

    private val blockEntities: MutableMap<Vector3i, BlockEntity> = mutableMapOf()

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
    }

    init {
        val dimensionType = Registries.DIMENSION_TYPE.getOrThrow(typeKey)
        val lowestSectionY: Int = dimensionType.minY / 16
        val highestSectionY: Int = (dimensionType.minY + dimensionType.height - 1) / 16

        for (i in lowestSectionY..highestSectionY) {
            sections[i] = sectionLoader.get(i)
        }
    }

    fun addBlockEntity(x: Int, y: Int, z: Int, blockEntity: BlockEntity) {
        blockEntities[Vector3i(x, y, z)] = blockEntity
    }

    fun getBlockIdAt(x: Int, y: Int, z: Int): Int {
        val section: ChunkSection = sections[getSectionY(y)] ?: return -1

        return section.getBlock(x, (y % 16 + 16) % 16, z)
    }

    fun getBlockStateAt(x: Int, y: Int, z: Int): BlockState {
        return Registries.BLOCK.getOrThrow(Registries.BLOCK.keyById(getBlockIdAt(x, y, z)))
    }

    fun setBlockStateAt(x: Int, y: Int, z: Int, state: BlockState) {
        val section: ChunkSection = sections[getSectionY(y)] ?: return

        section.setBlock(x, (y % 16 + 16) % 16, z, Registries.BLOCK.idByKey(Registries.BLOCK.resourceKeyByKey(state.id)))
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