package dev.ng5m.item

import dev.ng5m.entity.EntityType
import net.kyori.adventure.key.Key
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey

object Items {
    private var map: MutableMap<Key, Item?> = mutableMapOf()

    val AIR: ResourceKey<Item> = register(Key.key("minecraft:air"), AirItem)

    val STONE: ResourceKey<Item> = register(Key.key("minecraft:stone"))

    val GRANITE: ResourceKey<Item> = register(Key.key("minecraft:granite"))

    val POLISHED_GRANITE: ResourceKey<Item> = register(Key.key("minecraft:polished_granite"))

    val DIORITE: ResourceKey<Item> = register(Key.key("minecraft:diorite"))

    val POLISHED_DIORITE: ResourceKey<Item> = register(Key.key("minecraft:polished_diorite"))

    val ANDESITE: ResourceKey<Item> = register(Key.key("minecraft:andesite"))

    val POLISHED_ANDESITE: ResourceKey<Item> = register(Key.key("minecraft:polished_andesite"))

    val DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:deepslate"))

    val COBBLED_DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:cobbled_deepslate"))

    val POLISHED_DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:polished_deepslate"))

    val CALCITE: ResourceKey<Item> = register(Key.key("minecraft:calcite"))

    val TUFF: ResourceKey<Item> = register(Key.key("minecraft:tuff"))

    val TUFF_SLAB: ResourceKey<Item> = register(Key.key("minecraft:tuff_slab"))

    val TUFF_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:tuff_stairs"))

    val TUFF_WALL: ResourceKey<Item> = register(Key.key("minecraft:tuff_wall"))

    val CHISELED_TUFF: ResourceKey<Item> = register(Key.key("minecraft:chiseled_tuff"))

    val POLISHED_TUFF: ResourceKey<Item> = register(Key.key("minecraft:polished_tuff"))

    val POLISHED_TUFF_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_tuff_slab"))

    val POLISHED_TUFF_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_tuff_stairs"))

    val POLISHED_TUFF_WALL: ResourceKey<Item> = register(Key.key("minecraft:polished_tuff_wall"))

    val TUFF_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:tuff_bricks"))

    val TUFF_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:tuff_brick_slab"))

    val TUFF_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:tuff_brick_stairs"))

    val TUFF_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:tuff_brick_wall"))

    val CHISELED_TUFF_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:chiseled_tuff_bricks"))

    val DRIPSTONE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dripstone_block"))

    val GRASS_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:grass_block"))

    val DIRT: ResourceKey<Item> = register(Key.key("minecraft:dirt"))

    val COARSE_DIRT: ResourceKey<Item> = register(Key.key("minecraft:coarse_dirt"))

    val PODZOL: ResourceKey<Item> = register(Key.key("minecraft:podzol"))

    val ROOTED_DIRT: ResourceKey<Item> = register(Key.key("minecraft:rooted_dirt"))

    val MUD: ResourceKey<Item> = register(Key.key("minecraft:mud"))

    val CRIMSON_NYLIUM: ResourceKey<Item> = register(Key.key("minecraft:crimson_nylium"))

    val WARPED_NYLIUM: ResourceKey<Item> = register(Key.key("minecraft:warped_nylium"))

    val COBBLESTONE: ResourceKey<Item> = register(Key.key("minecraft:cobblestone"))

    val OAK_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:oak_planks"))

    val SPRUCE_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:spruce_planks"))

    val BIRCH_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:birch_planks"))

    val JUNGLE_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:jungle_planks"))

    val ACACIA_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:acacia_planks"))

    val CHERRY_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:cherry_planks"))

    val DARK_OAK_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_planks"))

    val PALE_OAK_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_planks"))

    val MANGROVE_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:mangrove_planks"))

    val BAMBOO_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:bamboo_planks"))

    val CRIMSON_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:crimson_planks"))

    val WARPED_PLANKS: ResourceKey<Item> = register(Key.key("minecraft:warped_planks"))

    val BAMBOO_MOSAIC: ResourceKey<Item> = register(Key.key("minecraft:bamboo_mosaic"))

    val OAK_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:oak_sapling"))

    val SPRUCE_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:spruce_sapling"))

    val BIRCH_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:birch_sapling"))

    val JUNGLE_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:jungle_sapling"))

    val ACACIA_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:acacia_sapling"))

    val CHERRY_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:cherry_sapling"))

    val DARK_OAK_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_sapling"))

    val PALE_OAK_SAPLING: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_sapling"))

    val MANGROVE_PROPAGULE: ResourceKey<Item> = register(Key.key("minecraft:mangrove_propagule"))

    val BEDROCK: ResourceKey<Item> = register(Key.key("minecraft:bedrock"))

    val SAND: ResourceKey<Item> = register(Key.key("minecraft:sand"))

    val SUSPICIOUS_SAND: ResourceKey<Item> = register(Key.key("minecraft:suspicious_sand"))

    val SUSPICIOUS_GRAVEL: ResourceKey<Item> = register(Key.key("minecraft:suspicious_gravel"))

    val RED_SAND: ResourceKey<Item> = register(Key.key("minecraft:red_sand"))

    val GRAVEL: ResourceKey<Item> = register(Key.key("minecraft:gravel"))

    val COAL_ORE: ResourceKey<Item> = register(Key.key("minecraft:coal_ore"))

    val DEEPSLATE_COAL_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_coal_ore"))

    val IRON_ORE: ResourceKey<Item> = register(Key.key("minecraft:iron_ore"))

    val DEEPSLATE_IRON_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_iron_ore"))

    val COPPER_ORE: ResourceKey<Item> = register(Key.key("minecraft:copper_ore"))

    val DEEPSLATE_COPPER_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_copper_ore"))

    val GOLD_ORE: ResourceKey<Item> = register(Key.key("minecraft:gold_ore"))

    val DEEPSLATE_GOLD_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_gold_ore"))

    val REDSTONE_ORE: ResourceKey<Item> = register(Key.key("minecraft:redstone_ore"))

    val DEEPSLATE_REDSTONE_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_redstone_ore"))

    val EMERALD_ORE: ResourceKey<Item> = register(Key.key("minecraft:emerald_ore"))

    val DEEPSLATE_EMERALD_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_emerald_ore"))

    val LAPIS_ORE: ResourceKey<Item> = register(Key.key("minecraft:lapis_ore"))

    val DEEPSLATE_LAPIS_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_lapis_ore"))

    val DIAMOND_ORE: ResourceKey<Item> = register(Key.key("minecraft:diamond_ore"))

    val DEEPSLATE_DIAMOND_ORE: ResourceKey<Item> = register(Key.key("minecraft:deepslate_diamond_ore"))

    val NETHER_GOLD_ORE: ResourceKey<Item> = register(Key.key("minecraft:nether_gold_ore"))

    val NETHER_QUARTZ_ORE: ResourceKey<Item> = register(Key.key("minecraft:nether_quartz_ore"))

    val ANCIENT_DEBRIS: ResourceKey<Item> = register(Key.key("minecraft:ancient_debris"))

    val COAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:coal_block"))

    val RAW_IRON_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:raw_iron_block"))

    val RAW_COPPER_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:raw_copper_block"))

    val RAW_GOLD_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:raw_gold_block"))

    val HEAVY_CORE: ResourceKey<Item> = register(Key.key("minecraft:heavy_core"))

    val AMETHYST_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:amethyst_block"))

    val BUDDING_AMETHYST: ResourceKey<Item> = register(Key.key("minecraft:budding_amethyst"))

    val IRON_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:iron_block"))

    val COPPER_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:copper_block"))

    val GOLD_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:gold_block"))

    val DIAMOND_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:diamond_block"))

    val NETHERITE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:netherite_block"))

    val EXPOSED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:exposed_copper"))

    val WEATHERED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:weathered_copper"))

    val OXIDIZED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:oxidized_copper"))

    val CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:chiseled_copper"))

    val EXPOSED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:exposed_chiseled_copper"))

    val WEATHERED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:weathered_chiseled_copper"))

    val OXIDIZED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:oxidized_chiseled_copper"))

    val CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:cut_copper"))

    val EXPOSED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:exposed_cut_copper"))

    val WEATHERED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:weathered_cut_copper"))

    val OXIDIZED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:oxidized_cut_copper"))

    val CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:cut_copper_stairs"))

    val EXPOSED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:exposed_cut_copper_stairs"))

    val WEATHERED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:weathered_cut_copper_stairs"))

    val OXIDIZED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:oxidized_cut_copper_stairs"))

    val CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cut_copper_slab"))

    val EXPOSED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:exposed_cut_copper_slab"))

    val WEATHERED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:weathered_cut_copper_slab"))

    val OXIDIZED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:oxidized_cut_copper_slab"))

    val WAXED_COPPER_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:waxed_copper_block"))

    val WAXED_EXPOSED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_copper"))

    val WAXED_WEATHERED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_copper"))

    val WAXED_OXIDIZED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_copper"))

    val WAXED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_chiseled_copper"))

    val WAXED_EXPOSED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_chiseled_copper"))

    val WAXED_WEATHERED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_chiseled_copper"))

    val WAXED_OXIDIZED_CHISELED_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_chiseled_copper"))

    val WAXED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_cut_copper"))

    val WAXED_EXPOSED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_cut_copper"))

    val WAXED_WEATHERED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_cut_copper"))

    val WAXED_OXIDIZED_CUT_COPPER: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_cut_copper"))

    val WAXED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:waxed_cut_copper_stairs"))

    val WAXED_EXPOSED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_cut_copper_stairs"))

    val WAXED_WEATHERED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_cut_copper_stairs"))

    val WAXED_OXIDIZED_CUT_COPPER_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_cut_copper_stairs"))

    val WAXED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:waxed_cut_copper_slab"))

    val WAXED_EXPOSED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_cut_copper_slab"))

    val WAXED_WEATHERED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_cut_copper_slab"))

    val WAXED_OXIDIZED_CUT_COPPER_SLAB: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_cut_copper_slab"))

    val OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:oak_log"))

    val SPRUCE_LOG: ResourceKey<Item> = register(Key.key("minecraft:spruce_log"))

    val BIRCH_LOG: ResourceKey<Item> = register(Key.key("minecraft:birch_log"))

    val JUNGLE_LOG: ResourceKey<Item> = register(Key.key("minecraft:jungle_log"))

    val ACACIA_LOG: ResourceKey<Item> = register(Key.key("minecraft:acacia_log"))

    val CHERRY_LOG: ResourceKey<Item> = register(Key.key("minecraft:cherry_log"))

    val PALE_OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_log"))

    val DARK_OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_log"))

    val MANGROVE_LOG: ResourceKey<Item> = register(Key.key("minecraft:mangrove_log"))

    val MANGROVE_ROOTS: ResourceKey<Item> = register(Key.key("minecraft:mangrove_roots"))

    val MUDDY_MANGROVE_ROOTS: ResourceKey<Item> = register(Key.key("minecraft:muddy_mangrove_roots"))

    val CRIMSON_STEM: ResourceKey<Item> = register(Key.key("minecraft:crimson_stem"))

    val WARPED_STEM: ResourceKey<Item> = register(Key.key("minecraft:warped_stem"))

    val BAMBOO_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:bamboo_block"))

    val STRIPPED_OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_oak_log"))

    val STRIPPED_SPRUCE_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_spruce_log"))

    val STRIPPED_BIRCH_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_birch_log"))

    val STRIPPED_JUNGLE_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_jungle_log"))

    val STRIPPED_ACACIA_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_acacia_log"))

    val STRIPPED_CHERRY_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_cherry_log"))

    val STRIPPED_DARK_OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_dark_oak_log"))

    val STRIPPED_PALE_OAK_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_pale_oak_log"))

    val STRIPPED_MANGROVE_LOG: ResourceKey<Item> = register(Key.key("minecraft:stripped_mangrove_log"))

    val STRIPPED_CRIMSON_STEM: ResourceKey<Item> = register(Key.key("minecraft:stripped_crimson_stem"))

    val STRIPPED_WARPED_STEM: ResourceKey<Item> = register(Key.key("minecraft:stripped_warped_stem"))

    val STRIPPED_OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_oak_wood"))

    val STRIPPED_SPRUCE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_spruce_wood"))

    val STRIPPED_BIRCH_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_birch_wood"))

    val STRIPPED_JUNGLE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_jungle_wood"))

    val STRIPPED_ACACIA_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_acacia_wood"))

    val STRIPPED_CHERRY_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_cherry_wood"))

    val STRIPPED_DARK_OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_dark_oak_wood"))

    val STRIPPED_PALE_OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_pale_oak_wood"))

    val STRIPPED_MANGROVE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:stripped_mangrove_wood"))

    val STRIPPED_CRIMSON_HYPHAE: ResourceKey<Item> = register(Key.key("minecraft:stripped_crimson_hyphae"))

    val STRIPPED_WARPED_HYPHAE: ResourceKey<Item> = register(Key.key("minecraft:stripped_warped_hyphae"))

    val STRIPPED_BAMBOO_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:stripped_bamboo_block"))

    val OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:oak_wood"))

    val SPRUCE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:spruce_wood"))

    val BIRCH_WOOD: ResourceKey<Item> = register(Key.key("minecraft:birch_wood"))

    val JUNGLE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:jungle_wood"))

    val ACACIA_WOOD: ResourceKey<Item> = register(Key.key("minecraft:acacia_wood"))

    val CHERRY_WOOD: ResourceKey<Item> = register(Key.key("minecraft:cherry_wood"))

    val PALE_OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_wood"))

    val DARK_OAK_WOOD: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_wood"))

    val MANGROVE_WOOD: ResourceKey<Item> = register(Key.key("minecraft:mangrove_wood"))

    val CRIMSON_HYPHAE: ResourceKey<Item> = register(Key.key("minecraft:crimson_hyphae"))

    val WARPED_HYPHAE: ResourceKey<Item> = register(Key.key("minecraft:warped_hyphae"))

    val OAK_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:oak_leaves"))

    val SPRUCE_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:spruce_leaves"))

    val BIRCH_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:birch_leaves"))

    val JUNGLE_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:jungle_leaves"))

    val ACACIA_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:acacia_leaves"))

    val CHERRY_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:cherry_leaves"))

    val DARK_OAK_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_leaves"))

    val PALE_OAK_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_leaves"))

    val MANGROVE_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:mangrove_leaves"))

    val AZALEA_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:azalea_leaves"))

    val FLOWERING_AZALEA_LEAVES: ResourceKey<Item> = register(Key.key("minecraft:flowering_azalea_leaves"))

    val SPONGE: ResourceKey<Item> = register(Key.key("minecraft:sponge"))

    val WET_SPONGE: ResourceKey<Item> = register(Key.key("minecraft:wet_sponge"))

