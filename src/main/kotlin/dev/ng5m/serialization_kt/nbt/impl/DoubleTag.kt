package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class DoubleTag(value: Double, name: String = "") : Tag<Double>(value, name) {
    override fun type(): Tag.TagType = TagType.DOUBLE
}