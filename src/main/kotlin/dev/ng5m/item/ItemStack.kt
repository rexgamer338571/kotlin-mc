package dev.ng5m.item

import dev.ng5m.item.component.ItemComponentMap
import dev.ng5m.item.component.ItemComponentType
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec

open class ItemStack(val item: Item) {
    companion object {
        val UNDEFINED: ItemStack = ItemStack(Item.UNDEFINED)
        val AIR = ItemStack().withCount(0)

        val CODEC: Codec<ItemStack> = Codec.of(
            { buf ->
                val count = Codec.VARINT.read(buf)
                if (count == 0) return@of ItemStack()

                val idRaw = Codec.VARINT.peek(buf)

                if (idRaw < 0) return@of UNDEFINED

                return@of ItemStack(
                    Registries.ITEM.getOrThrow(Registries.ITEM.idCodec.read(buf))
                )
                    .withCount(count)
                    .withComponents(ItemComponentMap.CODEC.read(buf))
            },
            { buf, stack ->
                if (stack == null) {
                    Codec.VARINT.write(buf, 0)
                    return@of
                }

                Codec.VARINT.write(buf, stack.count)
                if (stack.count == 0) return@of

                Registries.ITEM.idCodec.write(buf, Registries.ITEM.resourceKeyByValue(stack.item))

                ItemComponentMap.CODEC.write(buf, stack.components)
            }
        )
    }

    private var count = 1
    private var components = ItemComponentMap()

    constructor(key: ResourceKey<Item>) : this(Registries.ITEM.getOrThrow(key))
    constructor() : this(Items.AIR)

    fun withCount(count: Int): ItemStack {
        this.count = count
        return this
    }

    fun <T : Any> withComponent(type: ItemComponentType<T>, value: T): ItemStack {
        components.add(type, value)
        return this
    }

    fun withComponents(components: ItemComponentMap): ItemStack {
        this.components = components
        return this
    }

}