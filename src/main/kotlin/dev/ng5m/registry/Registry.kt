package dev.ng5m.registry

import com.google.gson.reflect.TypeToken
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.DoubleMap
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.Tag
import net.kyori.adventure.key.Key
import java.nio.file.Path
import java.util.Optional

open class Registry<T : Any>(
    val id: Key,
    val entryClass: Class<T>,
    val dataDriven: Boolean
) {
    companion object {
        val DATA_PATH: Path = Path.of("data")

        private val ROOT: MutableMap<Key, Registry<*>> = mutableMapOf()

        val CODEC: Codec<Registry<*>> = Codec.of(
            Codec.KEY, Registry<*>::id,
            Entry.CODEC.list(), Registry<*>::entryList
        ) { id, entryList ->
            val registry = ROOT[id]
                ?: throw IllegalStateException("Unknown registry: $id")

            for (entry in entryList) {
                @Suppress("UNCHECKED_CAST")
                val typedRegistry = registry as Registry<Any>

                typedRegistry.register(entry.identifier,
                    entry.nbt
                        .map { NBT.fromNBT(it, registry.entryClass) }
                        .orElse(null))
            }

            return@of registry
        }

        fun <T : Any> get(key: ResourceKey<T>): T {
            return key.registry.getOrThrow(key)
        }

        fun getAllRegistries(): Collection<Registry<*>> {
            return ROOT.values.toMutableList()
        }

        inline fun <reified T : Any> create(key: Key): Registry<T> {
            return Registry(key, T::class.java)
        }

        inline fun <reified T : Any> createNonDataDriven(key: Key): Registry<T> {
            return Registry(key, T::class.java, false)
        }

        inline fun <reified T: Any> createNonDataDrivenDefaulted(key: Key, default: Int): DefaultedRegistry<T> {
            return DefaultedRegistry(key, T::class.java, false, default)
        }
    }

    val idCodec: Codec<ResourceKey<T>> by lazy { provideIdCodec() }

    @Suppress("UNCHECKED_CAST")
    val mapTypeToken: TypeToken<Map<Key, T>> =
        TypeToken.getParameterized(Map::class.java, Key::class.java, entryClass) as TypeToken<Map<Key, T>>

    private var index = 0;
    private val map: DoubleMap<MutableMap<Any, Any>, Key, T> = DoubleMap(::LinkedHashMap)
    private val byRawId: DoubleMap<MutableMap<Any, Any>, Int, T> = DoubleMap(::LinkedHashMap)

    val tags: MutableMap<Key, MutableSet<ResourceKey<T>>> = mutableMapOf()

    constructor(id: Key, entryClass: Class<T>) : this(id, entryClass, true)

    init {
        init()
    }

    fun register(key: Key, t: T): ResourceKey<T> {
        registerAt(index++, key, t)

        return ResourceKey(this, key)
    }

    fun resourceKeyByKey(key: Key): ResourceKey<T> {
        if (byRawId.getB(map.getA(key)) == null) throw IllegalArgumentException("key $key not found in registry ($id)")

        return ResourceKey(this, key)
    }

    fun resourceKeyByValue(value: T): ResourceKey<T> {
        return ResourceKey(this, map.getB(value))
    }

    fun idByKey(key: ResourceKey<T>): Int {
        return idByRawKey(key.key)
    }

    fun idByRawKey(key: Key): Int {
        return byRawId.getB(map.getA(key))
    }

    fun keyById(id: Int): ResourceKey<T> {
        return resourceKeyByKey(map.getB(byRawId.getA(id)))
    }

    operator fun get(key: ResourceKey<T>): T? {
        return map.getA(key.key)
    }

    fun getOrThrow(key: ResourceKey<T>): T {
        return map.getA(key.key) ?: throw IllegalArgumentException("key $key not found in registry ($id)")
    }

    internal fun registerAt(index: Int, key: Key, t: T) {
        map.put(key, t)
        byRawId.put(index, t)
    }

    fun randomElement(random: kotlin.random.Random): T {
        return byRawId.getA(random.nextInt(byRawId.size()))
    }

    fun init() {
        ROOT[id] = this
    }

    fun entryList(): List<Entry> {
        val list = mutableListOf<Entry>()
        for (i in 0 until map.size()) {
            val key = keyById(i).key
            list.add(Entry(key, Optional.ofNullable(NBT.toNBT(map.getA(key)))))
        }

        return list
    }

    data class Entry(
        val identifier: Key,
        val nbt: Optional<Tag<*>>
    ) {
        companion object {
            val CODEC: Codec<Entry> = Codec.of(
                Codec.KEY, Entry::identifier,
                NBT.UNNAMED_TAG_CODEC.prefixedOptional(), Entry::nbt,
                ::Entry
            )
        }
    }

    open fun provideIdCodec(): Codec<ResourceKey<T>> = Codec.VARINT.xmap(this::keyById, this::idByKey)

    override fun toString(): String {
        return map.toString()
    }

}