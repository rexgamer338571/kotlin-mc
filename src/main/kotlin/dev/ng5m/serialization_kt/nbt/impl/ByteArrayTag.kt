package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class ByteArrayTag(value: ByteArray, name: String = "") : Tag<ByteArray>(value, name) {
    override fun type(): Tag.TagType = TagType.BYTE_ARRAY
}