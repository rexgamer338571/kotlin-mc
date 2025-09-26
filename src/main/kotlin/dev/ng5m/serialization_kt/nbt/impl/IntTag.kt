package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class IntTag(value: Int, name: String = "") : Tag<Int>(value, name) {
    override fun type(): Tag.TagType = TagType.INT
}