package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class StringTag(value: String, name: String = "") : Tag<String>(value = value, name) {
    override fun type(): Tag.TagType = TagType.STRING
}