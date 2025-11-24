package dev.ng5m.util

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.ng5m.MinecraftServer
import dev.ng5m.registry.Registry
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Transcoder
import io.netty.buffer.ByteBuf
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.function.Supplier
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.reflect.KClass

val COMPONENT_JSON_TRANSCODER: Transcoder<Component, String> = object : Transcoder<Component, String> {
    override fun to(t: Component): String {
        return GsonComponentSerializer.gson().serialize(t)
    }

    override fun from(r: String): Component {
        return GsonComponentSerializer.gson().deserialize(r)
    }
}

val PEEKING_BYTE_ARRAY_CODEC: Codec<ByteArray> = Codec.of(
    { buf ->
        val len = Codec.VARINT.peek(buf)
        val arr = ByteArray(len + Codec.VARINT.lastOperationLength)

        buf.getBytes(buf.readerIndex(), arr, 0, arr.size)
        buf.readerIndex(buf.readerIndex() + arr.size)

        return@of arr
    },
    ByteBuf::writeBytes
)

fun <T> or(a: T, fallback: T): T {
    a ?: return fallback
    return a
}

fun readResource(path: Path): ByteArray? {
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path.toString()) ?: return null
    val ret = ByteArrayOutputStream()
    val buf = ByteArray(1024)
    var length: Int
    while (true) {
        length = stream.read(buf)
        if (length == -1) break

        ret.write(buf, 0, length)
    }

    return ret.toByteArray()
}

fun readFileOrResource(path: Path): ByteArray {
    if (!path.exists()) {
        return readResource(path) ?: throw RuntimeException("Resource $path not found")
    }

    return path.toFile().readBytes()
}

fun readFileOrResourceAsString(path: Path): String {
    if (!path.exists()) {
        return String(readResource(path) ?: throw RuntimeException("Resource $path not found"), StandardCharsets.UTF_8)
    }

    return path.toFile().readText()
}

fun resourceExists(path: Path): Boolean {
    return Thread.currentThread().contextClassLoader.getResourceAsStream(path.toString()) != null
}

fun <T : Any> mapTags(registry: Registry<T>, raw: List<String>): MutableSet<ResourceKey<T>> {
    val set = mutableSetOf<ResourceKey<T>>()

    for (entry in raw) {
        set.add(registry.resourceKeyByKey(Key.key(entry)))
    }

    return set
}

fun mergeJSONTags(directory: Path): JsonObject {
    val o = JsonObject()

    Files.list(directory).forEach { file ->
        val fileName = file.fileName.toString()

        if (file.isDirectory()) {
            val o2 = mergeJSONTags(file)
            for (entry in o2.entrySet()) {
                o.add("minecraft:$fileName/${Key.key(entry.key).value()}", entry.value)
            }

            return@forEach
        }

        o.add(
            "minecraft:" + fileName.dropLast(5),
            JsonParser.parseString(Files.readString(file)).asJsonObject["values"]
        )
    }

    return o
}

fun mergeJSONTags(directory: Path, output: Path) {
    output.parent.toFile().mkdirs()
    Files.writeString(output, MinecraftServer.GSON_PRETTY.toJson(mergeJSONTags(directory)).toString())
}

fun toConstName(name: String): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return name.replace(pattern, "_$0").uppercase()
}

fun generateRegistryClass(`package`: String, className: String, registryFilePath: Path): String {
    val o: JsonObject = JsonParser.parseString(Files.readString(registryFilePath)).asJsonObject

    val sb = StringBuilder("package $`package`\n\n")

        .append("import net.kyori.adventure.key.Key\n\n")

        .append("object ${className}s : RegistryInitializer {\n")
        .append("    private val set: MutableSet<Key> = mutableSetOf()\n\n")

    for (key in o.keySet()) {
        val finalKey = if (key.contains(":")) key.split(":")[1] else key

        sb.append("    val ${finalKey.uppercase()} = add(\"${finalKey}\")\n\n")
    }

    val constName = toConstName(className)

    sb.append("""
    private fun add(path: String): ResourceKey<$className> {
        val key = Key.key(path)
        set.add(key)

        return ResourceKey(Registries.$constName, key)
    }

    override fun populate() {
        val map: Map<Key, $className> = load(Registries.$constName.id)

        for (key in set) {
            val v = map[key] ?: continue
            Registries.$constName.register(key, v)
        }
    }
}
    """)

    return sb.toString()
}

fun intSum(ii: List<Int>): Int {
    var value = 0
    for (i in ii) value += i
    return value
}

inline fun <reified T> copyList(list: List<T>): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(list)

    return newList
}

fun <T> splitMapByteArray(delimiter: Byte, array: ByteArray, mapper: (ByteArray) -> T): List<T> {
    val list = mutableListOf<T>()

    var current = mutableListOf<Byte>()
    for (byte in array) {
        if (byte == delimiter) {
            list.add(mapper(current.toByteArray()))
            current = mutableListOf()
        }
        else current.add(byte)
    }

    return list
}

fun <K, V> mapToValueList(map: Map<K, V>): List<V> {
    return map.values.stream().toList()
}

fun <T> initClass(clazz: Class<T>) {
    try {
        Class.forName(clazz.name)
    } catch (ignored: Exception) {

    }
}

fun <T : Any> initClass(clazz: KClass<T>) {
    initClass(clazz.java)
}

fun <T> sneakyThrow(x: Throwable): T {
    throw x
}

infix fun Byte.and(other: Byte): Byte {
    return (other.toInt() and toInt()).toByte()
}

fun bitsToRepresent(v: Int): Int {
    return Int.SIZE_BITS - Integer.numberOfLeadingZeros(v)
}

fun unTrimUUID(trimmed: String): String {
    if (trimmed.count() { it == '-' } == 4) return trimmed
    return "${trimmed.take(8)}-${trimmed.substring(8, 12)}-${trimmed.substring(12, 16)}-${trimmed.substring(16, 20)}-${trimmed.substring(20)}"
}

fun <T> getIf(cond: Boolean, getter: Supplier<T>): T? = if (cond) getter.get() else null

fun sha1(input: ByteArray): String {
    val md = MessageDigest.getInstance("SHA-1")
    md.update(input)
    return md.digest().joinToString("") { "%02x".format(it) }
}