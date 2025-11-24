package dev.ng5m.entity

import dev.ng5m.serialization.Transcoder
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.reflect.KProperty

class MetadataProperty<T, V>(
    private val metadata: EntityMetadata,
    private val index: EntityMetadata.Index<V>,
    private val transcoder: Transcoder<T, V>
) {
    companion object {
        fun bitMask(
            metadata: EntityMetadata,
            index: EntityMetadata.Index<Byte>,
            mask: Int
        ): MetadataProperty<Boolean, Byte> {
            val bm = mask.toByte()
            return MetadataProperty(
                metadata, index,
                Transcoder.of(
                    { bl ->
                        val cur = metadata.getOrThrow(index)
                        if (bl) cur or bm else cur
                    },
                    { b ->
                        (b and bm) != 0.toByte()
                    }
                )
            )
        }

        fun <T> of(metadata: EntityMetadata, index: EntityMetadata.Index<T>): MetadataProperty<T, T> {
            return MetadataProperty(metadata, index, Transcoder.identity())
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return transcoder.from(metadata.getOrThrow(index))
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        metadata.set(index, transcoder.to(value))
    }

}