    val GLASS: ResourceKey<Item> = register(Key.key("minecraft:glass"))

    val TINTED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:tinted_glass"))

    val LAPIS_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:lapis_block"))

    val SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:sandstone"))

    val CHISELED_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:chiseled_sandstone"))

    val CUT_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:cut_sandstone"))

    val COBWEB: ResourceKey<Item> = register(Key.key("minecraft:cobweb"))

    val SHORT_GRASS: ResourceKey<Item> = register(Key.key("minecraft:short_grass"))

    val FERN: ResourceKey<Item> = register(Key.key("minecraft:fern"))

    val AZALEA: ResourceKey<Item> = register(Key.key("minecraft:azalea"))

    val FLOWERING_AZALEA: ResourceKey<Item> = register(Key.key("minecraft:flowering_azalea"))

    val DEAD_BUSH: ResourceKey<Item> = register(Key.key("minecraft:dead_bush"))

    val SEAGRASS: ResourceKey<Item> = register(Key.key("minecraft:seagrass"))

    val SEA_PICKLE: ResourceKey<Item> = register(Key.key("minecraft:sea_pickle"))

    val WHITE_WOOL: ResourceKey<Item> = register(Key.key("minecraft:white_wool"))

    val ORANGE_WOOL: ResourceKey<Item> = register(Key.key("minecraft:orange_wool"))

    val MAGENTA_WOOL: ResourceKey<Item> = register(Key.key("minecraft:magenta_wool"))

    val LIGHT_BLUE_WOOL: ResourceKey<Item> = register(Key.key("minecraft:light_blue_wool"))

    val YELLOW_WOOL: ResourceKey<Item> = register(Key.key("minecraft:yellow_wool"))

    val LIME_WOOL: ResourceKey<Item> = register(Key.key("minecraft:lime_wool"))

    val PINK_WOOL: ResourceKey<Item> = register(Key.key("minecraft:pink_wool"))

    val GRAY_WOOL: ResourceKey<Item> = register(Key.key("minecraft:gray_wool"))

    val LIGHT_GRAY_WOOL: ResourceKey<Item> = register(Key.key("minecraft:light_gray_wool"))

    val CYAN_WOOL: ResourceKey<Item> = register(Key.key("minecraft:cyan_wool"))

    val PURPLE_WOOL: ResourceKey<Item> = register(Key.key("minecraft:purple_wool"))

    val BLUE_WOOL: ResourceKey<Item> = register(Key.key("minecraft:blue_wool"))

    val BROWN_WOOL: ResourceKey<Item> = register(Key.key("minecraft:brown_wool"))

    val GREEN_WOOL: ResourceKey<Item> = register(Key.key("minecraft:green_wool"))

    val RED_WOOL: ResourceKey<Item> = register(Key.key("minecraft:red_wool"))

    val BLACK_WOOL: ResourceKey<Item> = register(Key.key("minecraft:black_wool"))

    val DANDELION: ResourceKey<Item> = register(Key.key("minecraft:dandelion"))

    val OPEN_EYEBLOSSOM: ResourceKey<Item> = register(Key.key("minecraft:open_eyeblossom"))

    val CLOSED_EYEBLOSSOM: ResourceKey<Item> = register(Key.key("minecraft:closed_eyeblossom"))

    val POPPY: ResourceKey<Item> = register(Key.key("minecraft:poppy"))

    val BLUE_ORCHID: ResourceKey<Item> = register(Key.key("minecraft:blue_orchid"))

    val ALLIUM: ResourceKey<Item> = register(Key.key("minecraft:allium"))

    val AZURE_BLUET: ResourceKey<Item> = register(Key.key("minecraft:azure_bluet"))

    val RED_TULIP: ResourceKey<Item> = register(Key.key("minecraft:red_tulip"))

    val ORANGE_TULIP: ResourceKey<Item> = register(Key.key("minecraft:orange_tulip"))

    val WHITE_TULIP: ResourceKey<Item> = register(Key.key("minecraft:white_tulip"))

    val PINK_TULIP: ResourceKey<Item> = register(Key.key("minecraft:pink_tulip"))

    val OXEYE_DAISY: ResourceKey<Item> = register(Key.key("minecraft:oxeye_daisy"))

    val CORNFLOWER: ResourceKey<Item> = register(Key.key("minecraft:cornflower"))

    val LILY_OF_THE_VALLEY: ResourceKey<Item> = register(Key.key("minecraft:lily_of_the_valley"))

    val WITHER_ROSE: ResourceKey<Item> = register(Key.key("minecraft:wither_rose"))

    val TORCHFLOWER: ResourceKey<Item> = register(Key.key("minecraft:torchflower"))

    val PITCHER_PLANT: ResourceKey<Item> = register(Key.key("minecraft:pitcher_plant"))

    val SPORE_BLOSSOM: ResourceKey<Item> = register(Key.key("minecraft:spore_blossom"))

    val BROWN_MUSHROOM: ResourceKey<Item> = register(Key.key("minecraft:brown_mushroom"))

    val RED_MUSHROOM: ResourceKey<Item> = register(Key.key("minecraft:red_mushroom"))

    val CRIMSON_FUNGUS: ResourceKey<Item> = register(Key.key("minecraft:crimson_fungus"))

    val WARPED_FUNGUS: ResourceKey<Item> = register(Key.key("minecraft:warped_fungus"))

    val CRIMSON_ROOTS: ResourceKey<Item> = register(Key.key("minecraft:crimson_roots"))

    val WARPED_ROOTS: ResourceKey<Item> = register(Key.key("minecraft:warped_roots"))

    val NETHER_SPROUTS: ResourceKey<Item> = register(Key.key("minecraft:nether_sprouts"))

    val WEEPING_VINES: ResourceKey<Item> = register(Key.key("minecraft:weeping_vines"))

    val TWISTING_VINES: ResourceKey<Item> = register(Key.key("minecraft:twisting_vines"))

    val SUGAR_CANE: ResourceKey<Item> = register(Key.key("minecraft:sugar_cane"))

    val KELP: ResourceKey<Item> = register(Key.key("minecraft:kelp"))

    val PINK_PETALS: ResourceKey<Item> = register(Key.key("minecraft:pink_petals"))

    val MOSS_CARPET: ResourceKey<Item> = register(Key.key("minecraft:moss_carpet"))

    val MOSS_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:moss_block"))

    val PALE_MOSS_CARPET: ResourceKey<Item> = register(Key.key("minecraft:pale_moss_carpet"))

    val PALE_HANGING_MOSS: ResourceKey<Item> = register(Key.key("minecraft:pale_hanging_moss"))

    val PALE_MOSS_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:pale_moss_block"))

    val HANGING_ROOTS: ResourceKey<Item> = register(Key.key("minecraft:hanging_roots"))

    val BIG_DRIPLEAF: ResourceKey<Item> = register(Key.key("minecraft:big_dripleaf"))

    val SMALL_DRIPLEAF: ResourceKey<Item> = register(Key.key("minecraft:small_dripleaf"))

    val BAMBOO: ResourceKey<Item> = register(Key.key("minecraft:bamboo"))

    val OAK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:oak_slab"))

    val SPRUCE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:spruce_slab"))

    val BIRCH_SLAB: ResourceKey<Item> = register(Key.key("minecraft:birch_slab"))

    val JUNGLE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:jungle_slab"))

    val ACACIA_SLAB: ResourceKey<Item> = register(Key.key("minecraft:acacia_slab"))

    val CHERRY_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cherry_slab"))

    val DARK_OAK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_slab"))

    val PALE_OAK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_slab"))

    val MANGROVE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:mangrove_slab"))

    val BAMBOO_SLAB: ResourceKey<Item> = register(Key.key("minecraft:bamboo_slab"))

    val BAMBOO_MOSAIC_SLAB: ResourceKey<Item> = register(Key.key("minecraft:bamboo_mosaic_slab"))

    val CRIMSON_SLAB: ResourceKey<Item> = register(Key.key("minecraft:crimson_slab"))

    val WARPED_SLAB: ResourceKey<Item> = register(Key.key("minecraft:warped_slab"))

    val STONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:stone_slab"))

    val SMOOTH_STONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:smooth_stone_slab"))

    val SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:sandstone_slab"))

    val CUT_SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cut_sandstone_slab"))

    val PETRIFIED_OAK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:petrified_oak_slab"))

    val COBBLESTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cobblestone_slab"))

    val BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:brick_slab"))

    val STONE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:stone_brick_slab"))

    val MUD_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:mud_brick_slab"))

    val NETHER_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:nether_brick_slab"))

    val QUARTZ_SLAB: ResourceKey<Item> = register(Key.key("minecraft:quartz_slab"))

    val RED_SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:red_sandstone_slab"))

    val CUT_RED_SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cut_red_sandstone_slab"))

    val PURPUR_SLAB: ResourceKey<Item> = register(Key.key("minecraft:purpur_slab"))

    val PRISMARINE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:prismarine_slab"))

    val PRISMARINE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:prismarine_brick_slab"))

    val DARK_PRISMARINE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:dark_prismarine_slab"))

    val SMOOTH_QUARTZ: ResourceKey<Item> = register(Key.key("minecraft:smooth_quartz"))

    val SMOOTH_RED_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:smooth_red_sandstone"))

    val SMOOTH_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:smooth_sandstone"))

    val SMOOTH_STONE: ResourceKey<Item> = register(Key.key("minecraft:smooth_stone"))

    val BRICKS: ResourceKey<Item> = register(Key.key("minecraft:bricks"))

    val BOOKSHELF: ResourceKey<Item> = register(Key.key("minecraft:bookshelf"))

    val CHISELED_BOOKSHELF: ResourceKey<Item> = register(Key.key("minecraft:chiseled_bookshelf"))

    val DECORATED_POT: ResourceKey<Item> = register(Key.key("minecraft:decorated_pot"))

    val MOSSY_COBBLESTONE: ResourceKey<Item> = register(Key.key("minecraft:mossy_cobblestone"))

    val OBSIDIAN: ResourceKey<Item> = register(Key.key("minecraft:obsidian"))

    val TORCH: ResourceKey<Item> = register(Key.key("minecraft:torch"))

    val END_ROD: ResourceKey<Item> = register(Key.key("minecraft:end_rod"))

    val CHORUS_PLANT: ResourceKey<Item> = register(Key.key("minecraft:chorus_plant"))

    val CHORUS_FLOWER: ResourceKey<Item> = register(Key.key("minecraft:chorus_flower"))

    val PURPUR_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:purpur_block"))

    val PURPUR_PILLAR: ResourceKey<Item> = register(Key.key("minecraft:purpur_pillar"))

    val PURPUR_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:purpur_stairs"))

    val SPAWNER: ResourceKey<Item> = register(Key.key("minecraft:spawner"))

    val CREAKING_HEART: ResourceKey<Item> = register(Key.key("minecraft:creaking_heart"))

    val CHEST: ResourceKey<Item> = register(Key.key("minecraft:chest"))

    val CRAFTING_TABLE: ResourceKey<Item> = register(Key.key("minecraft:crafting_table"))

    val FARMLAND: ResourceKey<Item> = register(Key.key("minecraft:farmland"))

    val FURNACE: ResourceKey<Item> = register(Key.key("minecraft:furnace"))

    val LADDER: ResourceKey<Item> = register(Key.key("minecraft:ladder"))

    val COBBLESTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:cobblestone_stairs"))

    val SNOW: ResourceKey<Item> = register(Key.key("minecraft:snow"))

    val ICE: ResourceKey<Item> = register(Key.key("minecraft:ice"))

    val SNOW_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:snow_block"))

    val CACTUS: ResourceKey<Item> = register(Key.key("minecraft:cactus"))

    val CLAY: ResourceKey<Item> = register(Key.key("minecraft:clay"))

    val JUKEBOX: ResourceKey<Item> = register(Key.key("minecraft:jukebox"))

    val OAK_FENCE: ResourceKey<Item> = register(Key.key("minecraft:oak_fence"))

    val SPRUCE_FENCE: ResourceKey<Item> = register(Key.key("minecraft:spruce_fence"))

    val BIRCH_FENCE: ResourceKey<Item> = register(Key.key("minecraft:birch_fence"))

    val JUNGLE_FENCE: ResourceKey<Item> = register(Key.key("minecraft:jungle_fence"))

    val ACACIA_FENCE: ResourceKey<Item> = register(Key.key("minecraft:acacia_fence"))

    val CHERRY_FENCE: ResourceKey<Item> = register(Key.key("minecraft:cherry_fence"))

    val DARK_OAK_FENCE: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_fence"))

    val PALE_OAK_FENCE: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_fence"))

    val MANGROVE_FENCE: ResourceKey<Item> = register(Key.key("minecraft:mangrove_fence"))

    val BAMBOO_FENCE: ResourceKey<Item> = register(Key.key("minecraft:bamboo_fence"))

    val CRIMSON_FENCE: ResourceKey<Item> = register(Key.key("minecraft:crimson_fence"))

    val WARPED_FENCE: ResourceKey<Item> = register(Key.key("minecraft:warped_fence"))

    val PUMPKIN: ResourceKey<Item> = register(Key.key("minecraft:pumpkin"))

    val CARVED_PUMPKIN: ResourceKey<Item> = register(Key.key("minecraft:carved_pumpkin"))

    val JACK_O_LANTERN: ResourceKey<Item> = register(Key.key("minecraft:jack_o_lantern"))

    val NETHERRACK: ResourceKey<Item> = register(Key.key("minecraft:netherrack"))

    val SOUL_SAND: ResourceKey<Item> = register(Key.key("minecraft:soul_sand"))

    val SOUL_SOIL: ResourceKey<Item> = register(Key.key("minecraft:soul_soil"))

    val BASALT: ResourceKey<Item> = register(Key.key("minecraft:basalt"))

    val POLISHED_BASALT: ResourceKey<Item> = register(Key.key("minecraft:polished_basalt"))

    val SMOOTH_BASALT: ResourceKey<Item> = register(Key.key("minecraft:smooth_basalt"))

    val SOUL_TORCH: ResourceKey<Item> = register(Key.key("minecraft:soul_torch"))

    val GLOWSTONE: ResourceKey<Item> = register(Key.key("minecraft:glowstone"))

    val INFESTED_STONE: ResourceKey<Item> = register(Key.key("minecraft:infested_stone"))

    val INFESTED_COBBLESTONE: ResourceKey<Item> = register(Key.key("minecraft:infested_cobblestone"))

    val INFESTED_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:infested_stone_bricks"))

    val INFESTED_MOSSY_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:infested_mossy_stone_bricks"))

    val INFESTED_CRACKED_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:infested_cracked_stone_bricks"))

