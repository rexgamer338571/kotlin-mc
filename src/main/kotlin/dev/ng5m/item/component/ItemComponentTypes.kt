package dev.ng5m.item.component

import dev.ng5m.Enchantment
import dev.ng5m.EnchantmentType
import dev.ng5m.item.Item
import dev.ng5m.item.component.impl.UnbreakableComponent
import dev.ng5m.registry.Registries
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.impl.CompoundTag
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component

object ItemComponentTypes {
    val CUSTOM_DATA: ItemComponentType<CompoundTag> = register(
        "custom_data",
        ItemComponentType(NBT.UNNAMED_TAG_CODEC.cast())
    );
    val MAX_STACK_SIZE: ItemComponentType<Int> = register(
        "max_stack_size",
        ItemComponentType(Codec.VARINT)
    )
    val MAX_DAMAGE: ItemComponentType<Int> = register(
        "max_damage",
        ItemComponentType(Codec.VARINT)
    )
    val DAMAGE: ItemComponentType<Int> = register(
        "damage",
        ItemComponentType(Codec.VARINT)
    )
    val UNBREAKABLE: ItemComponentType<UnbreakableComponent> = register(
        "unbreakable",
        ItemComponentType(Codec.empty(UnbreakableComponent))
    )
    val CUSTOM_NAME: ItemComponentType<Component> = register(
        "custom_name",
        ItemComponentType(Codec.TEXT_COMPONENT)
    )
    val ITEM_NAME: ItemComponentType<Component> = register(
        "item_name",
        ItemComponentType(Codec.TEXT_COMPONENT)
    )
    val ITEM_MODEL: ItemComponentType<Key> = register(
        "item_model",
        ItemComponentType(Codec.KEY)
    )
    val LORE: ItemComponentType<List<Component>> = register(
        "lore",
        ItemComponentType(Codec.TEXT_COMPONENT.list())
    )
    val RARITY: ItemComponentType<Item.Rarity> = register(
        "rarity",
        ItemComponentType(Codec.ofEnum(Item.Rarity::class.java))
    )
    val ENCHANTMENTS: ItemComponentType<List<Enchantment>> = register(
        "enchantments",
        ItemComponentType(Enchantment.CODEC.list())
    )


    private fun <T> register(id: String, type: ItemComponentType<T>): ItemComponentType<T> {
        Registries.ITEM_COMPONENT_TYPE.register(Key.key(id), type)
        return type
    }

}