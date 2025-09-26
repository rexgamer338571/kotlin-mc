package dev.ng5m.util

import dev.ng5m.serialization.Codec

data class Property(
    val name: String,
    val value: String,
    val signature: String?
) {
    companion object {
        val CODEC: Codec<Property> = Codec.of(
            Codec.STRING, Property::name,
            Codec.STRING, Property::value,
            nullable(Codec.STRING), Property::signature,
            ::Property
        )
    }

}