    val INFESTED_CHISELED_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:infested_chiseled_stone_bricks"))

    val INFESTED_DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:infested_deepslate"))

    val STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:stone_bricks"))

    val MOSSY_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:mossy_stone_bricks"))

    val CRACKED_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:cracked_stone_bricks"))

    val CHISELED_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:chiseled_stone_bricks"))

    val PACKED_MUD: ResourceKey<Item> = register(Key.key("minecraft:packed_mud"))

    val MUD_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:mud_bricks"))

    val DEEPSLATE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:deepslate_bricks"))

    val CRACKED_DEEPSLATE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:cracked_deepslate_bricks"))

    val DEEPSLATE_TILES: ResourceKey<Item> = register(Key.key("minecraft:deepslate_tiles"))

    val CRACKED_DEEPSLATE_TILES: ResourceKey<Item> = register(Key.key("minecraft:cracked_deepslate_tiles"))

    val CHISELED_DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:chiseled_deepslate"))

    val REINFORCED_DEEPSLATE: ResourceKey<Item> = register(Key.key("minecraft:reinforced_deepslate"))

    val BROWN_MUSHROOM_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:brown_mushroom_block"))

    val RED_MUSHROOM_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:red_mushroom_block"))

    val MUSHROOM_STEM: ResourceKey<Item> = register(Key.key("minecraft:mushroom_stem"))

    val IRON_BARS: ResourceKey<Item> = register(Key.key("minecraft:iron_bars"))

    val CHAIN: ResourceKey<Item> = register(Key.key("minecraft:chain"))

    val GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:glass_pane"))

    val MELON: ResourceKey<Item> = register(Key.key("minecraft:melon"))

    val VINE: ResourceKey<Item> = register(Key.key("minecraft:vine"))

    val GLOW_LICHEN: ResourceKey<Item> = register(Key.key("minecraft:glow_lichen"))

    val RESIN_CLUMP: ResourceKey<Item> = register(Key.key("minecraft:resin_clump"))

    val RESIN_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:resin_block"))

    val RESIN_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:resin_bricks"))

    val RESIN_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:resin_brick_stairs"))

    val RESIN_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:resin_brick_slab"))

    val RESIN_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:resin_brick_wall"))

    val CHISELED_RESIN_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:chiseled_resin_bricks"))

    val BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:brick_stairs"))

    val STONE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:stone_brick_stairs"))

    val MUD_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:mud_brick_stairs"))

    val MYCELIUM: ResourceKey<Item> = register(Key.key("minecraft:mycelium"))

    val LILY_PAD: ResourceKey<Item> = register(Key.key("minecraft:lily_pad"))

    val NETHER_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:nether_bricks"))

    val CRACKED_NETHER_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:cracked_nether_bricks"))

    val CHISELED_NETHER_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:chiseled_nether_bricks"))

    val NETHER_BRICK_FENCE: ResourceKey<Item> = register(Key.key("minecraft:nether_brick_fence"))

    val NETHER_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:nether_brick_stairs"))

    val SCULK: ResourceKey<Item> = register(Key.key("minecraft:sculk"))

    val SCULK_VEIN: ResourceKey<Item> = register(Key.key("minecraft:sculk_vein"))

    val SCULK_CATALYST: ResourceKey<Item> = register(Key.key("minecraft:sculk_catalyst"))

    val SCULK_SHRIEKER: ResourceKey<Item> = register(Key.key("minecraft:sculk_shrieker"))

    val ENCHANTING_TABLE: ResourceKey<Item> = register(Key.key("minecraft:enchanting_table"))

    val END_PORTAL_FRAME: ResourceKey<Item> = register(Key.key("minecraft:end_portal_frame"))

    val END_STONE: ResourceKey<Item> = register(Key.key("minecraft:end_stone"))

    val END_STONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:end_stone_bricks"))

    val DRAGON_EGG: ResourceKey<Item> = register(Key.key("minecraft:dragon_egg"))

    val SANDSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:sandstone_stairs"))

    val ENDER_CHEST: ResourceKey<Item> = register(Key.key("minecraft:ender_chest"))

    val EMERALD_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:emerald_block"))

    val OAK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:oak_stairs"))

    val SPRUCE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:spruce_stairs"))

    val BIRCH_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:birch_stairs"))

    val JUNGLE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:jungle_stairs"))

    val ACACIA_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:acacia_stairs"))

    val CHERRY_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:cherry_stairs"))

    val DARK_OAK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_stairs"))

    val PALE_OAK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_stairs"))

    val MANGROVE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:mangrove_stairs"))

    val BAMBOO_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:bamboo_stairs"))

    val BAMBOO_MOSAIC_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:bamboo_mosaic_stairs"))

    val CRIMSON_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:crimson_stairs"))

    val WARPED_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:warped_stairs"))

    val COMMAND_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:command_block"))

    val BEACON: ResourceKey<Item> = register(Key.key("minecraft:beacon"))

    val COBBLESTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:cobblestone_wall"))

    val MOSSY_COBBLESTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:mossy_cobblestone_wall"))

    val BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:brick_wall"))

    val PRISMARINE_WALL: ResourceKey<Item> = register(Key.key("minecraft:prismarine_wall"))

    val RED_SANDSTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:red_sandstone_wall"))

    val MOSSY_STONE_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:mossy_stone_brick_wall"))

    val GRANITE_WALL: ResourceKey<Item> = register(Key.key("minecraft:granite_wall"))

    val STONE_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:stone_brick_wall"))

    val MUD_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:mud_brick_wall"))

    val NETHER_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:nether_brick_wall"))

    val ANDESITE_WALL: ResourceKey<Item> = register(Key.key("minecraft:andesite_wall"))

    val RED_NETHER_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:red_nether_brick_wall"))

    val SANDSTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:sandstone_wall"))

    val END_STONE_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:end_stone_brick_wall"))

    val DIORITE_WALL: ResourceKey<Item> = register(Key.key("minecraft:diorite_wall"))

    val BLACKSTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:blackstone_wall"))

    val POLISHED_BLACKSTONE_WALL: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_wall"))

    val POLISHED_BLACKSTONE_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_brick_wall"))

    val COBBLED_DEEPSLATE_WALL: ResourceKey<Item> = register(Key.key("minecraft:cobbled_deepslate_wall"))

    val POLISHED_DEEPSLATE_WALL: ResourceKey<Item> = register(Key.key("minecraft:polished_deepslate_wall"))

    val DEEPSLATE_BRICK_WALL: ResourceKey<Item> = register(Key.key("minecraft:deepslate_brick_wall"))

    val DEEPSLATE_TILE_WALL: ResourceKey<Item> = register(Key.key("minecraft:deepslate_tile_wall"))

    val ANVIL: ResourceKey<Item> = register(Key.key("minecraft:anvil"))

    val CHIPPED_ANVIL: ResourceKey<Item> = register(Key.key("minecraft:chipped_anvil"))

    val DAMAGED_ANVIL: ResourceKey<Item> = register(Key.key("minecraft:damaged_anvil"))

    val CHISELED_QUARTZ_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:chiseled_quartz_block"))

    val QUARTZ_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:quartz_block"))

    val QUARTZ_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:quartz_bricks"))

    val QUARTZ_PILLAR: ResourceKey<Item> = register(Key.key("minecraft:quartz_pillar"))

    val QUARTZ_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:quartz_stairs"))

    val WHITE_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:white_terracotta"))

    val ORANGE_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:orange_terracotta"))

    val MAGENTA_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:magenta_terracotta"))

    val LIGHT_BLUE_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:light_blue_terracotta"))

    val YELLOW_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:yellow_terracotta"))

    val LIME_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:lime_terracotta"))

    val PINK_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:pink_terracotta"))

    val GRAY_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:gray_terracotta"))

    val LIGHT_GRAY_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:light_gray_terracotta"))

    val CYAN_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:cyan_terracotta"))

    val PURPLE_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:purple_terracotta"))

    val BLUE_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:blue_terracotta"))

    val BROWN_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:brown_terracotta"))

    val GREEN_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:green_terracotta"))

    val RED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:red_terracotta"))

    val BLACK_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:black_terracotta"))

    val BARRIER: ResourceKey<Item> = register(Key.key("minecraft:barrier"))

    val LIGHT: ResourceKey<Item> = register(Key.key("minecraft:light"))

    val HAY_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:hay_block"))

    val WHITE_CARPET: ResourceKey<Item> = register(Key.key("minecraft:white_carpet"))

    val ORANGE_CARPET: ResourceKey<Item> = register(Key.key("minecraft:orange_carpet"))

    val MAGENTA_CARPET: ResourceKey<Item> = register(Key.key("minecraft:magenta_carpet"))

    val LIGHT_BLUE_CARPET: ResourceKey<Item> = register(Key.key("minecraft:light_blue_carpet"))

    val YELLOW_CARPET: ResourceKey<Item> = register(Key.key("minecraft:yellow_carpet"))

    val LIME_CARPET: ResourceKey<Item> = register(Key.key("minecraft:lime_carpet"))

    val PINK_CARPET: ResourceKey<Item> = register(Key.key("minecraft:pink_carpet"))

    val GRAY_CARPET: ResourceKey<Item> = register(Key.key("minecraft:gray_carpet"))

    val LIGHT_GRAY_CARPET: ResourceKey<Item> = register(Key.key("minecraft:light_gray_carpet"))

    val CYAN_CARPET: ResourceKey<Item> = register(Key.key("minecraft:cyan_carpet"))

    val PURPLE_CARPET: ResourceKey<Item> = register(Key.key("minecraft:purple_carpet"))

    val BLUE_CARPET: ResourceKey<Item> = register(Key.key("minecraft:blue_carpet"))

    val BROWN_CARPET: ResourceKey<Item> = register(Key.key("minecraft:brown_carpet"))

    val GREEN_CARPET: ResourceKey<Item> = register(Key.key("minecraft:green_carpet"))

    val RED_CARPET: ResourceKey<Item> = register(Key.key("minecraft:red_carpet"))

    val BLACK_CARPET: ResourceKey<Item> = register(Key.key("minecraft:black_carpet"))

    val TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:terracotta"))

    val PACKED_ICE: ResourceKey<Item> = register(Key.key("minecraft:packed_ice"))

    val DIRT_PATH: ResourceKey<Item> = register(Key.key("minecraft:dirt_path"))

    val SUNFLOWER: ResourceKey<Item> = register(Key.key("minecraft:sunflower"))

    val LILAC: ResourceKey<Item> = register(Key.key("minecraft:lilac"))

    val ROSE_BUSH: ResourceKey<Item> = register(Key.key("minecraft:rose_bush"))

    val PEONY: ResourceKey<Item> = register(Key.key("minecraft:peony"))

    val TALL_GRASS: ResourceKey<Item> = register(Key.key("minecraft:tall_grass"))

    val LARGE_FERN: ResourceKey<Item> = register(Key.key("minecraft:large_fern"))

    val WHITE_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:white_stained_glass"))

    val ORANGE_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:orange_stained_glass"))

    val MAGENTA_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:magenta_stained_glass"))

    val LIGHT_BLUE_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:light_blue_stained_glass"))

    val YELLOW_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:yellow_stained_glass"))

    val LIME_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:lime_stained_glass"))

    val PINK_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:pink_stained_glass"))

    val GRAY_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:gray_stained_glass"))

    val LIGHT_GRAY_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:light_gray_stained_glass"))

    val CYAN_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:cyan_stained_glass"))

    val PURPLE_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:purple_stained_glass"))

    val BLUE_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:blue_stained_glass"))

    val BROWN_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:brown_stained_glass"))

    val GREEN_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:green_stained_glass"))

    val RED_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:red_stained_glass"))

    val BLACK_STAINED_GLASS: ResourceKey<Item> = register(Key.key("minecraft:black_stained_glass"))

    val WHITE_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:white_stained_glass_pane"))

    val ORANGE_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:orange_stained_glass_pane"))

    val MAGENTA_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:magenta_stained_glass_pane"))

    val LIGHT_BLUE_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:light_blue_stained_glass_pane"))

    val YELLOW_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:yellow_stained_glass_pane"))

    val LIME_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:lime_stained_glass_pane"))

    val PINK_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:pink_stained_glass_pane"))

    val GRAY_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:gray_stained_glass_pane"))

    val LIGHT_GRAY_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:light_gray_stained_glass_pane"))

    val CYAN_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:cyan_stained_glass_pane"))

    val PURPLE_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:purple_stained_glass_pane"))

    val BLUE_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:blue_stained_glass_pane"))

    val BROWN_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:brown_stained_glass_pane"))

    val GREEN_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:green_stained_glass_pane"))

    val RED_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:red_stained_glass_pane"))

    val BLACK_STAINED_GLASS_PANE: ResourceKey<Item> = register(Key.key("minecraft:black_stained_glass_pane"))

    val PRISMARINE: ResourceKey<Item> = register(Key.key("minecraft:prismarine"))

    val PRISMARINE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:prismarine_bricks"))

    val DARK_PRISMARINE: ResourceKey<Item> = register(Key.key("minecraft:dark_prismarine"))

    val PRISMARINE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:prismarine_stairs"))

    val PRISMARINE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:prismarine_brick_stairs"))

    val DARK_PRISMARINE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:dark_prismarine_stairs"))

    val SEA_LANTERN: ResourceKey<Item> = register(Key.key("minecraft:sea_lantern"))

    val RED_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:red_sandstone"))

    val CHISELED_RED_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:chiseled_red_sandstone"))

    val CUT_RED_SANDSTONE: ResourceKey<Item> = register(Key.key("minecraft:cut_red_sandstone"))

    val RED_SANDSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:red_sandstone_stairs"))

    val REPEATING_COMMAND_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:repeating_command_block"))

    val CHAIN_COMMAND_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:chain_command_block"))

    val MAGMA_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:magma_block"))

    val NETHER_WART_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:nether_wart_block"))

    val WARPED_WART_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:warped_wart_block"))

    val RED_NETHER_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:red_nether_bricks"))

    val BONE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:bone_block"))

    val STRUCTURE_VOID: ResourceKey<Item> = register(Key.key("minecraft:structure_void"))

    val SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:shulker_box"))

    val WHITE_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:white_shulker_box"))

    val ORANGE_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:orange_shulker_box"))

    val MAGENTA_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:magenta_shulker_box"))

    val LIGHT_BLUE_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:light_blue_shulker_box"))

    val YELLOW_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:yellow_shulker_box"))

    val LIME_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:lime_shulker_box"))

    val PINK_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:pink_shulker_box"))

    val GRAY_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:gray_shulker_box"))

    val LIGHT_GRAY_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:light_gray_shulker_box"))

    val CYAN_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:cyan_shulker_box"))

    val PURPLE_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:purple_shulker_box"))

