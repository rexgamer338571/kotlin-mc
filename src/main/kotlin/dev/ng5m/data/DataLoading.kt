package dev.ng5m.data

import com.google.gson.reflect.TypeToken
import dev.ng5m.MinecraftServer.Companion.GSON
import dev.ng5m.block.Block
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.registry.Registries
import dev.ng5m.registry.Registry
import dev.ng5m.util.Properties
import dev.ng5m.util.mapTags
import dev.ng5m.util.readFileOrResourceAsString
import dev.ng5m.util.resourceExists
import net.kyori.adventure.key.Key
import kotlin.collections.iterator
import kotlin.reflect.full.declaredMemberProperties

fun loadBlocks() {
    val obj = GSON.fromJson(
        readFileOrResourceAsString(Registry.DATA_PATH.resolve("blocks.json")),
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
    for (registry in Registry.getAllRegistries()) {
        val outPath = Registry.DATA_PATH.resolve("tags")
            .resolve(registry.id.value() + ".json")

        if (!resourceExists(outPath)) continue

        val map: Map<String, List<String>> = GSON.fromJson(
            readFileOrResourceAsString(outPath),
            object : TypeToken<Map<String, List<String>>>() {}
        )

        val flat: Map<String, List<String>> = flattenTags(map)

        fun <T : Any> registerTagsTypeSafe(registry: Registry<T>) {
            for (entry in flat) {
                registry.tags[Key.key(entry.key)] = mapTags(registry, entry.value)
            }
        }

        registerTagsTypeSafe(registry)
    }
}