package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.NBT
import dev.ng5m.serialization_kt.nbt.Tag

class ListTag<T>(val elementType: Tag.TagType, value: List<T>, name: String = "") : Tag(value, name) {
    companion object {
        fun <T : Tag> make(value: List<T>, name: String): ListTag<T> =
            ListTag(
                if (value.isEmpty()) TagType.UNDEFINED else
                    NBT.TAG_TYPE_CLASS_MAP.getB(value.first()::class)!!, value, name
            )

        fun <T : Tag> make(value: List<T>): ListTag<T> = make(value, "")
    }

    override fun type(): Tag.TagType = TagType.LIST
}