    val BLUE_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:blue_shulker_box"))

    val BROWN_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:brown_shulker_box"))

    val GREEN_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:green_shulker_box"))

    val RED_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:red_shulker_box"))

    val BLACK_SHULKER_BOX: ResourceKey<Item> = register(Key.key("minecraft:black_shulker_box"))

    val WHITE_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:white_glazed_terracotta"))

    val ORANGE_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:orange_glazed_terracotta"))

    val MAGENTA_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:magenta_glazed_terracotta"))

    val LIGHT_BLUE_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:light_blue_glazed_terracotta"))

    val YELLOW_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:yellow_glazed_terracotta"))

    val LIME_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:lime_glazed_terracotta"))

    val PINK_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:pink_glazed_terracotta"))

    val GRAY_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:gray_glazed_terracotta"))

    val LIGHT_GRAY_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:light_gray_glazed_terracotta"))

    val CYAN_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:cyan_glazed_terracotta"))

    val PURPLE_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:purple_glazed_terracotta"))

    val BLUE_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:blue_glazed_terracotta"))

    val BROWN_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:brown_glazed_terracotta"))

    val GREEN_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:green_glazed_terracotta"))

    val RED_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:red_glazed_terracotta"))

    val BLACK_GLAZED_TERRACOTTA: ResourceKey<Item> = register(Key.key("minecraft:black_glazed_terracotta"))

    val WHITE_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:white_concrete"))

    val ORANGE_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:orange_concrete"))

    val MAGENTA_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:magenta_concrete"))

    val LIGHT_BLUE_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:light_blue_concrete"))

    val YELLOW_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:yellow_concrete"))

    val LIME_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:lime_concrete"))

    val PINK_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:pink_concrete"))

    val GRAY_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:gray_concrete"))

    val LIGHT_GRAY_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:light_gray_concrete"))

    val CYAN_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:cyan_concrete"))

    val PURPLE_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:purple_concrete"))

    val BLUE_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:blue_concrete"))

    val BROWN_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:brown_concrete"))

    val GREEN_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:green_concrete"))

    val RED_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:red_concrete"))

    val BLACK_CONCRETE: ResourceKey<Item> = register(Key.key("minecraft:black_concrete"))

    val WHITE_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:white_concrete_powder"))

    val ORANGE_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:orange_concrete_powder"))

    val MAGENTA_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:magenta_concrete_powder"))

    val LIGHT_BLUE_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:light_blue_concrete_powder"))

    val YELLOW_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:yellow_concrete_powder"))

    val LIME_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:lime_concrete_powder"))

    val PINK_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:pink_concrete_powder"))

    val GRAY_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:gray_concrete_powder"))

    val LIGHT_GRAY_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:light_gray_concrete_powder"))

    val CYAN_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:cyan_concrete_powder"))

    val PURPLE_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:purple_concrete_powder"))

    val BLUE_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:blue_concrete_powder"))

    val BROWN_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:brown_concrete_powder"))

    val GREEN_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:green_concrete_powder"))

    val RED_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:red_concrete_powder"))

    val BLACK_CONCRETE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:black_concrete_powder"))

    val TURTLE_EGG: ResourceKey<Item> = register(Key.key("minecraft:turtle_egg"))

    val SNIFFER_EGG: ResourceKey<Item> = register(Key.key("minecraft:sniffer_egg"))

    val DEAD_TUBE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dead_tube_coral_block"))

    val DEAD_BRAIN_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dead_brain_coral_block"))

    val DEAD_BUBBLE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dead_bubble_coral_block"))

    val DEAD_FIRE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dead_fire_coral_block"))

    val DEAD_HORN_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dead_horn_coral_block"))

    val TUBE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:tube_coral_block"))

    val BRAIN_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:brain_coral_block"))

    val BUBBLE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:bubble_coral_block"))

    val FIRE_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:fire_coral_block"))

    val HORN_CORAL_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:horn_coral_block"))

    val TUBE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:tube_coral"))

    val BRAIN_CORAL: ResourceKey<Item> = register(Key.key("minecraft:brain_coral"))

    val BUBBLE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:bubble_coral"))

    val FIRE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:fire_coral"))

    val HORN_CORAL: ResourceKey<Item> = register(Key.key("minecraft:horn_coral"))

    val DEAD_BRAIN_CORAL: ResourceKey<Item> = register(Key.key("minecraft:dead_brain_coral"))

    val DEAD_BUBBLE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:dead_bubble_coral"))

    val DEAD_FIRE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:dead_fire_coral"))

    val DEAD_HORN_CORAL: ResourceKey<Item> = register(Key.key("minecraft:dead_horn_coral"))

    val DEAD_TUBE_CORAL: ResourceKey<Item> = register(Key.key("minecraft:dead_tube_coral"))

    val TUBE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:tube_coral_fan"))

    val BRAIN_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:brain_coral_fan"))

    val BUBBLE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:bubble_coral_fan"))

    val FIRE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:fire_coral_fan"))

    val HORN_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:horn_coral_fan"))

    val DEAD_TUBE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:dead_tube_coral_fan"))

    val DEAD_BRAIN_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:dead_brain_coral_fan"))

    val DEAD_BUBBLE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:dead_bubble_coral_fan"))

    val DEAD_FIRE_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:dead_fire_coral_fan"))

    val DEAD_HORN_CORAL_FAN: ResourceKey<Item> = register(Key.key("minecraft:dead_horn_coral_fan"))

    val BLUE_ICE: ResourceKey<Item> = register(Key.key("minecraft:blue_ice"))

    val CONDUIT: ResourceKey<Item> = register(Key.key("minecraft:conduit"))

    val POLISHED_GRANITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_granite_stairs"))

    val SMOOTH_RED_SANDSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:smooth_red_sandstone_stairs"))

    val MOSSY_STONE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:mossy_stone_brick_stairs"))

    val POLISHED_DIORITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_diorite_stairs"))

    val MOSSY_COBBLESTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:mossy_cobblestone_stairs"))

    val END_STONE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:end_stone_brick_stairs"))

    val STONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:stone_stairs"))

    val SMOOTH_SANDSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:smooth_sandstone_stairs"))

    val SMOOTH_QUARTZ_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:smooth_quartz_stairs"))

    val GRANITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:granite_stairs"))

    val ANDESITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:andesite_stairs"))

    val RED_NETHER_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:red_nether_brick_stairs"))

    val POLISHED_ANDESITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_andesite_stairs"))

    val DIORITE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:diorite_stairs"))

    val COBBLED_DEEPSLATE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:cobbled_deepslate_stairs"))

    val POLISHED_DEEPSLATE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_deepslate_stairs"))

    val DEEPSLATE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:deepslate_brick_stairs"))

    val DEEPSLATE_TILE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:deepslate_tile_stairs"))

    val POLISHED_GRANITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_granite_slab"))

    val SMOOTH_RED_SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:smooth_red_sandstone_slab"))

    val MOSSY_STONE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:mossy_stone_brick_slab"))

    val POLISHED_DIORITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_diorite_slab"))

    val MOSSY_COBBLESTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:mossy_cobblestone_slab"))

    val END_STONE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:end_stone_brick_slab"))

    val SMOOTH_SANDSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:smooth_sandstone_slab"))

    val SMOOTH_QUARTZ_SLAB: ResourceKey<Item> = register(Key.key("minecraft:smooth_quartz_slab"))

    val GRANITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:granite_slab"))

    val ANDESITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:andesite_slab"))

    val RED_NETHER_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:red_nether_brick_slab"))

    val POLISHED_ANDESITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_andesite_slab"))

    val DIORITE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:diorite_slab"))

    val COBBLED_DEEPSLATE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:cobbled_deepslate_slab"))

    val POLISHED_DEEPSLATE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_deepslate_slab"))

    val DEEPSLATE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:deepslate_brick_slab"))

    val DEEPSLATE_TILE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:deepslate_tile_slab"))

    val SCAFFOLDING: ResourceKey<Item> = register(Key.key("minecraft:scaffolding"))

    val REDSTONE: ResourceKey<Item> = register(Key.key("minecraft:redstone"))

    val REDSTONE_TORCH: ResourceKey<Item> = register(Key.key("minecraft:redstone_torch"))

    val REDSTONE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:redstone_block"))

    val REPEATER: ResourceKey<Item> = register(Key.key("minecraft:repeater"))

    val COMPARATOR: ResourceKey<Item> = register(Key.key("minecraft:comparator"))

    val PISTON: ResourceKey<Item> = register(Key.key("minecraft:piston"))

    val STICKY_PISTON: ResourceKey<Item> = register(Key.key("minecraft:sticky_piston"))

    val SLIME_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:slime_block"))

    val HONEY_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:honey_block"))

    val OBSERVER: ResourceKey<Item> = register(Key.key("minecraft:observer"))

    val HOPPER: ResourceKey<Item> = register(Key.key("minecraft:hopper"))

    val DISPENSER: ResourceKey<Item> = register(Key.key("minecraft:dispenser"))

    val DROPPER: ResourceKey<Item> = register(Key.key("minecraft:dropper"))

    val LECTERN: ResourceKey<Item> = register(Key.key("minecraft:lectern"))

    val TARGET: ResourceKey<Item> = register(Key.key("minecraft:target"))

    val LEVER: ResourceKey<Item> = register(Key.key("minecraft:lever"))

    val LIGHTNING_ROD: ResourceKey<Item> = register(Key.key("minecraft:lightning_rod"))

    val DAYLIGHT_DETECTOR: ResourceKey<Item> = register(Key.key("minecraft:daylight_detector"))

    val SCULK_SENSOR: ResourceKey<Item> = register(Key.key("minecraft:sculk_sensor"))

    val CALIBRATED_SCULK_SENSOR: ResourceKey<Item> = register(Key.key("minecraft:calibrated_sculk_sensor"))

    val TRIPWIRE_HOOK: ResourceKey<Item> = register(Key.key("minecraft:tripwire_hook"))

    val TRAPPED_CHEST: ResourceKey<Item> = register(Key.key("minecraft:trapped_chest"))

    val TNT: ResourceKey<Item> = register(Key.key("minecraft:tnt"))

    val REDSTONE_LAMP: ResourceKey<Item> = register(Key.key("minecraft:redstone_lamp"))

    val NOTE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:note_block"))

    val STONE_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:stone_button"))

    val POLISHED_BLACKSTONE_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_button"))

    val OAK_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:oak_button"))

    val SPRUCE_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:spruce_button"))

    val BIRCH_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:birch_button"))

    val JUNGLE_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:jungle_button"))

    val ACACIA_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:acacia_button"))

    val CHERRY_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:cherry_button"))

    val DARK_OAK_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_button"))

    val PALE_OAK_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_button"))

    val MANGROVE_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:mangrove_button"))

    val BAMBOO_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:bamboo_button"))

    val CRIMSON_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:crimson_button"))

    val WARPED_BUTTON: ResourceKey<Item> = register(Key.key("minecraft:warped_button"))

    val STONE_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:stone_pressure_plate"))

    val POLISHED_BLACKSTONE_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_pressure_plate"))

    val LIGHT_WEIGHTED_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:light_weighted_pressure_plate"))

    val HEAVY_WEIGHTED_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:heavy_weighted_pressure_plate"))

    val OAK_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:oak_pressure_plate"))

    val SPRUCE_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:spruce_pressure_plate"))

    val BIRCH_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:birch_pressure_plate"))

    val JUNGLE_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:jungle_pressure_plate"))

    val ACACIA_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:acacia_pressure_plate"))

    val CHERRY_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:cherry_pressure_plate"))

    val DARK_OAK_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_pressure_plate"))

    val PALE_OAK_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_pressure_plate"))

    val MANGROVE_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:mangrove_pressure_plate"))

    val BAMBOO_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:bamboo_pressure_plate"))

    val CRIMSON_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:crimson_pressure_plate"))

    val WARPED_PRESSURE_PLATE: ResourceKey<Item> = register(Key.key("minecraft:warped_pressure_plate"))

    val IRON_DOOR: ResourceKey<Item> = register(Key.key("minecraft:iron_door"))

    val OAK_DOOR: ResourceKey<Item> = register(Key.key("minecraft:oak_door"))

    val SPRUCE_DOOR: ResourceKey<Item> = register(Key.key("minecraft:spruce_door"))

    val BIRCH_DOOR: ResourceKey<Item> = register(Key.key("minecraft:birch_door"))

    val JUNGLE_DOOR: ResourceKey<Item> = register(Key.key("minecraft:jungle_door"))

    val ACACIA_DOOR: ResourceKey<Item> = register(Key.key("minecraft:acacia_door"))

    val CHERRY_DOOR: ResourceKey<Item> = register(Key.key("minecraft:cherry_door"))

    val DARK_OAK_DOOR: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_door"))

    val PALE_OAK_DOOR: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_door"))

    val MANGROVE_DOOR: ResourceKey<Item> = register(Key.key("minecraft:mangrove_door"))

    val BAMBOO_DOOR: ResourceKey<Item> = register(Key.key("minecraft:bamboo_door"))

    val CRIMSON_DOOR: ResourceKey<Item> = register(Key.key("minecraft:crimson_door"))

    val WARPED_DOOR: ResourceKey<Item> = register(Key.key("minecraft:warped_door"))

    val COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:copper_door"))

    val EXPOSED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:exposed_copper_door"))

    val WEATHERED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:weathered_copper_door"))

    val OXIDIZED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:oxidized_copper_door"))

    val WAXED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_copper_door"))

    val WAXED_EXPOSED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_copper_door"))

    val WAXED_WEATHERED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_copper_door"))

    val WAXED_OXIDIZED_COPPER_DOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_copper_door"))

    val IRON_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:iron_trapdoor"))

    val OAK_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:oak_trapdoor"))

    val SPRUCE_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:spruce_trapdoor"))

    val BIRCH_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:birch_trapdoor"))

    val JUNGLE_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:jungle_trapdoor"))

    val ACACIA_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:acacia_trapdoor"))

    val CHERRY_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:cherry_trapdoor"))

    val DARK_OAK_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_trapdoor"))

    val PALE_OAK_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_trapdoor"))

    val MANGROVE_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:mangrove_trapdoor"))

    val BAMBOO_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:bamboo_trapdoor"))

    val CRIMSON_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:crimson_trapdoor"))

    val WARPED_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:warped_trapdoor"))

    val COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:copper_trapdoor"))

    val EXPOSED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:exposed_copper_trapdoor"))

    val WEATHERED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:weathered_copper_trapdoor"))

    val OXIDIZED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:oxidized_copper_trapdoor"))

    val WAXED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_copper_trapdoor"))

    val WAXED_EXPOSED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_copper_trapdoor"))

