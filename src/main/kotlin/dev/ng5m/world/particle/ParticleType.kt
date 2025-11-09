package dev.ng5m.world.particle

import dev.ng5m.serialization.Codec
import net.kyori.adventure.key.Key

data class ParticleType<O : ParticleOptions>(val key: Key, val optionsCodec: Codec<O>)