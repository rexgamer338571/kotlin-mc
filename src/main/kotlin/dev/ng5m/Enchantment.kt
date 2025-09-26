package dev.ng5m

import dev.ng5m.serialization.Codec

data class Enchantment(val type: EnchantmentType, val level: Int) {
    companion object {
        val CODEC: Codec<Enchantment> = Codec.of(
            Codec.ofEnum(EnchantmentType::class.java), { it.type },
            Codec.VARINT, { it.level },
            ::Enchantment
        )
    }
}