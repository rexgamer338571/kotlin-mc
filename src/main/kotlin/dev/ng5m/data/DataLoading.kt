package dev.ng5m.data

import com.google.gson.reflect.TypeToken
import dev.ng5m.MinecraftServer
import dev.ng5m.MinecraftServer.Companion.GSON
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.util.Properties
import dev.ng5m.util.mapTags
import net.kyori.adventure.key.Key
import java.nio.file.Files
import kotlin.collections.iterator
import kotlin.reflect.full.declaredMemberProperties

fun loadBlocks() {
    val obj = GSON.fromJson(
        Files.readString(_root_ide_package_.dev.ng5m.registry.Registry.Companion.DATA_PATH.resolve("blocks.json")),
        object : com.google.gson.reflect.TypeToken<Map<String, BlocksReportTemplate>>() {})

    for (field in Blocks::class.declaredMemberProperties) {
        val v = field.get(Blocks) as Key

        val blockObj = obj[v.asString()] ?: continue

        for (state in blockObj.states) {
            val properties = Properties.ofMap(state.properties ?: mapOf<String, Any>())

            _root_ide_package_.dev.ng5m.registry.Registries.BLOCK.registerAt(state.id, v, BlockState(v, properties))
        }
    }

}

private data class BlocksReportTemplate(
    val states: List<State>
) {
    data class State(
        val id: Int,
        val properties: Map<String, String>?
    )
}

private fun flattenTags(map: Map<String, List<String>>): MutableMap<String, List<String>> {
    val cache = mutableMapOf<String, List<String>>()
    val visited = mutableSetOf<String>()

    fun resolveTag(id: String, currentPath: Set<String> = emptySet()): List<String> {
        if (id in cache) {
            return cache[id] ?: emptyList()
        }

        val tagValue = map[id] ?: return emptyList()
        val res = mutableListOf<String>()

        for (s in tagValue)
            if (s.startsWith('#'))
                res.addAll(resolveTag(s.substring(1), currentPath + id))
            else
                res.add(s)

        val distinct = res.distinct()
        cache[id] = distinct
        return distinct
    }

    val flat = mutableMapOf<String, List<String>>()
    for (key in map.keys) {
        visited.clear()
        flat[key] = resolveTag(key)
    }

    return flat
}


fun computeTags() {
    for (registry in _root_ide_package_.dev.ng5m.registry.Registry.Companion.getAllRegistries()) {
        val outPath = _root_ide_package_.dev.ng5m.registry.Registry.Companion.DATA_PATH.resolve("tags").resolve(registry.id.value() + ".json")

        if (!outPath.toFile().exists()) continue

        val map: Map<String, List<String>> = MinecraftServer.GSON.fromJson(
            Files.readString(outPath),
            object : com.google.gson.reflect.TypeToken<Map<String, List<String>>>() {}
        )

        val flat: Map<String, List<String>> = flattenTags(map)

        fun <T : Any> registerTagsTypeSafe(registry: dev.ng5m.registry.Registry<T>) {
            for (entry in flat) {
                registry.tags[Key.key(entry.key)] = mapTags<T>(registry, entry.value)
            }
        }

        registerTagsTypeSafe(registry)
    }
}