package dev.ng5m.registry

import dev.ng5m.EnchantmentType
import dev.ng5m.block.BlockState
import dev.ng5m.entity.BlockEntityType
import dev.ng5m.entity.EntityType
import dev.ng5m.item.Item
import dev.ng5m.item.Items
import dev.ng5m.item.component.ItemComponentType
import net.kyori.adventure.key.Key

object Registries {
    val BIOME: Registry<Biome> = Registry.create(Key.key("worldgen/biome"))

    val TRIM_MATERIAL: Registry<TrimMaterial> = Registry.create(Key.key("trim_material"))

    val TRIM_PATTERN: Registry<TrimPattern> = Registry.create(Key.key("trim_pattern"))

    val BANNER_PATTERN: Registry<BannerPattern> = Registry.create(Key.key("banner_pattern"))

    val CHAT_TYPE: Registry<ChatType> = Registry.create(Key.key("chat_type"))

    val DAMAGE_TYPE: Registry<DamageType> = Registry.create(Key.key("damage_type"))

    val DIMENSION_TYPE: Registry<DimensionType> = Registry.create(Key.key("dimension_type"))

    val WOLF_VARIANT: Registry<WolfVariant> = Registry.create(Key.key("wolf_variant"))

    val PAINTING_VARIANT: Registry<PaintingVariant> = Registry.create(Key.key("painting_variant"))


    val BLOCK: Registry<BlockState> = Registry.createNonDataDriven(Key.key("block"))
    val ENTITY_TYPE: Registry<EntityType> = Registry.createNonDataDriven(Key.key("entity_type"))
    val BLOCK_ENTITY_TYPE: Registry<BlockEntityType> = Registry.createNonDataDriven(Key.key("block_entity_type"))
    val ITEM: DefaultedRegistry<Item> = Registry.createNonDataDrivenDefaulted(Key.key("item"), 0)
    val ITEM_COMPONENT_TYPE: Registry<ItemComponentType<*>> = Registry.createNonDataDriven(Key.key("data_component_type"))
    val ENCHANTMENT: Registry<EnchantmentType> = Registry.createNonDataDriven(Key.key("enchantment"))

    fun init() {
        // empty because it's here to init the class
    }
}