    val WAXED_WEATHERED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_copper_trapdoor"))

    val WAXED_OXIDIZED_COPPER_TRAPDOOR: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_copper_trapdoor"))

    val OAK_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:oak_fence_gate"))

    val SPRUCE_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:spruce_fence_gate"))

    val BIRCH_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:birch_fence_gate"))

    val JUNGLE_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:jungle_fence_gate"))

    val ACACIA_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:acacia_fence_gate"))

    val CHERRY_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:cherry_fence_gate"))

    val DARK_OAK_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_fence_gate"))

    val PALE_OAK_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_fence_gate"))

    val MANGROVE_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:mangrove_fence_gate"))

    val BAMBOO_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:bamboo_fence_gate"))

    val CRIMSON_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:crimson_fence_gate"))

    val WARPED_FENCE_GATE: ResourceKey<Item> = register(Key.key("minecraft:warped_fence_gate"))

    val POWERED_RAIL: ResourceKey<Item> = register(Key.key("minecraft:powered_rail"))

    val DETECTOR_RAIL: ResourceKey<Item> = register(Key.key("minecraft:detector_rail"))

    val RAIL: ResourceKey<Item> = register(Key.key("minecraft:rail"))

    val ACTIVATOR_RAIL: ResourceKey<Item> = register(Key.key("minecraft:activator_rail"))

    val SADDLE: ResourceKey<Item> = register(Key.key("minecraft:saddle"))

    val MINECART: ResourceKey<Item> = register(Key.key("minecraft:minecart"))

    val CHEST_MINECART: ResourceKey<Item> = register(Key.key("minecraft:chest_minecart"))

    val FURNACE_MINECART: ResourceKey<Item> = register(Key.key("minecraft:furnace_minecart"))

    val TNT_MINECART: ResourceKey<Item> = register(Key.key("minecraft:tnt_minecart"))

    val HOPPER_MINECART: ResourceKey<Item> = register(Key.key("minecraft:hopper_minecart"))

    val CARROT_ON_A_STICK: ResourceKey<Item> = register(Key.key("minecraft:carrot_on_a_stick"))

    val WARPED_FUNGUS_ON_A_STICK: ResourceKey<Item> = register(Key.key("minecraft:warped_fungus_on_a_stick"))

    val PHANTOM_MEMBRANE: ResourceKey<Item> = register(Key.key("minecraft:phantom_membrane"))

    val ELYTRA: ResourceKey<Item> = register(Key.key("minecraft:elytra"))

    val OAK_BOAT: ResourceKey<Item> = register(Key.key("minecraft:oak_boat"))

    val OAK_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:oak_chest_boat"))

    val SPRUCE_BOAT: ResourceKey<Item> = register(Key.key("minecraft:spruce_boat"))

    val SPRUCE_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:spruce_chest_boat"))

    val BIRCH_BOAT: ResourceKey<Item> = register(Key.key("minecraft:birch_boat"))

    val BIRCH_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:birch_chest_boat"))

    val JUNGLE_BOAT: ResourceKey<Item> = register(Key.key("minecraft:jungle_boat"))

    val JUNGLE_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:jungle_chest_boat"))

    val ACACIA_BOAT: ResourceKey<Item> = register(Key.key("minecraft:acacia_boat"))

    val ACACIA_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:acacia_chest_boat"))

    val CHERRY_BOAT: ResourceKey<Item> = register(Key.key("minecraft:cherry_boat"))

    val CHERRY_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:cherry_chest_boat"))

    val DARK_OAK_BOAT: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_boat"))

    val DARK_OAK_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_chest_boat"))

    val PALE_OAK_BOAT: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_boat"))

    val PALE_OAK_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_chest_boat"))

    val MANGROVE_BOAT: ResourceKey<Item> = register(Key.key("minecraft:mangrove_boat"))

    val MANGROVE_CHEST_BOAT: ResourceKey<Item> = register(Key.key("minecraft:mangrove_chest_boat"))

    val BAMBOO_RAFT: ResourceKey<Item> = register(Key.key("minecraft:bamboo_raft"))

    val BAMBOO_CHEST_RAFT: ResourceKey<Item> = register(Key.key("minecraft:bamboo_chest_raft"))

    val STRUCTURE_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:structure_block"))

    val JIGSAW: ResourceKey<Item> = register(Key.key("minecraft:jigsaw"))

    val TURTLE_HELMET: ResourceKey<Item> = register(Key.key("minecraft:turtle_helmet"))

    val TURTLE_SCUTE: ResourceKey<Item> = register(Key.key("minecraft:turtle_scute"))

    val ARMADILLO_SCUTE: ResourceKey<Item> = register(Key.key("minecraft:armadillo_scute"))

    val WOLF_ARMOR: ResourceKey<Item> = register(Key.key("minecraft:wolf_armor"))

    val FLINT_AND_STEEL: ResourceKey<Item> = register(Key.key("minecraft:flint_and_steel"))

    val BOWL: ResourceKey<Item> = register(Key.key("minecraft:bowl"))

    val APPLE: ResourceKey<Item> = register(Key.key("minecraft:apple"))

    val BOW: ResourceKey<Item> = register(Key.key("minecraft:bow"))

    val ARROW: ResourceKey<Item> = register(Key.key("minecraft:arrow"))

    val COAL: ResourceKey<Item> = register(Key.key("minecraft:coal"))

    val CHARCOAL: ResourceKey<Item> = register(Key.key("minecraft:charcoal"))

    val DIAMOND: ResourceKey<Item> = register(Key.key("minecraft:diamond"))

    val EMERALD: ResourceKey<Item> = register(Key.key("minecraft:emerald"))

    val LAPIS_LAZULI: ResourceKey<Item> = register(Key.key("minecraft:lapis_lazuli"))

    val QUARTZ: ResourceKey<Item> = register(Key.key("minecraft:quartz"))

    val AMETHYST_SHARD: ResourceKey<Item> = register(Key.key("minecraft:amethyst_shard"))

    val RAW_IRON: ResourceKey<Item> = register(Key.key("minecraft:raw_iron"))

    val IRON_INGOT: ResourceKey<Item> = register(Key.key("minecraft:iron_ingot"))

    val RAW_COPPER: ResourceKey<Item> = register(Key.key("minecraft:raw_copper"))

    val COPPER_INGOT: ResourceKey<Item> = register(Key.key("minecraft:copper_ingot"))

    val RAW_GOLD: ResourceKey<Item> = register(Key.key("minecraft:raw_gold"))

    val GOLD_INGOT: ResourceKey<Item> = register(Key.key("minecraft:gold_ingot"))

    val NETHERITE_INGOT: ResourceKey<Item> = register(Key.key("minecraft:netherite_ingot"))

    val NETHERITE_SCRAP: ResourceKey<Item> = register(Key.key("minecraft:netherite_scrap"))

    val WOODEN_SWORD: ResourceKey<Item> = register(Key.key("minecraft:wooden_sword"))

    val WOODEN_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:wooden_shovel"))

    val WOODEN_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:wooden_pickaxe"))

    val WOODEN_AXE: ResourceKey<Item> = register(Key.key("minecraft:wooden_axe"))

    val WOODEN_HOE: ResourceKey<Item> = register(Key.key("minecraft:wooden_hoe"))

    val STONE_SWORD: ResourceKey<Item> = register(Key.key("minecraft:stone_sword"))

    val STONE_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:stone_shovel"))

    val STONE_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:stone_pickaxe"))

    val STONE_AXE: ResourceKey<Item> = register(Key.key("minecraft:stone_axe"))

    val STONE_HOE: ResourceKey<Item> = register(Key.key("minecraft:stone_hoe"))

    val GOLDEN_SWORD: ResourceKey<Item> = register(Key.key("minecraft:golden_sword"))

    val GOLDEN_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:golden_shovel"))

    val GOLDEN_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:golden_pickaxe"))

    val GOLDEN_AXE: ResourceKey<Item> = register(Key.key("minecraft:golden_axe"))

    val GOLDEN_HOE: ResourceKey<Item> = register(Key.key("minecraft:golden_hoe"))

    val IRON_SWORD: ResourceKey<Item> = register(Key.key("minecraft:iron_sword"))

    val IRON_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:iron_shovel"))

    val IRON_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:iron_pickaxe"))

    val IRON_AXE: ResourceKey<Item> = register(Key.key("minecraft:iron_axe"))

    val IRON_HOE: ResourceKey<Item> = register(Key.key("minecraft:iron_hoe"))

    val DIAMOND_SWORD: ResourceKey<Item> = register(Key.key("minecraft:diamond_sword"))

    val DIAMOND_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:diamond_shovel"))

    val DIAMOND_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:diamond_pickaxe"))

    val DIAMOND_AXE: ResourceKey<Item> = register(Key.key("minecraft:diamond_axe"))

    val DIAMOND_HOE: ResourceKey<Item> = register(Key.key("minecraft:diamond_hoe"))

    val NETHERITE_SWORD: ResourceKey<Item> = register(Key.key("minecraft:netherite_sword"))

    val NETHERITE_SHOVEL: ResourceKey<Item> = register(Key.key("minecraft:netherite_shovel"))

    val NETHERITE_PICKAXE: ResourceKey<Item> = register(Key.key("minecraft:netherite_pickaxe"))

    val NETHERITE_AXE: ResourceKey<Item> = register(Key.key("minecraft:netherite_axe"))

    val NETHERITE_HOE: ResourceKey<Item> = register(Key.key("minecraft:netherite_hoe"))

    val STICK: ResourceKey<Item> = register(Key.key("minecraft:stick"))

    val MUSHROOM_STEW: ResourceKey<Item> = register(Key.key("minecraft:mushroom_stew"))

    val STRING: ResourceKey<Item> = register(Key.key("minecraft:string"))

    val FEATHER: ResourceKey<Item> = register(Key.key("minecraft:feather"))

    val GUNPOWDER: ResourceKey<Item> = register(Key.key("minecraft:gunpowder"))

    val WHEAT_SEEDS: ResourceKey<Item> = register(Key.key("minecraft:wheat_seeds"))

    val WHEAT: ResourceKey<Item> = register(Key.key("minecraft:wheat"))

    val BREAD: ResourceKey<Item> = register(Key.key("minecraft:bread"))

    val LEATHER_HELMET: ResourceKey<Item> = register(Key.key("minecraft:leather_helmet"))

    val LEATHER_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:leather_chestplate"))

    val LEATHER_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:leather_leggings"))

    val LEATHER_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:leather_boots"))

    val CHAINMAIL_HELMET: ResourceKey<Item> = register(Key.key("minecraft:chainmail_helmet"))

    val CHAINMAIL_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:chainmail_chestplate"))

    val CHAINMAIL_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:chainmail_leggings"))

    val CHAINMAIL_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:chainmail_boots"))

    val IRON_HELMET: ResourceKey<Item> = register(Key.key("minecraft:iron_helmet"))

    val IRON_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:iron_chestplate"))

    val IRON_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:iron_leggings"))

    val IRON_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:iron_boots"))

    val DIAMOND_HELMET: ResourceKey<Item> = register(Key.key("minecraft:diamond_helmet"))

    val DIAMOND_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:diamond_chestplate"))

    val DIAMOND_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:diamond_leggings"))

    val DIAMOND_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:diamond_boots"))

    val GOLDEN_HELMET: ResourceKey<Item> = register(Key.key("minecraft:golden_helmet"))

    val GOLDEN_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:golden_chestplate"))

    val GOLDEN_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:golden_leggings"))

    val GOLDEN_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:golden_boots"))

    val NETHERITE_HELMET: ResourceKey<Item> = register(Key.key("minecraft:netherite_helmet"))

    val NETHERITE_CHESTPLATE: ResourceKey<Item> = register(Key.key("minecraft:netherite_chestplate"))

    val NETHERITE_LEGGINGS: ResourceKey<Item> = register(Key.key("minecraft:netherite_leggings"))

    val NETHERITE_BOOTS: ResourceKey<Item> = register(Key.key("minecraft:netherite_boots"))

    val FLINT: ResourceKey<Item> = register(Key.key("minecraft:flint"))

    val PORKCHOP: ResourceKey<Item> = register(Key.key("minecraft:porkchop"))

    val COOKED_PORKCHOP: ResourceKey<Item> = register(Key.key("minecraft:cooked_porkchop"))

    val PAINTING: ResourceKey<Item> = register(Key.key("minecraft:painting"))

    val GOLDEN_APPLE: ResourceKey<Item> = register(Key.key("minecraft:golden_apple"))

    val ENCHANTED_GOLDEN_APPLE: ResourceKey<Item> = register(Key.key("minecraft:enchanted_golden_apple"))

    val OAK_SIGN: ResourceKey<Item> = register(Key.key("minecraft:oak_sign"))

    val SPRUCE_SIGN: ResourceKey<Item> = register(Key.key("minecraft:spruce_sign"))

    val BIRCH_SIGN: ResourceKey<Item> = register(Key.key("minecraft:birch_sign"))

    val JUNGLE_SIGN: ResourceKey<Item> = register(Key.key("minecraft:jungle_sign"))

    val ACACIA_SIGN: ResourceKey<Item> = register(Key.key("minecraft:acacia_sign"))

    val CHERRY_SIGN: ResourceKey<Item> = register(Key.key("minecraft:cherry_sign"))

    val DARK_OAK_SIGN: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_sign"))

    val PALE_OAK_SIGN: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_sign"))

    val MANGROVE_SIGN: ResourceKey<Item> = register(Key.key("minecraft:mangrove_sign"))

    val BAMBOO_SIGN: ResourceKey<Item> = register(Key.key("minecraft:bamboo_sign"))

    val CRIMSON_SIGN: ResourceKey<Item> = register(Key.key("minecraft:crimson_sign"))

    val WARPED_SIGN: ResourceKey<Item> = register(Key.key("minecraft:warped_sign"))

    val OAK_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:oak_hanging_sign"))

    val SPRUCE_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:spruce_hanging_sign"))

    val BIRCH_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:birch_hanging_sign"))

    val JUNGLE_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:jungle_hanging_sign"))

    val ACACIA_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:acacia_hanging_sign"))

    val CHERRY_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:cherry_hanging_sign"))

    val DARK_OAK_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:dark_oak_hanging_sign"))

    val PALE_OAK_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:pale_oak_hanging_sign"))

    val MANGROVE_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:mangrove_hanging_sign"))

    val BAMBOO_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:bamboo_hanging_sign"))

    val CRIMSON_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:crimson_hanging_sign"))

    val WARPED_HANGING_SIGN: ResourceKey<Item> = register(Key.key("minecraft:warped_hanging_sign"))

    val BUCKET: ResourceKey<Item> = register(Key.key("minecraft:bucket"))

