package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag


class ShortTag(value: Short, name: String = "") : Tag(value, name) {
    override fun type(): Tag.TagType = TagType.SHORT
}