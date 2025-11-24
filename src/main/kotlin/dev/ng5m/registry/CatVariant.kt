package dev.ng5m.registry

import dev.ng5m.serialization.Codec
import net.kyori.adventure.key.Key

class CatVariant(val texture: Key) {
    companion object {
        val CODEC: Codec<CatVariant> = Codec.of<Key, CatVariant>(
            Codec.KEY, { it.texture }, ::CatVariant
        )
    }
}