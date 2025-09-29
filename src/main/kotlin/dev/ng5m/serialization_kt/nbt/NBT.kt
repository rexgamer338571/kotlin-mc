package dev.ng5m.serialization_kt.nbt

import com.google.gson.JsonElement
import dev.ng5m.serialization_kt.ByteIO
import dev.ng5m.serialization_kt.Codec
import dev.ng5m.serialization_kt.DoubleMap
import dev.ng5m.serialization_kt.Either
import dev.ng5m.serialization_kt.Transcoder
import dev.ng5m.serialization_kt.nbt.impl.ByteArrayTag
import dev.ng5m.serialization_kt.nbt.impl.ByteTag
import dev.ng5m.serialization_kt.nbt.impl.CompoundTag
import dev.ng5m.serialization_kt.nbt.impl.DoubleTag
import dev.ng5m.serialization_kt.nbt.impl.EndTag
import dev.ng5m.serialization_kt.nbt.impl.FloatTag
import dev.ng5m.serialization_kt.nbt.impl.IntArrayTag
import dev.ng5m.serialization_kt.nbt.impl.IntTag
import dev.ng5m.serialization_kt.nbt.impl.ListTag
import dev.ng5m.serialization_kt.nbt.impl.LongArrayTag
import dev.ng5m.serialization_kt.nbt.impl.LongTag
import dev.ng5m.serialization_kt.nbt.impl.ShortTag
import dev.ng5m.serialization_kt.nbt.impl.StringTag
import dev.ng5m.util.Null
import dev.ng5m.util.ReflectObject
import dev.ng5m.util.TypeArguments
import dev.ng5m.util.annotation.IgnoreTransient
import dev.ng5m.util.annotation.SerializedName
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

object NBT {
    private var typeArguments: TypeArguments? = null

    private val initialized = false

    internal val TAG_TYPE_CLASS_MAP = DoubleMap<Tag.TagType, KClass<out Any>>()
    internal val TAG_TYPE_CODEC_MAP = mutableMapOf<Tag.TagType, Pair<Codec<Tag>, Codec<Tag>>>()
    private val TYPE_ADAPTERS = mutableMapOf<KClass<out Any>, Transcoder<Tag, Any>>()

    internal val TAG_TYPE_CODEC = Codec.BYTE.xmap(
        { s -> if (s < Tag.TagType.entries.size) Tag.TagType.entries[s.toInt()] else null },
        { it?.ordinal?.toByte() ?: 0 }
    )

    val NAMED_TAG_CODEC = codec(true)
    val UNNAMED_TAG_CODEC = codec(false)

    private fun codec(named: Boolean): Codec<Tag> = Codec.of(
        { readTag(it, named) },
        { buf, tag -> writeTag(buf, tag, named) }
    )

