package dev.ng5m

import dev.ng5m.registry.Registries
import net.kyori.adventure.key.Key

enum class EnchantmentType {
    ;

    init {
        Registries.ENCHANTMENT.register(Key.key(name.lowercase()), this)
    }
}