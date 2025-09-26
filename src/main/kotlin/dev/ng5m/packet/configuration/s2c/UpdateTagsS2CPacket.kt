package dev.ng5m.packet.configuration.s2c

import dev.ng5m.registry.Registry
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import net.kyori.adventure.key.Key
import java.util.stream.Collectors

data class UpdateTagsS2CPacket(
    val listOfTags: List<Tags>
) : Packet {
    companion object {
        val CODEC: Codec<UpdateTagsS2CPacket> = Tags.CODEC
            .list()
            .xmap(::UpdateTagsS2CPacket, UpdateTagsS2CPacket::listOfTags)
            .forType(UpdateTagsS2CPacket::class.java)

        fun ofRegistries(): UpdateTagsS2CPacket {
            val listOfTags: MutableList<Tags> = mutableListOf()

            for (registry in Registry.getAllRegistries()) {
                if (registry.tags.isEmpty()) continue

                @Suppress("UNCHECKED_CAST")
                val typedRegistry = registry as Registry<Any>

                val typedTags = registry.tags as Map<Key, Set<ResourceKey<Any>>>

                val list: MutableList<Tag> = mutableListOf()

                for ((tagKey, resourceKeys) in typedTags.entries) {
                    val ids = resourceKeys.map { resourceKey ->
                        typedRegistry.idByKey(resourceKey)
                    }.toList()

                    list.add(Tag(tagKey, ids))
                }

                listOfTags.add(Tags(registry.id, list))
            }

            return UpdateTagsS2CPacket(listOfTags)
        }
    }

    data class Tags(
        val registryId: Key,
        val tags: List<Tag>
    ) {
        companion object {
            val CODEC: Codec<Tags> = Codec.of(
                Codec.KEY, { it.registryId },
                Tag.CODEC.list(), { it.tags },
                ::Tags
            )
        }
    }

    data class Tag(
        val id: Key,
        val rawIds: List<Int>
    ) {
        companion object {
            val CODEC: Codec<Tag> = Codec.of(
                Codec.KEY, { it.id },
                Codec.VARINT.list(), { it.rawIds },
                ::Tag
            )
        }
    }
}