    val WATER_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:water_bucket"), )

    val LAVA_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:lava_bucket"))

    val POWDER_SNOW_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:powder_snow_bucket"))

    val SNOWBALL: ResourceKey<Item> = register(Key.key("minecraft:snowball"))

    val LEATHER: ResourceKey<Item> = register(Key.key("minecraft:leather"))

    val MILK_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:milk_bucket"))

    val PUFFERFISH_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:pufferfish_bucket"))

    val SALMON_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:salmon_bucket"))

    val COD_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:cod_bucket"))

    val TROPICAL_FISH_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:tropical_fish_bucket"))

    val AXOLOTL_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:axolotl_bucket"))

    val TADPOLE_BUCKET: ResourceKey<Item> = register(Key.key("minecraft:tadpole_bucket"))

    val BRICK: ResourceKey<Item> = register(Key.key("minecraft:brick"))

    val CLAY_BALL: ResourceKey<Item> = register(Key.key("minecraft:clay_ball"))

    val DRIED_KELP_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:dried_kelp_block"))

    val PAPER: ResourceKey<Item> = register(Key.key("minecraft:paper"))

    val BOOK: ResourceKey<Item> = register(Key.key("minecraft:book"))

    val SLIME_BALL: ResourceKey<Item> = register(Key.key("minecraft:slime_ball"))

    val EGG: ResourceKey<Item> = register(Key.key("minecraft:egg"))

    val COMPASS: ResourceKey<Item> = register(Key.key("minecraft:compass"))

    val RECOVERY_COMPASS: ResourceKey<Item> = register(Key.key("minecraft:recovery_compass"))

    val BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:bundle"))

    val WHITE_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:white_bundle"))

    val ORANGE_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:orange_bundle"))

    val MAGENTA_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:magenta_bundle"))

    val LIGHT_BLUE_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:light_blue_bundle"))

    val YELLOW_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:yellow_bundle"))

    val LIME_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:lime_bundle"))

    val PINK_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:pink_bundle"))

    val GRAY_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:gray_bundle"))

    val LIGHT_GRAY_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:light_gray_bundle"))

    val CYAN_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:cyan_bundle"))

    val PURPLE_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:purple_bundle"))

    val BLUE_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:blue_bundle"))

    val BROWN_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:brown_bundle"))

    val GREEN_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:green_bundle"))

    val RED_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:red_bundle"))

    val BLACK_BUNDLE: ResourceKey<Item> = register(Key.key("minecraft:black_bundle"))

    val FISHING_ROD: ResourceKey<Item> = register(Key.key("minecraft:fishing_rod"))

    val CLOCK: ResourceKey<Item> = register(Key.key("minecraft:clock"))

    val SPYGLASS: ResourceKey<Item> = register(Key.key("minecraft:spyglass"))

    val GLOWSTONE_DUST: ResourceKey<Item> = register(Key.key("minecraft:glowstone_dust"))

    val COD: ResourceKey<Item> = register(Key.key("minecraft:cod"))

    val SALMON: ResourceKey<Item> = register(Key.key("minecraft:salmon"))

    val TROPICAL_FISH: ResourceKey<Item> = register(Key.key("minecraft:tropical_fish"))

    val PUFFERFISH: ResourceKey<Item> = register(Key.key("minecraft:pufferfish"))

    val COOKED_COD: ResourceKey<Item> = register(Key.key("minecraft:cooked_cod"))

    val COOKED_SALMON: ResourceKey<Item> = register(Key.key("minecraft:cooked_salmon"))

    val INK_SAC: ResourceKey<Item> = register(Key.key("minecraft:ink_sac"))

    val GLOW_INK_SAC: ResourceKey<Item> = register(Key.key("minecraft:glow_ink_sac"))

    val COCOA_BEANS: ResourceKey<Item> = register(Key.key("minecraft:cocoa_beans"))

    val WHITE_DYE: ResourceKey<Item> = register(Key.key("minecraft:white_dye"))

    val ORANGE_DYE: ResourceKey<Item> = register(Key.key("minecraft:orange_dye"))

    val MAGENTA_DYE: ResourceKey<Item> = register(Key.key("minecraft:magenta_dye"))

    val LIGHT_BLUE_DYE: ResourceKey<Item> = register(Key.key("minecraft:light_blue_dye"))

    val YELLOW_DYE: ResourceKey<Item> = register(Key.key("minecraft:yellow_dye"))

    val LIME_DYE: ResourceKey<Item> = register(Key.key("minecraft:lime_dye"))

    val PINK_DYE: ResourceKey<Item> = register(Key.key("minecraft:pink_dye"))

    val GRAY_DYE: ResourceKey<Item> = register(Key.key("minecraft:gray_dye"))

    val LIGHT_GRAY_DYE: ResourceKey<Item> = register(Key.key("minecraft:light_gray_dye"))

    val CYAN_DYE: ResourceKey<Item> = register(Key.key("minecraft:cyan_dye"))

    val PURPLE_DYE: ResourceKey<Item> = register(Key.key("minecraft:purple_dye"))

    val BLUE_DYE: ResourceKey<Item> = register(Key.key("minecraft:blue_dye"))

    val BROWN_DYE: ResourceKey<Item> = register(Key.key("minecraft:brown_dye"))

    val GREEN_DYE: ResourceKey<Item> = register(Key.key("minecraft:green_dye"))

    val RED_DYE: ResourceKey<Item> = register(Key.key("minecraft:red_dye"))

    val BLACK_DYE: ResourceKey<Item> = register(Key.key("minecraft:black_dye"))

    val BONE_MEAL: ResourceKey<Item> = register(Key.key("minecraft:bone_meal"))

    val BONE: ResourceKey<Item> = register(Key.key("minecraft:bone"))

    val SUGAR: ResourceKey<Item> = register(Key.key("minecraft:sugar"))

    val CAKE: ResourceKey<Item> = register(Key.key("minecraft:cake"))

    val WHITE_BED: ResourceKey<Item> = register(Key.key("minecraft:white_bed"))

    val ORANGE_BED: ResourceKey<Item> = register(Key.key("minecraft:orange_bed"))

    val MAGENTA_BED: ResourceKey<Item> = register(Key.key("minecraft:magenta_bed"))

    val LIGHT_BLUE_BED: ResourceKey<Item> = register(Key.key("minecraft:light_blue_bed"))

    val YELLOW_BED: ResourceKey<Item> = register(Key.key("minecraft:yellow_bed"))

    val LIME_BED: ResourceKey<Item> = register(Key.key("minecraft:lime_bed"))

    val PINK_BED: ResourceKey<Item> = register(Key.key("minecraft:pink_bed"))

    val GRAY_BED: ResourceKey<Item> = register(Key.key("minecraft:gray_bed"))

    val LIGHT_GRAY_BED: ResourceKey<Item> = register(Key.key("minecraft:light_gray_bed"))

    val CYAN_BED: ResourceKey<Item> = register(Key.key("minecraft:cyan_bed"))

    val PURPLE_BED: ResourceKey<Item> = register(Key.key("minecraft:purple_bed"))

    val BLUE_BED: ResourceKey<Item> = register(Key.key("minecraft:blue_bed"))

    val BROWN_BED: ResourceKey<Item> = register(Key.key("minecraft:brown_bed"))

    val GREEN_BED: ResourceKey<Item> = register(Key.key("minecraft:green_bed"))

    val RED_BED: ResourceKey<Item> = register(Key.key("minecraft:red_bed"))

    val BLACK_BED: ResourceKey<Item> = register(Key.key("minecraft:black_bed"))

    val COOKIE: ResourceKey<Item> = register(Key.key("minecraft:cookie"))

    val CRAFTER: ResourceKey<Item> = register(Key.key("minecraft:crafter"))

    val FILLED_MAP: ResourceKey<Item> = register(Key.key("minecraft:filled_map"))

    val SHEARS: ResourceKey<Item> = register(Key.key("minecraft:shears"))

    val MELON_SLICE: ResourceKey<Item> = register(Key.key("minecraft:melon_slice"))

    val DRIED_KELP: ResourceKey<Item> = register(Key.key("minecraft:dried_kelp"))

    val PUMPKIN_SEEDS: ResourceKey<Item> = register(Key.key("minecraft:pumpkin_seeds"))

    val MELON_SEEDS: ResourceKey<Item> = register(Key.key("minecraft:melon_seeds"))

    val BEEF: ResourceKey<Item> = register(Key.key("minecraft:beef"))

    val COOKED_BEEF: ResourceKey<Item> = register(Key.key("minecraft:cooked_beef"))

    val CHICKEN: ResourceKey<Item> = register(Key.key("minecraft:chicken"))

    val COOKED_CHICKEN: ResourceKey<Item> = register(Key.key("minecraft:cooked_chicken"))

    val ROTTEN_FLESH: ResourceKey<Item> = register(Key.key("minecraft:rotten_flesh"))

    val ENDER_PEARL: ResourceKey<Item> = register(Key.key("minecraft:ender_pearl"))

    val BLAZE_ROD: ResourceKey<Item> = register(Key.key("minecraft:blaze_rod"))

    val GHAST_TEAR: ResourceKey<Item> = register(Key.key("minecraft:ghast_tear"))

    val GOLD_NUGGET: ResourceKey<Item> = register(Key.key("minecraft:gold_nugget"))

    val NETHER_WART: ResourceKey<Item> = register(Key.key("minecraft:nether_wart"))

    val GLASS_BOTTLE: ResourceKey<Item> = register(Key.key("minecraft:glass_bottle"))

    val POTION: ResourceKey<Item> = register(Key.key("minecraft:potion"))

    val SPIDER_EYE: ResourceKey<Item> = register(Key.key("minecraft:spider_eye"))

    val FERMENTED_SPIDER_EYE: ResourceKey<Item> = register(Key.key("minecraft:fermented_spider_eye"))

    val BLAZE_POWDER: ResourceKey<Item> = register(Key.key("minecraft:blaze_powder"))

    val MAGMA_CREAM: ResourceKey<Item> = register(Key.key("minecraft:magma_cream"))

    val BREWING_STAND: ResourceKey<Item> = register(Key.key("minecraft:brewing_stand"))

    val CAULDRON: ResourceKey<Item> = register(Key.key("minecraft:cauldron"))

    val ENDER_EYE: ResourceKey<Item> = register(Key.key("minecraft:ender_eye"))

    val GLISTERING_MELON_SLICE: ResourceKey<Item> = register(Key.key("minecraft:glistering_melon_slice"))

    val ARMADILLO_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:armadillo_spawn_egg")) { SpawnEggItem(it, EntityType.ARMADILLO) }

    val ALLAY_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:allay_spawn_egg")) { SpawnEggItem(it, EntityType.ALLAY) }

    val AXOLOTL_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:axolotl_spawn_egg")) { SpawnEggItem(it, EntityType.AXOLOTL) }

    val BAT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:bat_spawn_egg")) { SpawnEggItem(it, EntityType.BAT) }

    val BEE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:bee_spawn_egg")) { SpawnEggItem(it, EntityType.BEE) }

    val BLAZE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:blaze_spawn_egg")) { SpawnEggItem(it, EntityType.BLAZE) }

    val BOGGED_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:bogged_spawn_egg")) { SpawnEggItem(it, EntityType.BOGGED) }

    val BREEZE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:breeze_spawn_egg")) { SpawnEggItem(it, EntityType.BREEZE) }

    val CAT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:cat_spawn_egg")) { SpawnEggItem(it, EntityType.CAT) }

    val CAMEL_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:camel_spawn_egg")) { SpawnEggItem(it, EntityType.CAMEL) }

    val CAVE_SPIDER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:cave_spider_spawn_egg")) { SpawnEggItem(it, EntityType.CAVE_SPIDER) }

    val CHICKEN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:chicken_spawn_egg")) { SpawnEggItem(it, EntityType.CHICKEN) }

    val COD_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:cod_spawn_egg")) { SpawnEggItem(it, EntityType.COD) }

    val COW_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:cow_spawn_egg")) { SpawnEggItem(it, EntityType.COW) }

    val CREEPER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:creeper_spawn_egg")) { SpawnEggItem(it, EntityType.CREEPER) }

    val DOLPHIN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:dolphin_spawn_egg")) { SpawnEggItem(it, EntityType.DOLPHIN) }

    val DONKEY_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:donkey_spawn_egg")) { SpawnEggItem(it, EntityType.DONKEY) }

    val DROWNED_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:drowned_spawn_egg")) { SpawnEggItem(it, EntityType.DROWNED) }

    val ELDER_GUARDIAN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:elder_guardian_spawn_egg")) { SpawnEggItem(it, EntityType.ELDER_GUARDIAN) }

    val ENDER_DRAGON_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:ender_dragon_spawn_egg")) { SpawnEggItem(it, EntityType.ENDER_DRAGON) }

    val ENDERMAN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:enderman_spawn_egg")) { SpawnEggItem(it, EntityType.ENDERMAN) }

    val ENDERMITE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:endermite_spawn_egg")) { SpawnEggItem(it, EntityType.ENDERMITE) }

    val EVOKER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:evoker_spawn_egg")) { SpawnEggItem(it, EntityType.EVOKER) }

    val FOX_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:fox_spawn_egg")) { SpawnEggItem(it, EntityType.FOX) }

    val FROG_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:frog_spawn_egg")) { SpawnEggItem(it, EntityType.FROG) }

    val GHAST_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:ghast_spawn_egg")) { SpawnEggItem(it, EntityType.GHAST) }

    val GLOW_SQUID_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:glow_squid_spawn_egg")) { SpawnEggItem(it, EntityType.GLOW_SQUID) }

    val GOAT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:goat_spawn_egg")) { SpawnEggItem(it, EntityType.GOAT) }

    val GUARDIAN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:guardian_spawn_egg")) { SpawnEggItem(it, EntityType.GUARDIAN) }

    val HOGLIN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:hoglin_spawn_egg")) { SpawnEggItem(it, EntityType.HOGLIN) }

    val HORSE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:horse_spawn_egg")) { SpawnEggItem(it, EntityType.HORSE) }

    val HUSK_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:husk_spawn_egg")) { SpawnEggItem(it, EntityType.HUSK) }

    val IRON_GOLEM_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:iron_golem_spawn_egg")) { SpawnEggItem(it, EntityType.IRON_GOLEM) }

    val LLAMA_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:llama_spawn_egg")) { SpawnEggItem(it, EntityType.LLAMA) }

    val MAGMA_CUBE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:magma_cube_spawn_egg")) { SpawnEggItem(it, EntityType.MAGMA_CUBE) }

    val MOOSHROOM_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:mooshroom_spawn_egg")) { SpawnEggItem(it, EntityType.MOOSHROOM) }

