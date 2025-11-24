package dev.ng5m.entity.villager

import dev.ng5m.registry.Registries
import dev.ng5m.serialization.Codec

data class VillagerData(
    val type: VillagerType,
    val profession: VillagerProfession,
    val level: Int
) {
    companion object {
        val METADATA_CODEC: Codec<VillagerData> = Codec.of(
            Registries.VILLAGER_TYPE.idValueCodec, { it.type },
            Registries.VILLAGER_PROFESSION.idValueCodec, { it.profession },
            Codec.VARINT, { it.level },
            ::VillagerData
        )
    }
}