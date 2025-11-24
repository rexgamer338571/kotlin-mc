package dev.ng5m.world.particle

import dev.ng5m.registry.Registries
import dev.ng5m.serialization.Codec

data class ParticleInfo<O : ParticleOptions>(
    val type: ParticleType<O>,
    val options: O
) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        val CODEC: Codec<ParticleInfo<*>> = Codec.of(
            { buf ->
                val type = Registries.PARTICLE_TYPE.idCodec.read(buf).value()
                ParticleInfo(type as ParticleType<ParticleOptions>, type.optionsCodec.read(buf))
            },
            { buf, info ->
                Registries.PARTICLE_TYPE.idCodec.write(
                    buf,
                    Registries.PARTICLE_TYPE.resourceKeyByValue(info.type)
                )
                (info.type.optionsCodec as Codec<ParticleOptions>).write(buf, info.options)
            }
        )
    }
}