    val MULE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:mule_spawn_egg")) { SpawnEggItem(it, EntityType.MULE) }

    val OCELOT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:ocelot_spawn_egg")) { SpawnEggItem(it, EntityType.OCELOT) }

    val PANDA_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:panda_spawn_egg")) { SpawnEggItem(it, EntityType.PANDA) }

    val PARROT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:parrot_spawn_egg")) { SpawnEggItem(it, EntityType.PARROT) }

    val PHANTOM_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:phantom_spawn_egg")) { SpawnEggItem(it, EntityType.PHANTOM) }

    val PIG_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:pig_spawn_egg")) { SpawnEggItem(it, EntityType.PIG) }

    val PIGLIN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:piglin_spawn_egg")) { SpawnEggItem(it, EntityType.PIGLIN) }

    val PIGLIN_BRUTE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:piglin_brute_spawn_egg")) { SpawnEggItem(it, EntityType.PIGLIN_BRUTE) }

    val PILLAGER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:pillager_spawn_egg")) { SpawnEggItem(it, EntityType.PILLAGER) }

    val POLAR_BEAR_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:polar_bear_spawn_egg")) { SpawnEggItem(it, EntityType.POLAR_BEAR) }

    val PUFFERFISH_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:pufferfish_spawn_egg")) { SpawnEggItem(it, EntityType.PUFFERFISH) }

    val RABBIT_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:rabbit_spawn_egg")) { SpawnEggItem(it, EntityType.RABBIT) }

    val RAVAGER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:ravager_spawn_egg")) { SpawnEggItem(it, EntityType.RAVAGER) }

    val SALMON_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:salmon_spawn_egg")) { SpawnEggItem(it, EntityType.SALMON) }

    val SHEEP_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:sheep_spawn_egg")) { SpawnEggItem(it, EntityType.SHEEP) }

    val SHULKER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:shulker_spawn_egg")) { SpawnEggItem(it, EntityType.SHULKER) }

    val SILVERFISH_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:silverfish_spawn_egg")) { SpawnEggItem(it, EntityType.SILVERFISH) }

    val SKELETON_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:skeleton_spawn_egg")) { SpawnEggItem(it, EntityType.SKELETON) }

    val SKELETON_HORSE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:skeleton_horse_spawn_egg")) { SpawnEggItem(it, EntityType.SKELETON_HORSE) }

    val SLIME_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:slime_spawn_egg")) { SpawnEggItem(it, EntityType.SLIME) }

    val SNIFFER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:sniffer_spawn_egg")) { SpawnEggItem(it, EntityType.SNIFFER) }

    val SNOW_GOLEM_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:snow_golem_spawn_egg")) { SpawnEggItem(it, EntityType.SNOW_GOLEM) }

    val SPIDER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:spider_spawn_egg")) { SpawnEggItem(it, EntityType.SPIDER) }

    val SQUID_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:squid_spawn_egg")) { SpawnEggItem(it, EntityType.SQUID) }

    val STRAY_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:stray_spawn_egg")) { SpawnEggItem(it, EntityType.STRAY) }

    val STRIDER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:strider_spawn_egg")) { SpawnEggItem(it, EntityType.STRIDER) }

    val TADPOLE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:tadpole_spawn_egg")) { SpawnEggItem(it, EntityType.TADPOLE) }

    val TRADER_LLAMA_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:trader_llama_spawn_egg")) { SpawnEggItem(it, EntityType.TRADER_LLAMA) }

    val TROPICAL_FISH_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:tropical_fish_spawn_egg")) { SpawnEggItem(it, EntityType.TROPICAL_FISH) }

    val TURTLE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:turtle_spawn_egg")) { SpawnEggItem(it, EntityType.TURTLE) }

    val VEX_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:vex_spawn_egg")) { SpawnEggItem(it, EntityType.VEX) }

    val VILLAGER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:villager_spawn_egg")) { SpawnEggItem(it, EntityType.VILLAGER) }

    val VINDICATOR_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:vindicator_spawn_egg")) { SpawnEggItem(it, EntityType.VINDICATOR) }

    val WANDERING_TRADER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:wandering_trader_spawn_egg")) { SpawnEggItem(it, EntityType.WANDERING_TRADER) }

    val WARDEN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:warden_spawn_egg")) { SpawnEggItem(it, EntityType.WARDEN) }

    val WITCH_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:witch_spawn_egg")) { SpawnEggItem(it, EntityType.WITCH) }

    val WITHER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:wither_spawn_egg")) { SpawnEggItem(it, EntityType.WITHER) }

    val WITHER_SKELETON_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:wither_skeleton_spawn_egg")) { SpawnEggItem(it, EntityType.WITHER_SKELETON) }

    val WOLF_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:wolf_spawn_egg")) { SpawnEggItem(it, EntityType.WOLF) }

    val ZOGLIN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:zoglin_spawn_egg")) { SpawnEggItem(it, EntityType.ZOGLIN) }

    val CREAKING_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:creaking_spawn_egg")) { SpawnEggItem(it, EntityType.CREAKING) }

    val ZOMBIE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:zombie_spawn_egg")) { SpawnEggItem(it, EntityType.ZOMBIE) }

    val ZOMBIE_HORSE_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:zombie_horse_spawn_egg")) { SpawnEggItem(it, EntityType.ZOMBIE_HORSE) }

    val ZOMBIE_VILLAGER_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:zombie_villager_spawn_egg")) { SpawnEggItem(it, EntityType.ZOMBIE_VILLAGER) }

    val ZOMBIFIED_PIGLIN_SPAWN_EGG: ResourceKey<Item> = register(Key.key("minecraft:zombified_piglin_spawn_egg")) { SpawnEggItem(it, EntityType.ZOMBIFIED_PIGLIN) }

    val EXPERIENCE_BOTTLE: ResourceKey<Item> = register(Key.key("minecraft:experience_bottle"))

    val FIRE_CHARGE: ResourceKey<Item> = register(Key.key("minecraft:fire_charge"))

    val WIND_CHARGE: ResourceKey<Item> = register(Key.key("minecraft:wind_charge"))

    val WRITABLE_BOOK: ResourceKey<Item> = register(Key.key("minecraft:writable_book"))

    val WRITTEN_BOOK: ResourceKey<Item> = register(Key.key("minecraft:written_book"))

    val BREEZE_ROD: ResourceKey<Item> = register(Key.key("minecraft:breeze_rod"))

    val MACE: ResourceKey<Item> = register(Key.key("minecraft:mace"))

    val ITEM_FRAME: ResourceKey<Item> = register(Key.key("minecraft:item_frame"))

    val GLOW_ITEM_FRAME: ResourceKey<Item> = register(Key.key("minecraft:glow_item_frame"))

    val FLOWER_POT: ResourceKey<Item> = register(Key.key("minecraft:flower_pot"))

    val CARROT: ResourceKey<Item> = register(Key.key("minecraft:carrot"))

    val POTATO: ResourceKey<Item> = register(Key.key("minecraft:potato"))

    val BAKED_POTATO: ResourceKey<Item> = register(Key.key("minecraft:baked_potato"))

    val POISONOUS_POTATO: ResourceKey<Item> = register(Key.key("minecraft:poisonous_potato"))

    val MAP: ResourceKey<Item> = register(Key.key("minecraft:map"))

    val GOLDEN_CARROT: ResourceKey<Item> = register(Key.key("minecraft:golden_carrot"))

    val SKELETON_SKULL: ResourceKey<Item> = register(Key.key("minecraft:skeleton_skull"))

    val WITHER_SKELETON_SKULL: ResourceKey<Item> = register(Key.key("minecraft:wither_skeleton_skull"))

    val PLAYER_HEAD: ResourceKey<Item> = register(Key.key("minecraft:player_head"))

    val ZOMBIE_HEAD: ResourceKey<Item> = register(Key.key("minecraft:zombie_head"))

    val CREEPER_HEAD: ResourceKey<Item> = register(Key.key("minecraft:creeper_head"))

    val DRAGON_HEAD: ResourceKey<Item> = register(Key.key("minecraft:dragon_head"))

    val PIGLIN_HEAD: ResourceKey<Item> = register(Key.key("minecraft:piglin_head"))

    val NETHER_STAR: ResourceKey<Item> = register(Key.key("minecraft:nether_star"))

    val PUMPKIN_PIE: ResourceKey<Item> = register(Key.key("minecraft:pumpkin_pie"))

    val FIREWORK_ROCKET: ResourceKey<Item> = register(Key.key("minecraft:firework_rocket"))

    val FIREWORK_STAR: ResourceKey<Item> = register(Key.key("minecraft:firework_star"))

    val ENCHANTED_BOOK: ResourceKey<Item> = register(Key.key("minecraft:enchanted_book"))

    val NETHER_BRICK: ResourceKey<Item> = register(Key.key("minecraft:nether_brick"))

    val RESIN_BRICK: ResourceKey<Item> = register(Key.key("minecraft:resin_brick"))

    val PRISMARINE_SHARD: ResourceKey<Item> = register(Key.key("minecraft:prismarine_shard"))

    val PRISMARINE_CRYSTALS: ResourceKey<Item> = register(Key.key("minecraft:prismarine_crystals"))

    val RABBIT: ResourceKey<Item> = register(Key.key("minecraft:rabbit"))

    val COOKED_RABBIT: ResourceKey<Item> = register(Key.key("minecraft:cooked_rabbit"))

    val RABBIT_STEW: ResourceKey<Item> = register(Key.key("minecraft:rabbit_stew"))

    val RABBIT_FOOT: ResourceKey<Item> = register(Key.key("minecraft:rabbit_foot"))

    val RABBIT_HIDE: ResourceKey<Item> = register(Key.key("minecraft:rabbit_hide"))

    val ARMOR_STAND: ResourceKey<Item> = register(Key.key("minecraft:armor_stand"))

    val IRON_HORSE_ARMOR: ResourceKey<Item> = register(Key.key("minecraft:iron_horse_armor"))

    val GOLDEN_HORSE_ARMOR: ResourceKey<Item> = register(Key.key("minecraft:golden_horse_armor"))

    val DIAMOND_HORSE_ARMOR: ResourceKey<Item> = register(Key.key("minecraft:diamond_horse_armor"))

    val LEATHER_HORSE_ARMOR: ResourceKey<Item> = register(Key.key("minecraft:leather_horse_armor"))

    val LEAD: ResourceKey<Item> = register(Key.key("minecraft:lead"))

    val NAME_TAG: ResourceKey<Item> = register(Key.key("minecraft:name_tag"))

    val COMMAND_BLOCK_MINECART: ResourceKey<Item> = register(Key.key("minecraft:command_block_minecart"))

    val MUTTON: ResourceKey<Item> = register(Key.key("minecraft:mutton"))

    val COOKED_MUTTON: ResourceKey<Item> = register(Key.key("minecraft:cooked_mutton"))

    val WHITE_BANNER: ResourceKey<Item> = register(Key.key("minecraft:white_banner"))

    val ORANGE_BANNER: ResourceKey<Item> = register(Key.key("minecraft:orange_banner"))

    val MAGENTA_BANNER: ResourceKey<Item> = register(Key.key("minecraft:magenta_banner"))

    val LIGHT_BLUE_BANNER: ResourceKey<Item> = register(Key.key("minecraft:light_blue_banner"))

    val YELLOW_BANNER: ResourceKey<Item> = register(Key.key("minecraft:yellow_banner"))

    val LIME_BANNER: ResourceKey<Item> = register(Key.key("minecraft:lime_banner"))

    val PINK_BANNER: ResourceKey<Item> = register(Key.key("minecraft:pink_banner"))

    val GRAY_BANNER: ResourceKey<Item> = register(Key.key("minecraft:gray_banner"))

    val LIGHT_GRAY_BANNER: ResourceKey<Item> = register(Key.key("minecraft:light_gray_banner"))

    val CYAN_BANNER: ResourceKey<Item> = register(Key.key("minecraft:cyan_banner"))

    val PURPLE_BANNER: ResourceKey<Item> = register(Key.key("minecraft:purple_banner"))

    val BLUE_BANNER: ResourceKey<Item> = register(Key.key("minecraft:blue_banner"))

    val BROWN_BANNER: ResourceKey<Item> = register(Key.key("minecraft:brown_banner"))

    val GREEN_BANNER: ResourceKey<Item> = register(Key.key("minecraft:green_banner"))

    val RED_BANNER: ResourceKey<Item> = register(Key.key("minecraft:red_banner"))

    val BLACK_BANNER: ResourceKey<Item> = register(Key.key("minecraft:black_banner"))

    val END_CRYSTAL: ResourceKey<Item> = register(Key.key("minecraft:end_crystal"))

    val CHORUS_FRUIT: ResourceKey<Item> = register(Key.key("minecraft:chorus_fruit"))

    val POPPED_CHORUS_FRUIT: ResourceKey<Item> = register(Key.key("minecraft:popped_chorus_fruit"))

    val TORCHFLOWER_SEEDS: ResourceKey<Item> = register(Key.key("minecraft:torchflower_seeds"))

    val PITCHER_POD: ResourceKey<Item> = register(Key.key("minecraft:pitcher_pod"))

    val BEETROOT: ResourceKey<Item> = register(Key.key("minecraft:beetroot"))

    val BEETROOT_SEEDS: ResourceKey<Item> = register(Key.key("minecraft:beetroot_seeds"))

    val BEETROOT_SOUP: ResourceKey<Item> = register(Key.key("minecraft:beetroot_soup"))

    val DRAGON_BREATH: ResourceKey<Item> = register(Key.key("minecraft:dragon_breath"))

    val SPLASH_POTION: ResourceKey<Item> = register(Key.key("minecraft:splash_potion"))

    val SPECTRAL_ARROW: ResourceKey<Item> = register(Key.key("minecraft:spectral_arrow"))

    val TIPPED_ARROW: ResourceKey<Item> = register(Key.key("minecraft:tipped_arrow"))

    val LINGERING_POTION: ResourceKey<Item> = register(Key.key("minecraft:lingering_potion"))

    val SHIELD: ResourceKey<Item> = register(Key.key("minecraft:shield"))

    val TOTEM_OF_UNDYING: ResourceKey<Item> = register(Key.key("minecraft:totem_of_undying"))

    val SHULKER_SHELL: ResourceKey<Item> = register(Key.key("minecraft:shulker_shell"))

    val IRON_NUGGET: ResourceKey<Item> = register(Key.key("minecraft:iron_nugget"))

    val KNOWLEDGE_BOOK: ResourceKey<Item> = register(Key.key("minecraft:knowledge_book"))

    val DEBUG_STICK: ResourceKey<Item> = register(Key.key("minecraft:debug_stick"))

