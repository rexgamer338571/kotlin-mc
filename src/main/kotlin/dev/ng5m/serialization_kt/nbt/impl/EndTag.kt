package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.Codec
import dev.ng5m.serialization_kt.nbt.Tag
import dev.ng5m.util.Null

object EndTag : Tag<Null>(Null, name = "") {
    val CODEC = Codec.empty(EndTag).forType(EndTag::class.java)

    override fun type(): TagType = TagType.END
}