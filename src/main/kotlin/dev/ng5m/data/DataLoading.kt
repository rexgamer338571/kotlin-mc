package dev.ng5m.data

import dev.ng5m.MinecraftServer
import dev.ng5m.MinecraftServer.Companion.GSON
import dev.ng5m.block.Block
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.registry.Registries
import dev.ng5m.registry.Registry
import dev.ng5m.util.Properties
import dev.ng5m.util.mapTags
import net.kyori.adventure.key.Key
import java.nio.file.Files
import kotlin.collections.iterator
import kotlin.reflect.full.declaredMemberProperties

fun loadBlocks() {
    val obj = GSON.fromJson(
        Files.readString(Registry.DATA_PATH.resolve("blocks.json")),
        object : com.google.gson.reflect.TypeToken<Map<String, BlocksReportTemplate>>() {})

    for (field in Blocks::class.declaredMemberProperties) {
        val v = field.get(Blocks) as Block

        val key = Registries.BLOCK.keyByValue(v).toString()
        val blockObj = obj[key] ?: continue

        var def: BlockState? = null
        for (state in blockObj.states) {
            if (state.default) def = state.toBlockState(v)
        }

        if (def == null) {
            throw RuntimeException(blockObj.toString())
        }

        val lookup = mutableMapOf<BlockState, Int>()
        val mappedStates = blockObj.states.toList().map {
            val bs = it.toBlockState(v)
            lookup[bs] = it.id
            bs
        }

        BlockState.stateManager.register(
            v,
            def,
            mappedStates,
        ) { state -> lookup[state]!! }
    }
}

private data class BlocksReportTemplate(
    val states: List<State>
) {
    data class State(
        val default: Boolean = false,
        val id: Int,
        val properties: Map<String, String>?
    ) {
        fun toBlockState(block: Block): BlockState = BlockState(block,
            if (properties == null) Properties.ofMap()
            else Properties.ofMap(properties)
        )
    }
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
        val outPath = _root_ide_package_.dev.ng5m.registry.Registry.Companion.DATA_PATH.resolve("tags")
            .resolve(registry.id.value() + ".json")

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