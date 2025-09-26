package dev.ng5m.entity

import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import net.kyori.adventure.key.Key

class BlockEntityType {
    companion object {
        val FURNACE = register("furnace")
        val CHEST = register("chest")
        val TRAPPED_CHEST = register("trapped_chest")
        val ENDER_CHEST = register("ender_chest")
        val JUKEBOX = register("jukebox")
        val DISPENSER = register("dispenser")
        val DROPPER = register("dropper")
        val SIGN = register("sign")
        val HANGING_SIGN = register("hanging_sign")
        val MOB_SPAWNER = register("mob_spawner")
        val CREAKING_HEART = register("creaking_heart")
        val PISTON = register("piston")
        val BREWING_STAND = register("brewing_stand")
        val ENCHANTING_TABLE = register("enchanting_table")
        val END_PORTAL = register("end_portal")
        val BEACON = register("beacon")
        val SKULL = register("skull")
        val DAYLIGHT_DETECTOR = register("daylight_detector")
        val HOPPER = register("hopper")
        val COMPARATOR = register("comparator")
        val BANNER = register("banner")
        val STRUCTURE_BLOCK = register("structure_block")
        val END_GATEWAY = register("end_gateway")
        val COMMAND_BLOCK = register("command_block")
        val SHULKER_BOX = register("shulker_box")
        val BED = register("bed")
        val CONDUIT = register("conduit")
        val BARREL = register("barrel")
        val SMOKER = register("smoker")
        val BLAST_FURNACE = register("blast_furnace")
        val LECTERN = register("lectern")
        val BELL = register("bell")
        val JIGSAW = register("jigsaw")
        val CAMPFIRE = register("campfire")
        val BEEHIVE = register("beehive")
        val SCULK_SENSOR = register("sculk_sensor")
        val CALIBRATED_SCULK_SENSOR = register("calibrated_sculk_sensor")
        val SCULK_CATALYST = register("sculk_catalyst")
        val SCULK_SHRIEKER = register("sculk_shrieker")
        val CHISELED_BOOKSHELF = register("chiseled_bookshelf")
        val BRUSHABLE_BLOCK = register("brushable_block")
        val DECORATED_POT = register("decorated_pot")
        val CRAFTER = register("crafter")
        val TRIAL_SPAWNER = register("trial_spawner")
        val VAULT = register("vault")


        private fun register(path: String): ResourceKey<BlockEntityType> {
            return Registries.BLOCK_ENTITY_TYPE.register(Key.key(path), BlockEntityType())
        }
    }

}