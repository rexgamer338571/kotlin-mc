package dev.ng5m.registry

import dev.ng5m.MinecraftServer
import dev.ng5m.util.readFile
import net.kyori.adventure.key.Key

abstract class RegistryInitializer<T : Any>(private val registry: Registry<T>) {
    protected val set: MutableSet<Key> = mutableSetOf()

    fun add(path: String): ResourceKey<T> {
        val key = Key.key(path)
        set.add(key)

        return ResourceKey(registry, key)
    }

    fun populate() {
        val map: Map<Key, T> = load(registry.id)

        for (key in set) {
            val v = map[key] ?: continue
            registry.register(key, v)
        }
    }

    private fun <T> load(registryKey: Key): Map<Key, T> {
        return MinecraftServer.GSON.fromJson(
            readFile(
                Registry.DATA_PATH.resolve("${registryKey.value()}.json")
            ),
            registry.mapTypeToken.type
        )
    }


}

