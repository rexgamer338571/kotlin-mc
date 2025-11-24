package dev.ng5m

import dev.ng5m.registry.Registries
import net.kyori.adventure.key.Key

enum class EnchantmentType {
    PROTECTION,
    FIRE_PROTECTION,
    FEATHER_FALLING,
    BLAST_PROTECTION,
    PROJECTILE_PROTECTION,
    RESPIRATION,
    AQUA_AFFINITY,
    THORNS,
    DEPTH_STRIDER,
    FROST_WALKER,
    BINDING_CURSE,
    SOUL_SPEED,
    SWIFT_SNEAK,
    SHARPNESS,
    SMITE,
    BANE_OF_ARTHROPODS,
    KNOCKBACK,
    FIRE_ASPECT,
    LOOTING,
    SWEEPING_EDGE,
    EFFICIENCY,
    SILK_TOUCH,
    UNBREAKING,
    FORTUNE,
    POWER,
    PUNCH,
    FLAME,
    INFINITY,
    LUCK_OF_THE_SEA,
    LURE,
    LOYALTY,
    IMPALING,
    RIPTIDE,
    CHANNELING,
    MULTISHOT,
    QUICK_CHARGE,
    PIERCING,
    DENSITY,
    BREACH,
    WIND_BURST,
    MENDING,
    VANISHING_CURSE
    ;

    init {
        Registries.ENCHANTMENT.register(Key.key(name.lowercase()), this)
    }
}