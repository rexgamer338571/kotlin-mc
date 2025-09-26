package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class IntArrayTag(value: IntArray, name: String = "") : Tag<IntArray>(value, name) {
    override fun type(): Tag.TagType = TagType.INT_ARRAY
}