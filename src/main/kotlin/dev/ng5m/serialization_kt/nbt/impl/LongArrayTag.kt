package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class LongArrayTag(value: LongArray, name: String = "") : Tag<LongArray>(value, name) {
    override fun type(): Tag.TagType = TagType.LONG_ARRAY
}