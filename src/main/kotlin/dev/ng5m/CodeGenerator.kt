package dev.ng5m

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.ng5m.registry.Registries
import dev.ng5m.registry.Registry
import dev.ng5m.util.generateRegistryClass
import dev.ng5m.util.mergeJSONTags
import net.kyori.adventure.key.Key
import java.nio.file.Files
import java.nio.file.Path

// this is fucking atrocious, should only be used in development!!

private fun mergeTags() {
    Registries.init()
    val rootPath: Path = Path.of("/home/ng5m/IdeaProjects/anothermcserver/data/registry/tags")

    for (registry in Registry.getAllRegistries()) {
        val inPath = rootPath.resolve(registry.id.value())
        val outPath = Registry.DATA_PATH.resolve("tags").resolve(registry.id.value() + ".json")

        if (!inPath.toFile().exists()) continue

        mergeJSONTags(
            inPath,
            outPath
        )
    }
}

private fun generateBlocksCode() {
    Registries.init()

    val path: Path = Path.of("/home/ng5m/paper-1.21.4/generated/reports/registries.json")
    val outPath: Path = Path.of("/home/ng5m/IdeaProjects/kt-test/src/main/kotlin/dev/ng5m/block/Blocks.kt")

    val obj: JsonObject = JsonParser.parseString(Files.readString(path)).asJsonObject
    val block: JsonObject = obj.getAsJsonObject("minecraft:block")
    val entries: JsonObject = block.getAsJsonObject("entries")

    val map: MutableMap<Int, Key> = mutableMapOf()

    for (entry in entries.entrySet()) {
        map[entry.value.asJsonObject["protocol_id"].asInt] = Key.key(entry.key)
    }

    val array: Array<Key> = Array(map.keys.max()) { map[it]!! }

    val sb: StringBuilder = StringBuilder("package dev.ng5m.block\n\n")

        .append("import net.kyori.adventure.key.Key\n\n")

        .append("object Blocks {\n")

    for (entry in array) {
        sb.append("    val ${entry.value().uppercase()}: Key = Key.key(\"${entry.asString()}\")\n\n")
    }

    sb.append("}")

    Files.writeString(outPath, sb)
}

internal fun generateItemCode() {
    Registries.init()

    val path: Path = Path.of("/home/ng5m/paper-1.21.4/generated/reports/registries.json")
    val outPath: Path = Path.of("/home/ng5m/IdeaProjects/kt-test/src/main/kotlin/dev/ng5m/Items.kt")

    val obj: JsonObject = JsonParser.parseString(Files.readString(path)).asJsonObject
    val block: JsonObject = obj.getAsJsonObject("minecraft:item")
    val entries: JsonObject = block.getAsJsonObject("entries")

    val map: MutableMap<Int, Key> = mutableMapOf()

    for (entry in entries.entrySet()) {
        map[entry.value.asJsonObject["protocol_id"].asInt] = Key.key(entry.key)
    }

    val array: Array<Key> = Array(map.keys.max()) { map[it]!! }

    val sb: StringBuilder = StringBuilder("package dev.ng5m\n\n")

        .append("import net.kyori.adventure.key.Key\n")
        .append("import dev.ng5m.item.Item\n")
        .append("import dev.ng5m.registry.Registries\n\n")

        .append("object Items {\n")

    for (entry in array) {
        sb.append("    val ${entry.value().uppercase()}: ResourceKey<Item> = Registries.ITEM.register(Key.key(\"${entry.asString()}\"), Item())\n\n")
    }

    sb.append("}")

    Files.writeString(outPath, sb)
}

private fun generateCode() {
    Registries.init()

    val rootPath: Path = Path.of("/home/ng5m/IdeaProjects/anothermcserver/data/registry")
    val outRootPath: Path = Path.of("src")
        .resolve("main")
        .resolve("kotlin")
        .resolve("dev")
        .resolve("ng5m")
        .resolve("registry")

    for (registry in Registry.getAllRegistries()) {
        val outPath = Registry.DATA_PATH.resolve(registry.id.value() + ".json")
        mergeJSONTags(
            rootPath.resolve(registry.id.value()),
            outPath
        )

        Files.writeString(
            outRootPath.resolve(registry.entryClass.simpleName + "s.kt"),
            generateRegistryClass("dev.ng5m.registry", registry.entryClass.simpleName, outPath)
        )

        println(registry.entryClass.simpleName + "s.populate()")
    }
}