package dev.ng5m.entity

import dev.ng5m.item.ItemStack
import dev.ng5m.registry.Registry
import dev.ng5m.serialization.Codec
import dev.ng5m.util.math.Vector3f
import dev.ng5m.util.math.Vector3i
import net.kyori.adventure.key.Key
import java.util.function.BiConsumer
import java.util.function.BiFunction

class EntityMetadata {
    @Suppress("UNCHECKED_CAST")
    companion object {
        val CODEC: Codec<EntityMetadata> = Codec.of(
            { buf ->
                var index: Int = buf.readUnsignedByte().toInt()
                val md = EntityMetadata()
                while (index != 0xFF) {
                    val type = Type.registry.getOrThrow(Type.registry.idCodec.read(buf))
                    val value = type.codec.read(buf)

                    md.map[index] = Entry(type as Type<Any>, value as Any)

                    index = buf.readUnsignedByte().toInt()
                }

                return@of md
            },
            { buf, md ->
                for ((index, entry) in md.map) {
                    buf.writeByte(index)
                    Type.registry.idCodec.write(buf, Type.registry.resourceKeyByValue(entry.type))
                    (entry.type.codec as Codec<Any>).write(buf, entry.value)
                }

                buf.writeByte(0xFF)
            }
        )

        fun ofPairs(vararg map: Pair<Int, Pair<Type<*>, Any>>): EntityMetadata {
            val md = EntityMetadata()
            for (entry in map) {
                md.map[entry.first] = Entry(entry.second.first as Type<Any>, entry.second.second)
            }

            return md
        }
    }

    private val map = mutableMapOf<Int, Entry<*>>()

    data class Type<T>(val codec: Codec<T>) {
        companion object {
            val registry = Registry.createNonDataDriven<Type<*>>(Key.key("kmc", "entity_metadata_type"))
            val BYTE = register("byte", Codec.BYTE)
            val VARINT = register("varint", Codec.VARINT)
            val VARLONG = register("varlong", Codec.VARLONG)
            val FLOAT = register("float", Codec.FLOAT)
            val STRING = register("string", Codec.STRING.limit(32767))
            val TEXT_COMPONENT = register("text_component", Codec.TEXT_COMPONENT)
            val OPTIONAL_TEXT_COMPONENT = register("optional_text_component", Codec.TEXT_COMPONENT.prefixedOptional())
            val SLOT = register("item_stack", ItemStack.CODEC)
            val BOOLEAN = register("boolean", Codec.BOOLEAN)
            val ROTATIONS = register("rotations", Vector3f.CODEC_3_FLOATS)
            val POSITION = register("position", Vector3i.POSITION)
            val OPTIONAL_POSITION = register("optional_position", Vector3i.POSITION.prefixedOptional())

            private fun <T> register(id: String, codec: Codec<T>): Type<T> {
                val type = Type(codec)
                registry.getOrThrow(registry.register(Key.key("kmc", id), type))
                return type
            }
        }
    }

    data class Entry<T>(val type: Type<T>, val value: T)
}