    val MUSIC_DISC_13: ResourceKey<Item> = register(Key.key("minecraft:music_disc_13"))

    val MUSIC_DISC_CAT: ResourceKey<Item> = register(Key.key("minecraft:music_disc_cat"))

    val MUSIC_DISC_BLOCKS: ResourceKey<Item> = register(Key.key("minecraft:music_disc_blocks"))

    val MUSIC_DISC_CHIRP: ResourceKey<Item> = register(Key.key("minecraft:music_disc_chirp"))

    val MUSIC_DISC_CREATOR: ResourceKey<Item> = register(Key.key("minecraft:music_disc_creator"))

    val MUSIC_DISC_CREATOR_MUSIC_BOX: ResourceKey<Item> = register(Key.key("minecraft:music_disc_creator_music_box"))

    val MUSIC_DISC_FAR: ResourceKey<Item> = register(Key.key("minecraft:music_disc_far"))

    val MUSIC_DISC_MALL: ResourceKey<Item> = register(Key.key("minecraft:music_disc_mall"))

    val MUSIC_DISC_MELLOHI: ResourceKey<Item> = register(Key.key("minecraft:music_disc_mellohi"))

    val MUSIC_DISC_STAL: ResourceKey<Item> = register(Key.key("minecraft:music_disc_stal"))

    val MUSIC_DISC_STRAD: ResourceKey<Item> = register(Key.key("minecraft:music_disc_strad"))

    val MUSIC_DISC_WARD: ResourceKey<Item> = register(Key.key("minecraft:music_disc_ward"))

    val MUSIC_DISC_11: ResourceKey<Item> = register(Key.key("minecraft:music_disc_11"))

    val MUSIC_DISC_WAIT: ResourceKey<Item> = register(Key.key("minecraft:music_disc_wait"))

    val MUSIC_DISC_OTHERSIDE: ResourceKey<Item> = register(Key.key("minecraft:music_disc_otherside"))

    val MUSIC_DISC_RELIC: ResourceKey<Item> = register(Key.key("minecraft:music_disc_relic"))

    val MUSIC_DISC_5: ResourceKey<Item> = register(Key.key("minecraft:music_disc_5"))

    val MUSIC_DISC_PIGSTEP: ResourceKey<Item> = register(Key.key("minecraft:music_disc_pigstep"))

    val MUSIC_DISC_PRECIPICE: ResourceKey<Item> = register(Key.key("minecraft:music_disc_precipice"))

    val DISC_FRAGMENT_5: ResourceKey<Item> = register(Key.key("minecraft:disc_fragment_5"))

    val TRIDENT: ResourceKey<Item> = register(Key.key("minecraft:trident"))

    val NAUTILUS_SHELL: ResourceKey<Item> = register(Key.key("minecraft:nautilus_shell"))

    val HEART_OF_THE_SEA: ResourceKey<Item> = register(Key.key("minecraft:heart_of_the_sea"))

    val CROSSBOW: ResourceKey<Item> = register(Key.key("minecraft:crossbow"))

    val SUSPICIOUS_STEW: ResourceKey<Item> = register(Key.key("minecraft:suspicious_stew"))

    val LOOM: ResourceKey<Item> = register(Key.key("minecraft:loom"))

    val FLOWER_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:flower_banner_pattern"))

    val CREEPER_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:creeper_banner_pattern"))

    val SKULL_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:skull_banner_pattern"))

    val MOJANG_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:mojang_banner_pattern"))

    val GLOBE_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:globe_banner_pattern"))

    val PIGLIN_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:piglin_banner_pattern"))

    val FLOW_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:flow_banner_pattern"))

    val GUSTER_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:guster_banner_pattern"))

    val FIELD_MASONED_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:field_masoned_banner_pattern"))

    val BORDURE_INDENTED_BANNER_PATTERN: ResourceKey<Item> = register(Key.key("minecraft:bordure_indented_banner_pattern"))

    val GOAT_HORN: ResourceKey<Item> = register(Key.key("minecraft:goat_horn"))

    val COMPOSTER: ResourceKey<Item> = register(Key.key("minecraft:composter"))

    val BARREL: ResourceKey<Item> = register(Key.key("minecraft:barrel"))

    val SMOKER: ResourceKey<Item> = register(Key.key("minecraft:smoker"))

    val BLAST_FURNACE: ResourceKey<Item> = register(Key.key("minecraft:blast_furnace"))

    val CARTOGRAPHY_TABLE: ResourceKey<Item> = register(Key.key("minecraft:cartography_table"))

    val FLETCHING_TABLE: ResourceKey<Item> = register(Key.key("minecraft:fletching_table"))

    val GRINDSTONE: ResourceKey<Item> = register(Key.key("minecraft:grindstone"))

    val SMITHING_TABLE: ResourceKey<Item> = register(Key.key("minecraft:smithing_table"))

    val STONECUTTER: ResourceKey<Item> = register(Key.key("minecraft:stonecutter"))

    val BELL: ResourceKey<Item> = register(Key.key("minecraft:bell"))

    val LANTERN: ResourceKey<Item> = register(Key.key("minecraft:lantern"))

    val SOUL_LANTERN: ResourceKey<Item> = register(Key.key("minecraft:soul_lantern"))

    val SWEET_BERRIES: ResourceKey<Item> = register(Key.key("minecraft:sweet_berries"))

    val GLOW_BERRIES: ResourceKey<Item> = register(Key.key("minecraft:glow_berries"))

    val CAMPFIRE: ResourceKey<Item> = register(Key.key("minecraft:campfire"))

    val SOUL_CAMPFIRE: ResourceKey<Item> = register(Key.key("minecraft:soul_campfire"))

    val SHROOMLIGHT: ResourceKey<Item> = register(Key.key("minecraft:shroomlight"))

    val HONEYCOMB: ResourceKey<Item> = register(Key.key("minecraft:honeycomb"))

    val BEE_NEST: ResourceKey<Item> = register(Key.key("minecraft:bee_nest"))

    val BEEHIVE: ResourceKey<Item> = register(Key.key("minecraft:beehive"))

    val HONEY_BOTTLE: ResourceKey<Item> = register(Key.key("minecraft:honey_bottle"))

    val HONEYCOMB_BLOCK: ResourceKey<Item> = register(Key.key("minecraft:honeycomb_block"))

    val LODESTONE: ResourceKey<Item> = register(Key.key("minecraft:lodestone"))

    val CRYING_OBSIDIAN: ResourceKey<Item> = register(Key.key("minecraft:crying_obsidian"))

    val BLACKSTONE: ResourceKey<Item> = register(Key.key("minecraft:blackstone"))

    val BLACKSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:blackstone_slab"))

    val BLACKSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:blackstone_stairs"))

    val GILDED_BLACKSTONE: ResourceKey<Item> = register(Key.key("minecraft:gilded_blackstone"))

    val POLISHED_BLACKSTONE: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone"))

    val POLISHED_BLACKSTONE_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_slab"))

    val POLISHED_BLACKSTONE_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_stairs"))

    val CHISELED_POLISHED_BLACKSTONE: ResourceKey<Item> = register(Key.key("minecraft:chiseled_polished_blackstone"))

    val POLISHED_BLACKSTONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_bricks"))

    val POLISHED_BLACKSTONE_BRICK_SLAB: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_brick_slab"))

    val POLISHED_BLACKSTONE_BRICK_STAIRS: ResourceKey<Item> = register(Key.key("minecraft:polished_blackstone_brick_stairs"))

    val CRACKED_POLISHED_BLACKSTONE_BRICKS: ResourceKey<Item> = register(Key.key("minecraft:cracked_polished_blackstone_bricks"))

    val RESPAWN_ANCHOR: ResourceKey<Item> = register(Key.key("minecraft:respawn_anchor"))

    val CANDLE: ResourceKey<Item> = register(Key.key("minecraft:candle"))

    val WHITE_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:white_candle"))

    val ORANGE_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:orange_candle"))

    val MAGENTA_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:magenta_candle"))

    val LIGHT_BLUE_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:light_blue_candle"))

    val YELLOW_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:yellow_candle"))

    val LIME_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:lime_candle"))

    val PINK_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:pink_candle"))

    val GRAY_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:gray_candle"))

    val LIGHT_GRAY_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:light_gray_candle"))

    val CYAN_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:cyan_candle"))

    val PURPLE_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:purple_candle"))

    val BLUE_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:blue_candle"))

    val BROWN_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:brown_candle"))

    val GREEN_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:green_candle"))

    val RED_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:red_candle"))

    val BLACK_CANDLE: ResourceKey<Item> = register(Key.key("minecraft:black_candle"))

    val SMALL_AMETHYST_BUD: ResourceKey<Item> = register(Key.key("minecraft:small_amethyst_bud"))

    val MEDIUM_AMETHYST_BUD: ResourceKey<Item> = register(Key.key("minecraft:medium_amethyst_bud"))

    val LARGE_AMETHYST_BUD: ResourceKey<Item> = register(Key.key("minecraft:large_amethyst_bud"))

    val AMETHYST_CLUSTER: ResourceKey<Item> = register(Key.key("minecraft:amethyst_cluster"))

    val POINTED_DRIPSTONE: ResourceKey<Item> = register(Key.key("minecraft:pointed_dripstone"))

    val OCHRE_FROGLIGHT: ResourceKey<Item> = register(Key.key("minecraft:ochre_froglight"))

    val VERDANT_FROGLIGHT: ResourceKey<Item> = register(Key.key("minecraft:verdant_froglight"))

    val PEARLESCENT_FROGLIGHT: ResourceKey<Item> = register(Key.key("minecraft:pearlescent_froglight"))

    val FROGSPAWN: ResourceKey<Item> = register(Key.key("minecraft:frogspawn"))

    val ECHO_SHARD: ResourceKey<Item> = register(Key.key("minecraft:echo_shard"))

    val BRUSH: ResourceKey<Item> = register(Key.key("minecraft:brush"))

    val NETHERITE_UPGRADE_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:netherite_upgrade_smithing_template"))

    val SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:sentry_armor_trim_smithing_template"))

    val DUNE_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:dune_armor_trim_smithing_template"))

    val COAST_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:coast_armor_trim_smithing_template"))

    val WILD_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:wild_armor_trim_smithing_template"))

    val WARD_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:ward_armor_trim_smithing_template"))

    val EYE_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:eye_armor_trim_smithing_template"))

    val VEX_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:vex_armor_trim_smithing_template"))

    val TIDE_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:tide_armor_trim_smithing_template"))

    val SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:snout_armor_trim_smithing_template"))

    val RIB_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:rib_armor_trim_smithing_template"))

    val SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:spire_armor_trim_smithing_template"))

    val WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:wayfinder_armor_trim_smithing_template"))

    val SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:shaper_armor_trim_smithing_template"))

    val SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:silence_armor_trim_smithing_template"))

    val RAISER_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:raiser_armor_trim_smithing_template"))

    val HOST_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:host_armor_trim_smithing_template"))

    val FLOW_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:flow_armor_trim_smithing_template"))

    val BOLT_ARMOR_TRIM_SMITHING_TEMPLATE: ResourceKey<Item> = register(Key.key("minecraft:bolt_armor_trim_smithing_template"))

    val ANGLER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:angler_pottery_sherd"))

    val ARCHER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:archer_pottery_sherd"))

    val ARMS_UP_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:arms_up_pottery_sherd"))

    val BLADE_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:blade_pottery_sherd"))

    val BREWER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:brewer_pottery_sherd"))

    val BURN_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:burn_pottery_sherd"))

    val DANGER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:danger_pottery_sherd"))

    val EXPLORER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:explorer_pottery_sherd"))

    val FLOW_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:flow_pottery_sherd"))

    val FRIEND_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:friend_pottery_sherd"))

    val GUSTER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:guster_pottery_sherd"))

    val HEART_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:heart_pottery_sherd"))

    val HEARTBREAK_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:heartbreak_pottery_sherd"))

    val HOWL_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:howl_pottery_sherd"))

    val MINER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:miner_pottery_sherd"))

    val MOURNER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:mourner_pottery_sherd"))

    val PLENTY_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:plenty_pottery_sherd"))

    val PRIZE_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:prize_pottery_sherd"))

    val SCRAPE_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:scrape_pottery_sherd"))

    val SHEAF_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:sheaf_pottery_sherd"))

    val SHELTER_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:shelter_pottery_sherd"))

    val SKULL_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:skull_pottery_sherd"))

    val SNORT_POTTERY_SHERD: ResourceKey<Item> = register(Key.key("minecraft:snort_pottery_sherd"))

    val COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:copper_grate"))

    val EXPOSED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:exposed_copper_grate"))

    val WEATHERED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:weathered_copper_grate"))

    val OXIDIZED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:oxidized_copper_grate"))

    val WAXED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:waxed_copper_grate"))

    val WAXED_EXPOSED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_copper_grate"))

    val WAXED_WEATHERED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_copper_grate"))

    val WAXED_OXIDIZED_COPPER_GRATE: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_copper_grate"))

    val COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:copper_bulb"))

    val EXPOSED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:exposed_copper_bulb"))

    val WEATHERED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:weathered_copper_bulb"))

    val OXIDIZED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:oxidized_copper_bulb"))

    val WAXED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:waxed_copper_bulb"))

    val WAXED_EXPOSED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:waxed_exposed_copper_bulb"))

    val WAXED_WEATHERED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:waxed_weathered_copper_bulb"))

    val WAXED_OXIDIZED_COPPER_BULB: ResourceKey<Item> = register(Key.key("minecraft:waxed_oxidized_copper_bulb"))

    val TRIAL_SPAWNER: ResourceKey<Item> = register(Key.key("minecraft:trial_spawner"))

    val TRIAL_KEY: ResourceKey<Item> = register(Key.key("minecraft:trial_key"))

    val OMINOUS_TRIAL_KEY: ResourceKey<Item> = register(Key.key("minecraft:ominous_trial_key"))

    val VAULT: ResourceKey<Item> = register(Key.key("minecraft:vault"))

    private fun register(key: Key): ResourceKey<Item> {
        return register(key, null)
    }

    private inline fun register(key: Key, crossinline itemFactory: (Key) -> Item): ResourceKey<Item> {
        return register(key, itemFactory(key))
    }

    private fun register(key: Key, item: Item?): ResourceKey<Item> {
        map[key] = item
        return ResourceKey(Registries.ITEM, key)
    }

    fun populate() {
        for ((key, value) in map) {
            val item = value
                ?: if (Registries.BLOCK.containsKey(key)) BlockItem(
                    key, Registries.BLOCK.getOrThrow(Registries.BLOCK.resourceKeyByKey(key)))
                else Item(key)

            Registries.ITEM.register(key, item)
        }

        map.clear()
    }

}