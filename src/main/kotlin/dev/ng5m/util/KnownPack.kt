package dev.ng5m.util

import dev.ng5m.serialization.Codec

data class KnownPack(
    val namespace: String,
    val id: String,
    val version: String
) {
    companion object {
        val CODEC: Codec<KnownPack> = Codec.of(
            Codec.STRING, KnownPack::namespace,
            Codec.STRING, KnownPack::id,
            Codec.STRING, KnownPack::version,
            ::KnownPack
        ).forType(KnownPack::class.java)
    }
}
