package dev.ng5m.item.component

import dev.ng5m.registry.Registries
import dev.ng5m.serialization.Codec
import io.netty.buffer.ByteBuf

class ItemComponentMap {
    companion object {
        val CODEC: Codec<ItemComponentMap> = Codec.of(
            { buf ->
                val toAddSize = Codec.VARINT.read(buf)
                val toRemoveSize = Codec.VARINT.read(buf)

                val map = ItemComponentMap()

                for (i in 0 until toAddSize) {
                    val type = Registries.ITEM_COMPONENT_TYPE[Registries.ITEM_COMPONENT_TYPE.idCodec.read(buf)]!!

                    map.addUnsafe(type, type.codec.read(buf)!!)
                }

                for (i in 0 until toRemoveSize) {
                    val type = Registries.ITEM_COMPONENT_TYPE[Registries.ITEM_COMPONENT_TYPE.idCodec.read(buf)]!!

                    map.removeUnsafe(type)
                }

                return@of map
           },
            { buf, map ->
                Codec.VARINT.write(buf, map.toAdd.size)
                Codec.VARINT.write(buf, map.toRemove.size)

                for ((type, value) in map.toAdd) {
                    Registries.ITEM_COMPONENT_TYPE.idCodec.write(
                        buf, Registries.ITEM_COMPONENT_TYPE.resourceKeyByValue(type)
                    )

                    helperWrite(type, value, buf)
                }

                for (type in map.toRemove) {
                    Registries.ITEM_COMPONENT_TYPE.idCodec.write(
                        buf, Registries.ITEM_COMPONENT_TYPE.resourceKeyByValue(type)
                    )
                }
            }
        )

        @Suppress("UNCHECKED_CAST")
        private fun <T : Any> helperWrite(type: ItemComponentType<T>, value: Any, buf: ByteBuf) {
            type.codec.write(buf, value as T)
        }

        fun of(map: Map<ItemComponentType<out Any>, Any>): ItemComponentMap {
            val componentMap = ItemComponentMap()
            componentMap.toAdd.putAll(map)

            return componentMap
        }
    }

    private val toAdd: MutableMap<ItemComponentType<out Any>, Any> = mutableMapOf()
    private val toRemove: MutableSet<ItemComponentType<out Any>> = mutableSetOf()

    fun <T : Any> add(type: ItemComponentType<T>, value: T): ItemComponentMap {
        toAdd[type] = value
        return this
    }

    private fun addUnsafe(type: ItemComponentType<*>, value: Any): ItemComponentMap {
        @Suppress("UNCHECKED_CAST")
        toAdd[type as ItemComponentType<out Any>] = value
        return this
    }

    fun <T : Any> remove(type: ItemComponentType<T>): ItemComponentMap {
        toRemove.add(type)
        return this
    }

    private fun removeUnsafe(type: ItemComponentType<*>): ItemComponentMap {
        @Suppress("UNCHECKED_CAST")
        toRemove.add(type as ItemComponentType<out Any>)
        return this
    }

}
