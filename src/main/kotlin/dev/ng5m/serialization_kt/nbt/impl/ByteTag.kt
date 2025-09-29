package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.nbt.Tag

class ByteTag(value: Byte, name: String) : Tag(value, name) {
    constructor(value: Byte) : this(value, "")

    override fun type(): TagType = TagType.BYTE
}