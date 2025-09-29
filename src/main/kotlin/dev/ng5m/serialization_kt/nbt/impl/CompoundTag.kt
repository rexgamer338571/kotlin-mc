package dev.ng5m.serialization_kt.nbt.impl

import dev.ng5m.serialization_kt.Codec
import dev.ng5m.serialization_kt.nbt.NBT
import dev.ng5m.serialization_kt.nbt.Tag

class CompoundTag(name: String = "") : Tag(mutableMapOf<String, Tag>(), name) {
    companion object {
        val VALUE_CODEC: Codec<Map<String, Tag>> = Codec.of(
            { buf ->
                val map = LinkedHashMap<String, Tag>()

                while (true) {
                    val type = NBT.TAG_TYPE_CODEC.read(buf)
                    if (type == TagType.END) break

                    val valueCodec = NBT.TAG_TYPE_CODEC_MAP[type]!!.first
                    val tag = valueCodec.read(buf)

                    map[tag.name] = tag
                }

                return@of map
            },
            { buf, map ->
                for ((name, tag) in map) {
                    tag.name = name

                    NBT.TAG_TYPE_CODEC.write(buf, tag.type())
                    val codec = NBT.TAG_TYPE_CODEC_MAP[tag.type()]!!.first

                    codec.write(buf, tag)
                }

                NBT.TAG_TYPE_CODEC.write(buf, TagType.END)
            }
        )
    }

    fun <T : Tag> add(tag: T) {
        value()[tag.name] = tag
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Tag> get(name: String): T = value()[name] as T

    fun getByte(name: String): Byte = get<ByteTag>(name).value as Byte
    fun getShort(name: String): Short = get<ShortTag>(name).value as Short
    fun getInt(name: String): Int = get<IntTag>(name).value as Int
    fun getLong(name: String): Long = get<LongTag>(name).value as Long
    fun getFloat(name: String): Float = get<FloatTag>(name).value as Float
    fun getDouble(name: String): Double = get<DoubleTag>(name).value as Double
    fun getString(name: String): String = get<StringTag>(name).value as String
    fun getByteArray(name: String): ByteArray = get<ByteArrayTag>(name).value as ByteArray
    fun getIntArray(name: String): IntArray = get<IntArrayTag>(name).value as IntArray
    fun getLongArray(name: String): LongArray = get<LongArrayTag>(name).value as LongArray
    @Suppress("UNCHECKED_CAST")
    fun <T : Tag> getList(name: String): List<T> = get<ListTag<T>>(name).value as List<T>
    @Suppress("UNCHECKED_CAST")
    fun getCompound(name: String): Map<String, Tag> = get<CompoundTag>(name).value as Map<String, Tag>

    fun has(name: String): Boolean = value().containsKey(name)

    @Suppress("UNCHECKED_CAST")
    private fun value(): MutableMap<String, Tag> = value as MutableMap<String, Tag>

    fun map(): Map<String, Tag> = value().toMap()

    override fun type(): TagType = TagType.COMPOUND
}