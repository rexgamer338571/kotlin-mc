package dev.ng5m.entity

import dev.ng5m.player.Player
import dev.ng5m.registry.Registries.ENTITY_TYPE
import dev.ng5m.util.AABB
import net.kyori.adventure.key.Key

data class EntityType(
    val boundingBox: AABB,
    val factory: (EntityType) -> Entity
) {
    companion object {
        private val AABB_BOAT = AABB(1.375, 0.5625, 1.375)

        val ACACIA_BOAT = ENTITY_TYPE.register(
            Key.key("acacia_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val ACACIA_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("acacia_chest_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val ALLAY = ENTITY_TYPE.register(
            Key.key("allay"),
            Builder().dimensions(0.35, 0.6).build()
        )

        val AREA_EFFECT_CLOUD = ENTITY_TYPE.register(
            Key.key("area_effect_cloud"),
            Builder().dimensions(2.0, 0.5).build()
        )

        val ARMADILLO = ENTITY_TYPE.register(
            Key.key("armadillo"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val ARMOR_STAND = ENTITY_TYPE.register(
            Key.key("armor_stand"),
            Builder().dimensions(0.5, 1.975).build()
        )

        val ARROW = ENTITY_TYPE.register(
            Key.key("arrow"),
            Builder().dimensions(0.5, 0.5).build()
        )

        val AXOLOTL = ENTITY_TYPE.register(
            Key.key("axolotl"),
            Builder().dimensions(0.75, 0.42).build()
        )

        val BAMBOO_CHEST_RAFT = ENTITY_TYPE.register(
            Key.key("bamboo_chest_raft"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val BAMBOO_RAFT = ENTITY_TYPE.register(
            Key.key("bamboo_raft"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val BAT = ENTITY_TYPE.register(
            Key.key("bat"),
            Builder().dimensions(0.5, 0.9).build()
        )

        val BEE = ENTITY_TYPE.register(
            Key.key("bee"),
            Builder().dimensions(0.7, 0.6).build()
        )

        val BIRCH_BOAT = ENTITY_TYPE.register(
            Key.key("birch_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val BIRCH_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("birch_chest_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val BLAZE = ENTITY_TYPE.register(
            Key.key("blaze"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val BLOCK_DISPLAY = ENTITY_TYPE.register(
            Key.key("block_display"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val BOGGED = ENTITY_TYPE.register(
            Key.key("bogged"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val BREEZE = ENTITY_TYPE.register(
            Key.key("breeze"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val BREEZE_WIND_CHARGE = ENTITY_TYPE.register(
            Key.key("breeze_wind_charge"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val CAMEL = ENTITY_TYPE.register(
            Key.key("camel"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val CAT = ENTITY_TYPE.register(
            Key.key("cat"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val CAVE_SPIDER = ENTITY_TYPE.register(
            Key.key("cave_spider"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val CHERRY_BOAT = ENTITY_TYPE.register(
            Key.key("cherry_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val CHERRY_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("cherry_chest_boat"),
            Builder().boundingBox(AABB_BOAT).build()
        )

        val CHEST_MINECART = ENTITY_TYPE.register(
            Key.key("chest_minecart"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val CHICKEN = ENTITY_TYPE.register(
            Key.key("chicken"),
            Builder().dimensions(0.7, 0.65).build()
        )

        val COD = ENTITY_TYPE.register(
            Key.key("cod"),
            Builder().build()
        )

        val COMMAND_BLOCK_MINECART = ENTITY_TYPE.register(
            Key.key("command_block_minecart"),
            Builder().build()
        )

        val COW = ENTITY_TYPE.register(
            Key.key("cow"),
            Builder().build()
        )

        val CREAKING = ENTITY_TYPE.register(
            Key.key("creaking"),
            Builder().build()
        )

        val CREEPER = ENTITY_TYPE.register(
            Key.key("creeper"),
            Builder().build()
        )

        val DARK_OAK_BOAT = ENTITY_TYPE.register(
            Key.key("dark_oak_boat"),
            Builder().build()
        )

        val DARK_OAK_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("dark_oak_chest_boat"),
            Builder().build()
        )

        val DOLPHIN = ENTITY_TYPE.register(
            Key.key("dolphin"),
            Builder().build()
        )

        val DONKEY = ENTITY_TYPE.register(
            Key.key("donkey"),
            Builder().build()
        )

        val DRAGON_FIREBALL = ENTITY_TYPE.register(
            Key.key("dragon_fireball"),
            Builder().build()
        )

        val DROWNED = ENTITY_TYPE.register(
            Key.key("drowned"),
            Builder().build()
        )
        val EGG = ENTITY_TYPE.register(
            Key.key("egg"),
            Builder().build()
        )
        val ELDER_GUARDIAN = ENTITY_TYPE.register(
            Key.key("elder_guardian"),
            Builder().build()
        )
        val ENDERMAN = ENTITY_TYPE.register(
            Key.key("enderman"),
            Builder().build()
        )

        val ENDERMITE = ENTITY_TYPE.register(
            Key.key("endermite"),
            Builder().build()
        )

        val ENDER_DRAGON = ENTITY_TYPE.register(
            Key.key("ender_dragon"),
            Builder().build()
        )

        val ENDER_PEARL = ENTITY_TYPE.register(
            Key.key("ender_pearl"),
            Builder().build()
        )

        val END_CRYSTAL = ENTITY_TYPE.register(
            Key.key("end_crystal"),
            Builder().build()
        )

        val EVOKER = ENTITY_TYPE.register(
            Key.key("evoker"),
            Builder().build()
        )

        val EVOKER_FANGS = ENTITY_TYPE.register(
            Key.key("evoker_fangs"),
            Builder().build()
        )

        val EXPERIENCE_BOTTLE = ENTITY_TYPE.register(
            Key.key("experience_bottle"),
            Builder().build()
        )
        val EXPERIENCE_ORB = ENTITY_TYPE.register(
            Key.key("experience_orb"),
            Builder().build()
        )
        val EYE_OF_ENDER = ENTITY_TYPE.register(
            Key.key("eye_of_ender"),
            Builder().build()
        )

        val FALLING_BLOCK = ENTITY_TYPE.register(
            Key.key("falling_block"),
            Builder().build()
        )

        val FIREBALL = ENTITY_TYPE.register(
            Key.key("fireball"),
            Builder().build()
        )

        val FIREWORK_ROCKET = ENTITY_TYPE.register(
            Key.key("firework_rocket"),
            Builder().build()
        )

        val FOX = ENTITY_TYPE.register(
            Key.key("fox"),
            Builder().build()
        )

        val FROG = ENTITY_TYPE.register(
            Key.key("frog"),
            Builder().build()
        )

        val FURNACE_MINECART = ENTITY_TYPE.register(
            Key.key("furnace_minecart"),
            Builder().build()
        )

        val GHAST = ENTITY_TYPE.register(
            Key.key("ghast"),
            Builder().build()
        )

        val GIANT = ENTITY_TYPE.register(
            Key.key("giant"),
            Builder().build()
        )

        val GLOW_ITEM_FRAME = ENTITY_TYPE.register(
            Key.key("glow_item_frame"),
            Builder().build()
        )

        val GLOW_SQUID = ENTITY_TYPE.register(
            Key.key("glow_squid"),
            Builder().build()
        )

        val GOAT = ENTITY_TYPE.register(
            Key.key("goat"),
            Builder().build()
        )

        val GUARDIAN = ENTITY_TYPE.register(
            Key.key("guardian"),
            Builder().build()
        )

        val HOGLIN = ENTITY_TYPE.register(
            Key.key("hoglin"),
            Builder().build()
        )

        val HOPPER_MINECART = ENTITY_TYPE.register(
            Key.key("hopper_minecart"),
            Builder().build()
        )

        val HORSE = ENTITY_TYPE.register(
            Key.key("horse"),
            Builder().build()
        )

        val HUSK = ENTITY_TYPE.register(
            Key.key("husk"),
            Builder().build()
        )

        val ILLUSIONER = ENTITY_TYPE.register(
            Key.key("illusioner"),
            Builder().build()
        )

        val INTERACTION = ENTITY_TYPE.register(
            Key.key("interaction"),
            Builder().build()
        )

        val IRON_GOLEM = ENTITY_TYPE.register(
            Key.key("iron_golem"),
            Builder().build()
        )

        val ITEM = ENTITY_TYPE.register(
            Key.key("item"),
            Builder().build()
        )

        val ITEM_DISPLAY = ENTITY_TYPE.register(
            Key.key("item_display"),
            Builder().build()
        )

        val ITEM_FRAME = ENTITY_TYPE.register(
            Key.key("item_frame"),
            Builder().build()
        )

        val JUNGLE_BOAT = ENTITY_TYPE.register(
            Key.key("jungle_boat"),
            Builder().build()
        )

        val JUNGLE_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("jungle_chest_boat"),
            Builder().build()
        )

        val LEASH_KNOT = ENTITY_TYPE.register(
            Key.key("leash_knot"),
            Builder().build()
        )

        val LIGHTNING_BOLT = ENTITY_TYPE.register(
            Key.key("lightning_bolt"),
            Builder().build()
        )

        val LLAMA = ENTITY_TYPE.register(
            Key.key("llama"),
            Builder().build()
        )

        val LLAMA_SPIT = ENTITY_TYPE.register(
            Key.key("llama_spit"),
            Builder().build()
        )

        val MAGMA_CUBE = ENTITY_TYPE.register(
            Key.key("magma_cube"),
            Builder().build()
        )

        val MANGROVE_BOAT = ENTITY_TYPE.register(
            Key.key("mangrove_boat"),
            Builder().build()
        )

        val MANGROVE_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("mangrove_chest_boat"),
            Builder().build()
        )

        val MARKER = ENTITY_TYPE.register(
            Key.key("marker"),
            Builder().build()
        )

        val MINECART = ENTITY_TYPE.register(
            Key.key("minecart"),
            Builder().build()
        )

        val MOOSHROOM = ENTITY_TYPE.register(
            Key.key("mooshroom"),
            Builder().build()
        )

        val MULE = ENTITY_TYPE.register(
            Key.key("mule"),
            Builder().build()
        )

        val OAK_BOAT = ENTITY_TYPE.register(
            Key.key("oak_boat"),
            Builder().build()
        )

        val OAK_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("oak_chest_boat"),
            Builder().build()
        )

        val OCELOT = ENTITY_TYPE.register(
            Key.key("ocelot"),
            Builder().build()
        )

        val OMINOUS_ITEM_SPAWNER = ENTITY_TYPE.register(
            Key.key("ominous_item_spawner"),
            Builder().build()
        )

        val PAINTING = ENTITY_TYPE.register(
            Key.key("painting"),
            Builder().build()
        )

        val PALE_OAK_BOAT = ENTITY_TYPE.register(
            Key.key("pale_oak_boat"),
            Builder().build()
        )

        val PALE_OAK_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("pale_oak_chest_boat"),
            Builder().build()
        )

        val PANDA = ENTITY_TYPE.register(
            Key.key("panda"),
            Builder().build()
        )

        val PARROT = ENTITY_TYPE.register(
            Key.key("parrot"),
            Builder().build()
        )

        val PHANTOM = ENTITY_TYPE.register(
            Key.key("phantom"),
            Builder().build()
        )

        val PIG = ENTITY_TYPE.register(
            Key.key("pig"),
            Builder().build()
        )

        val PIGLIN = ENTITY_TYPE.register(
            Key.key("piglin"),
            Builder().build()
        )

        val PIGLIN_BRUTE = ENTITY_TYPE.register(
            Key.key("piglin_brute"),
            Builder().build()
        )

        val PILLAGER = ENTITY_TYPE.register(
            Key.key("pillager"),
            Builder().build()
        )

        val POLAR_BEAR = ENTITY_TYPE.register(
            Key.key("polar_bear"),
            Builder().build()
        )

        val POTION = ENTITY_TYPE.register(
            Key.key("potion"),
            Builder().build()
        )

        val PUFFERFISH = ENTITY_TYPE.register(
            Key.key("pufferfish"),
            Builder().build()
        )

        val RABBIT = ENTITY_TYPE.register(
            Key.key("rabbit"),
            Builder().build()
        )

        val RAVAGER = ENTITY_TYPE.register(
            Key.key("ravager"),
            Builder().build()
        )

        val SALMON = ENTITY_TYPE.register(
            Key.key("salmon"),
            Builder().build()
        )

        val SHEEP = ENTITY_TYPE.register(
            Key.key("sheep"),
            Builder().build()
        )

        val SHULKER = ENTITY_TYPE.register(
            Key.key("shulker"),
            Builder().build()
        )

        val SHULKER_BULLET = ENTITY_TYPE.register(
            Key.key("shulker_bullet"),
            Builder().build()
        )

        val SILVERFISH = ENTITY_TYPE.register(
            Key.key("silverfish"),
            Builder().build()
        )

        val SKELETON = ENTITY_TYPE.register(
            Key.key("skeleton"),
            Builder().build()
        )

        val SKELETON_HORSE = ENTITY_TYPE.register(
            Key.key("skeleton_horse"),
            Builder().build()
        )

        val SLIME = ENTITY_TYPE.register(
            Key.key("slime"),
            Builder().build()
        )

        val SMALL_FIREBALL = ENTITY_TYPE.register(
            Key.key("small_fireball"),
            Builder().build()
        )

        val SNIFFER = ENTITY_TYPE.register(
            Key.key("sniffer"),
            Builder().build()
        )

        val SNOWBALL = ENTITY_TYPE.register(
            Key.key("snowball"),
            Builder().build()
        )

        val SNOW_GOLEM = ENTITY_TYPE.register(
            Key.key("snow_golem"),
            Builder().build()
        )

        val SPAWNER_MINECART = ENTITY_TYPE.register(
            Key.key("spawner_minecart"),
            Builder().build()
        )

        val SPECTRAL_ARROW = ENTITY_TYPE.register(
            Key.key("spectral_arrow"),
            Builder().build()
        )

        val SPIDER = ENTITY_TYPE.register(
            Key.key("spider"),
            Builder().build()
        )

        val SPRUCE_BOAT = ENTITY_TYPE.register(
            Key.key("spruce_boat"),
            Builder().build()
        )

        val SPRUCE_CHEST_BOAT = ENTITY_TYPE.register(
            Key.key("spruce_chest_boat"),
            Builder().build()
        )

        val SQUID = ENTITY_TYPE.register(
            Key.key("squid"),
            Builder().build()
        )

        val STRAY = ENTITY_TYPE.register(
            Key.key("stray"),
            Builder().build()
        )

        val STRIDER = ENTITY_TYPE.register(
            Key.key("strider"),
            Builder().build()
        )

        val TADPOLE = ENTITY_TYPE.register(
            Key.key("tadpole"),
            Builder().build()
        )

        val TEXT_DISPLAY = ENTITY_TYPE.register(
            Key.key("text_display"),
            Builder().build()
        )

        val TNT = ENTITY_TYPE.register(
            Key.key("tnt"),
            Builder().build()
        )

        val TNT_MINECART = ENTITY_TYPE.register(
            Key.key("tnt_minecart"),
            Builder().build()
        )

        val TRADER_LLAMA = ENTITY_TYPE.register(
            Key.key("trader_llama"),
            Builder().build()
        )

        val TRIDENT = ENTITY_TYPE.register(
            Key.key("trident"),
            Builder().build()
        )

        val TROPICAL_FISH = ENTITY_TYPE.register(
            Key.key("tropical_fish"),
            Builder().build()
        )

        val TURTLE = ENTITY_TYPE.register(
            Key.key("turtle"),
            Builder().build()
        )

        val VEX = ENTITY_TYPE.register(
            Key.key("vex"),
            Builder().build()
        )

        val VILLAGER = ENTITY_TYPE.register(
            Key.key("villager"),
            Builder().build()
        )

        val VINDICATOR = ENTITY_TYPE.register(
            Key.key("vindicator"),
            Builder().build()
        )

        val WANDERING_TRADER = ENTITY_TYPE.register(
            Key.key("wandering_trader"),
            Builder().build()
        )

        val WARDEN = ENTITY_TYPE.register(
            Key.key("warden"),
            Builder().build()
        )

        val WIND_CHARGE = ENTITY_TYPE.register(
            Key.key("wind_charge"),
            Builder().build()
        )

        val WITCH = ENTITY_TYPE.register(
            Key.key("witch"),
            Builder().build()
        )

        val WITHER = ENTITY_TYPE.register(
            Key.key("wither"),
            Builder().build()
        )

        val WITHER_SKELETON = ENTITY_TYPE.register(
            Key.key("wither_skeleton"),
            Builder().build()
        )

        val WITHER_SKULL = ENTITY_TYPE.register(
            Key.key("wither_skull"),
            Builder().build()
        )

        val WOLF = ENTITY_TYPE.register(
            Key.key("wolf"),
            Builder().build()
        )

        val ZOGLIN = ENTITY_TYPE.register(
            Key.key("zoglin"),
            Builder().build()
        )

        val ZOMBIE = ENTITY_TYPE.register(
            Key.key("zombie"),
            Builder().build()
        )

        val ZOMBIE_HORSE = ENTITY_TYPE.register(
            Key.key("zombie_horse"),
            Builder().build()
        )

        val ZOMBIE_VILLAGER = ENTITY_TYPE.register(
            Key.key("zombie_villager"),
            Builder().build()
        )

        val ZOMBIFIED_PIGLIN = ENTITY_TYPE.register(
            Key.key("zombified_piglin"),
            Builder().build()
        )

        val PLAYER = ENTITY_TYPE.register(
            Key.key("player"),
            Builder().factory { Player() }.build()
        )

        val FISHING_BOBBER = ENTITY_TYPE.register(
            Key.key("fishing_bobber"),
            Builder().build()
        )

    }


    class Builder() {
        private var boundingBox: AABB = AABB()
        private var factory: (EntityType) -> Entity = { Entity(it) }

        fun dimensions(width: Double, height: Double): Builder {
            boundingBox = AABB(width, height, width)
            return this
        }

        fun boundingBox(boundingBox: AABB): Builder {
            this.boundingBox = boundingBox
            return this
        }

        fun factory(factory: (EntityType) -> Entity): Builder {
            this.factory = factory
            return this
        }

        fun build(): EntityType {
            return EntityType(
                boundingBox,
                factory
            )
        }
    }

}