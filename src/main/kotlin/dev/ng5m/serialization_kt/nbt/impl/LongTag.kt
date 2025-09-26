package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class LongTag(value: Long, name: String = "") : Tag<Long>(value, name) {
    override fun type(): Tag.TagType = TagType.LONG
}