    fun readTag(buf: ByteIO, named: Boolean): Tag {
        val (nc, unc) = TAG_TYPE_CODEC_MAP[TAG_TYPE_CODEC.read(buf)]!!

        return (if (named) nc else unc).read(buf)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Tag> readTagT(buf: ByteIO, named: Boolean): T = readTag(buf, named) as T

    @Suppress("UNCHECKED_CAST")
    fun <T : Tag> writeTag(buf: ByteIO, tag: T, named: Boolean) {
        TAG_TYPE_CODEC.write(buf, tag.type())
        val (nc, unc) = TAG_TYPE_CODEC_MAP[tag.type()]!!

        ((if (named) nc else unc) as Codec<Any>).write(buf, tag)
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <V, T : Tag> namedTagCodec(
        valueCodec: Codec<V>,
        crossinline factory: (String, V) -> T
    ) = Codec.of(
        Codec.BEDROCK_STRING, { it.name },
        valueCodec, { it.value as V },
        factory
    )

    @Suppress("UNCHECKED_CAST")
    private fun <V, T : Tag> unnamedTagCodec(
        valueCodec: Codec<V>,
        factory: (V) -> T
    ) = valueCodec.xmap(factory) { it.value as V }

    private fun <F, S> eitherCodec(): Transcoder<Tag, Either<F, S>> {
        return object : Transcoder<Tag, Either<F, S>> {
            override fun from(r: Either<F, S>): Tag = toNBT(r.get())

            @Suppress("UNCHECKED_CAST")
            override fun to(t: Tag): Either<F, S> {
                require(typeArguments != null) { "could not decode Either: no @TypeArguments" }

                return if (TAG_TYPE_CLASS_MAP.getA(t.type())!!.isSubclassOf(typeArguments!!.value[0])) {
                    Either.ofFirst(t.value as F)
                } else {
                    Either.ofSecond(t.value as S)
                }
            }
        }
    }

    private fun ensureInitialized() {
        if (!initialized) init()
    }

    fun init() {
        TAG_TYPE_CLASS_MAP.clear()
        TAG_TYPE_CODEC_MAP.clear()

        @Suppress("UNCHECKED_CAST")
        TYPE_ADAPTERS[Either::class] = eitherCodec<Any, Any>() as Transcoder<Tag, Any>
    }

    fun fromJSON(element: JsonElement): Tag? {
        ensureInitialized()

        when {
            element.isJsonNull -> return null
            element.isJsonPrimitive -> {
                val primitive = element.asJsonPrimitive
                return when {
                    primitive.isNumber -> when (primitive.asNumber) {
                        is Byte -> ByteTag(primitive.asByte)
                        is Short -> ShortTag(primitive.asShort)
                        is Int -> IntTag(primitive.asInt)
                        is Long -> LongTag(primitive.asLong)
                        is Float -> FloatTag(primitive.asFloat)
                        is Double -> DoubleTag(primitive.asDouble)
                        else -> throw IllegalArgumentException("waa")
                    }

                    primitive.isString -> StringTag(primitive.asString)
                    primitive.isBoolean -> ByteTag(if (primitive.asBoolean) 1 else 0)
                    else -> throw IllegalArgumentException("waa")
                }
            }

            element.isJsonArray -> {
                val array = element.asJsonArray
                if (array.isEmpty) return ListTag(Tag.TagType.END, emptyList<Null>())
                val tags = array.map { fromJSON(it) }

                return ListTag(TAG_TYPE_CLASS_MAP.getB(tags.first()!!::class)!!, tags)
            }

            element.isJsonObject -> {
                val o = element.asJsonObject
                val ct = CompoundTag()

                for ((k, v) in o.entrySet()) {
                    val tag = fromJSON(v)!!
                    tag.name = k

                    ct.add(tag)
                }

                return ct
            }

            else -> throw IllegalArgumentException("waa")
        }
    }

    fun <T : Any> fromNBT(tag: Tag, type: KClass<T>): T? {
        ensureInitialized()

        if (tag !is EndTag && tag !is CompoundTag) {
            if (!type.isSubclassOf(tag.value::class)) {
                throw NBTConversionException("type does not match tag value type")
            }

            @Suppress("UNCHECKED_CAST")
            return tag.value as? T
        }

        val ro = ReflectObject.ofClass(type)

        @Suppress("UNCHECKED_CAST")
        if (tag is CompoundTag) {
            val typeAdapter = TYPE_ADAPTERS[type]
            if (typeAdapter != null) {
                @Suppress("UNCHECKED_CAST")
                return (typeAdapter.to(tag)) as T?
            }

            val ctor = ro.findDefaultConstructor()
            val instance = ctor.newInstance()

            val fieldLookup = type.declaredMemberProperties.associateBy { getRealName(it) }

            for ((key, valueTag) in tag.map()) {
                val property = fieldLookup[key] ?: continue

                if (property.visibility != KVisibility.PUBLIC) continue

                val mutableProperty = property as? KMutableProperty1<T, Any> ?: continue
                val value = (if (mutableProperty.returnType.jvmErasure == Either::class) {
                    println(mutableProperty.returnType.arguments)
                    val left = try {
                        fromNBT(valueTag, mutableProperty.returnType.arguments.first().type!!.jvmErasure)
                        true
                    } catch (x: Exception) {
                        false
                    }

                    val value = fromNBT(valueTag, (if (left) mutableProperty.returnType.arguments.first() else mutableProperty.returnType.arguments.last()).type!!.jvmErasure as KClass<T>)
                    if (left) Either.ofFirstUnsafe(value) else Either.ofSecondUnsafe(value)
                } else fromNBT(valueTag, mutableProperty.returnType.jvmErasure))

                println(value)
//                propertyValue?.let { value ->
//                    mutableProperty.set(instance, value)
//                }
            }

            return instance
        }

        return null
    }

    fun <T> toNBT(instance: T): Tag {
        ensureInitialized()

        val tag = when (instance) {
            is Boolean -> ByteTag((if (instance) 1 else 0))
            is Byte -> ByteTag(instance)
            is Short -> ShortTag(instance)
            is Int -> IntTag(instance)
            is Long -> LongTag(instance)
            is Float -> FloatTag(instance)
            is Double -> DoubleTag(instance)
            is ByteArray -> ByteArrayTag(instance)
            is String -> StringTag(instance)
            is IntArray -> IntArrayTag(instance)
            is LongArray -> LongArrayTag(instance)
            is List<*> -> {
                val type = if (instance.isEmpty()) Tag.TagType.UNDEFINED
                else if (instance.first() == null) Tag.TagType.UNDEFINED else TAG_TYPE_CLASS_MAP.getB(instance.first()!!::class)
                val newList = mutableListOf<Tag>()
                for (o in instance) newList.add(toNBT(o))

                ListTag(type!!, newList)
            }

            else -> {
                var adapter: Transcoder<Tag, Any>? = null
                TYPE_ADAPTERS.forEach { (k, v) ->
                    if (k.isSubclassOf(instance!!::class)) adapter = v
                }

                if (adapter == null) {
                    val ct = CompoundTag()

                    for (property in instance!!::class.declaredMemberProperties) {
                        var shouldSerialize = true

                        if (property.hasAnnotation<Transient>()) {
                            shouldSerialize = property.hasAnnotation<IgnoreTransient>()
                        }

                        if (!shouldSerialize) continue

                        @Suppress("UNCHECKED_CAST")
                        val value = (property as KProperty1<Any, *>).get(instance) ?: continue

                        val tag = toNBT(value)
                        tag.name = getRealName(property)
                        ct.add(tag)
                    }

                    ct
                }

                adapter to instance
            }
        }

        throw IllegalArgumentException("waa")
    }

    private fun getRealName(property: KProperty1<*, *>): String {
        if (property.hasAnnotation<SerializedName>()) return property.findAnnotation<SerializedName>()!!.value
        if (property.hasAnnotation<com.google.gson.annotations.SerializedName>()) return property.findAnnotation<com.google.gson.annotations.SerializedName>()!!.value

        return property.name
    }

    class NBTConversionException(message: String) : RuntimeException(message)

}