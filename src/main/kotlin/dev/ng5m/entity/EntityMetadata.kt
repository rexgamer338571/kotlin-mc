package dev.ng5m.entity

import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.block.Direction
import dev.ng5m.entity.villager.VillagerData
import dev.ng5m.item.ItemStack
import dev.ng5m.registry.PaintingVariant
import dev.ng5m.registry.Registries
import dev.ng5m.registry.Registry
import dev.ng5m.registry.WolfVariant
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.util.CODEC_POSITION
import dev.ng5m.util.CODEC_QUATERNIONF
import dev.ng5m.util.CODEC_VECTOR3F
import dev.ng5m.util.idOrTCodec
import dev.ng5m.util.math.Vector3f
import dev.ng5m.util.nullable
import dev.ng5m.world.Location
import dev.ng5m.world.particle.ParticleInfo
import net.kyori.adventure.key.Key
import java.util.Optional

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


    fun <T> set(index: Int, entry: Entry<T>) {
        map[index] = entry
    }

    fun <T> set(index: Index<T>, value: T) {
        set(index.index, Entry(index.type, value))
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrThrow(index: Index<T>): T {
        map.computeIfAbsent(index.index) { _ -> Entry(index.type, index.default) }
        return map[index.index]!!.value as T
    }

    data class Type<T>(val codec: Codec<T>) {
        companion object {
            val registry = Registry.createInternal<Type<*>>(Key.key("kmc", "entity_metadata_type"))
            val BYTE = register("byte", Codec.BYTE)
            val VARINT = register("varint", Codec.VARINT)
            val VARLONG = register("varlong", Codec.VARLONG)
            val FLOAT = register("float", Codec.FLOAT)
            val STRING = register("string", Codec.STRING.limit(32767))
            val TEXT_COMPONENT = register("text_component", Codec.TEXT_COMPONENT)
            val OPTIONAL_TEXT_COMPONENT = register("optional_text_component", Codec.TEXT_COMPONENT.nullable())
            val SLOT = register("item_stack", ItemStack.CODEC)
            val BOOLEAN = register("boolean", Codec.BOOLEAN)
            val ROTATIONS = register("rotations", CODEC_VECTOR3F)
            val POSITION = register("position", CODEC_POSITION)
            val OPTIONAL_POSITION = register("optional_position", CODEC_POSITION.nullable())
            val DIRECTION = register("direction", Codec.ofEnum(Direction::class.java))
            val OPTIONAL_UUID = register("optional_uuid", Codec.UUID.nullable())
            val BLOCK_STATE = register("block_state", BlockState.ID_CODEC)
            val OPTIONAL_BLOCK_STATE = register("optional_block_state", Codec.idOrT(BlockState.ID_TRANSCODER, Blocks.AIR.defaultBlockState()))
            val NBT_TAG = register("nbt", NBT.unnamedTagCodec(CompoundTag.VALUE_CODEC, ::CompoundTag))
            val PARTICLE = register("particle", ParticleInfo.CODEC)
            val PARTICLES = register("particles", ParticleInfo.CODEC.list())
            val VILLAGER_DATA = register("villager_data", VillagerData.METADATA_CODEC)
            val OPTIONAL_VARINT = register("optional_varint", Codec.of(
                { buf ->
                    val i = Codec.VARINT.read(buf)
                    return@of if (i == 0) Optional.empty() else Optional.of(i - 1)
                },
                { buf, opt ->
                    Codec.VARINT.write(buf, if (opt.isPresent) 1 + opt.get() else 0)
                }
            ))
            val POSE = register("pose", Codec.ofEnum(Pose::class.java))
            val CAT_VARIANT = register("cat_variant", Registries.CAT_VARIANT.idCodec)
            val WOLF_VARIANT = register("wolf_variant", Codec.NULL) // TODO
            val FROG_VARIANT = register("frog_variant", Codec.NULL) // TODO
            val OPTIONAL_LOCATION = register("optional_global_position", Location.GLOBAL_POS_CODEC.nullable())
            val PAINTING_VARIANT = register("painting_variant", idOrTCodec(Registries.PAINTING_VARIANT, PaintingVariant.CODEC))
            val SNIFFER_STATE = register("sniffer_state", Codec.ofEnum(SnifferState::class.java))
            val ARMADILLO_STATE = register("armadillo_state", Codec.ofEnum(ArmadilloState::class.java))
            val VECTOR3F = register("vector3", CODEC_VECTOR3F)
            val QUATERNION = register("quaternion", CODEC_QUATERNIONF)

            private fun <T> register(id: String, codec: Codec<T>): Type<T> {
                val type = Type(codec)
                registry.getOrThrow(registry.register(Key.key("kmc", id), type))
                return type
            }
        }
    }

    data class Entry<T>(val type: Type<T>, val value: T)

    data class Index<T>(val index: Int, val type: Type<T>, val default: T)
}