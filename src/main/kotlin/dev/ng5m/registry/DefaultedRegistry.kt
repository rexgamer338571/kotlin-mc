package dev.ng5m.registry

import dev.ng5m.serialization.Codec
import net.kyori.adventure.key.Key

class DefaultedRegistry<T : Any>(id: Key, entryClass: Class<T>, dataDriven: Boolean, private val default: Int) : Registry<T>(id, entryClass, dataDriven) {
    override fun provideIdCodec(): Codec<ResourceKey<T>> =
        Codec.VARINT.xmap({
            try {
                return@xmap keyById(it)
            } catch (x: Exception) {
                return@xmap keyById(default)
            }
        }, { idByKey(it) })

}