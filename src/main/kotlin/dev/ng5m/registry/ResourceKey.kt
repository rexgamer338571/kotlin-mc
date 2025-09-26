package dev.ng5m.registry

import net.kyori.adventure.key.Key

data class ResourceKey<T : Any>(
    val registry: Registry<T>,
    val key: Key
)