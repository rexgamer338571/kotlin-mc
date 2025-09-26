package dev.ng5m.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName
import dev.ng5m.MinecraftServer
import dev.ng5m.serialization.Transcoder
import net.kyori.adventure.key.Key
import java.util.*

sealed interface IntProvider {
    companion object {
        val TRANSCODER = object : Transcoder<JsonElement, IntProvider> {
            override fun to(t: JsonElement): IntProvider {
                if (t.isJsonPrimitive) {
                    return Constant(t.asInt)
                }

                val obj = t.asJsonObject
                val type = TypeEnum.valueOf(Key.key(obj["type"].asString).value().uppercase())

                return MinecraftServer.GSON.fromJson(obj, type.clazz)
            }

            override fun from(r: IntProvider): JsonElement {
                val obj = MinecraftServer.GSON.toJsonTree(r).asJsonObject
                obj.add("type", JsonPrimitive(r.type().key.asString()))

                return obj
            }
        }
    }

    fun min(): Int
    fun max(): Int

    fun get(random: Random): Int

    fun type(): TypeEnum

    data class Constant(private val value: Int) : IntProvider {
        override fun min(): Int = value
        override fun max(): Int = value
        override fun get(random: Random): Int = value

        override fun type(): TypeEnum = TypeEnum.CONSTANT
    }

    data class Uniform(
        @field:SerializedName("min_inclusive") private val min: Int,
        @field:SerializedName("max_inclusive") private val max: Int
    ) : IntProvider {
        override fun min(): Int = min
        override fun max(): Int = max

        override fun get(random: Random): Int {
            return random.nextInt(max - min + 1) + min
        }

        override fun type(): TypeEnum = TypeEnum.UNIFORM
    }

    data class BiasedToBottom(
        @field:SerializedName("min_inclusive") private val min: Int,
        @field:SerializedName("max_inclusive") private val max: Int
    ) : IntProvider {
        override fun min(): Int = min
        override fun max(): Int = max

        override fun get(random: Random): Int {
            return min + random.nextInt(random.nextInt(max - min + 1) + 1)
        }

        override fun type(): TypeEnum = TypeEnum.BIASED_TO_BOTTOM
    }

    data class Clamped(
        @field:SerializedName("min_inclusive") private val min: Int,
        @field:SerializedName("max_inclusive") private val max: Int,
        private val source: IntProvider
    ) : IntProvider {
        override fun min(): Int = min
        override fun max(): Int = max

        override fun get(random: Random): Int {
            return Math.clamp(source.get(random).toLong(), min, max)
        }

        override fun type(): TypeEnum = TypeEnum.CLAMPED
    }

    data class ClampedNormal(
        private val mean: Float,
        private val deviation: Float,
        private val min: Int,
        private val max: Int
    ) : IntProvider {
        override fun min(): Int = min
        override fun max(): Int = max

        override fun get(random: Random): Int {
            return Math.clamp(mean + random.nextGaussian() * deviation, min.toDouble(), max.toDouble()).toInt()
        }

        override fun type(): TypeEnum = TypeEnum.CLAMPED_NORMAL
    }

    data class WeightedList(
        private val distribution: List<PoolEntry>
    ) : IntProvider {
        private val min: Int;
        private val max: Int;

        init {
            var i: Int = Integer.MAX_VALUE
            var j: Int = Integer.MIN_VALUE
            for (entry in distribution) {
                i = i.coerceAtMost(entry.data.min())
                j = j.coerceAtLeast(entry.data.max())
            }

            min = i
            max = j
        }

        override fun min(): Int = min
        override fun max(): Int = max

        override fun get(random: Random): Int {
            val totalWeight = intSum(distribution.map { it.weight })

            TODO()
        }

        override fun type(): TypeEnum = TypeEnum.WEIGHTED_LIST
    }

    data class PoolEntry(
        val data: IntProvider,
        val weight: Int
    )

    enum class TypeEnum(s: String, val clazz: Class<out IntProvider>) {
        CONSTANT("constant", Constant::class.java),
        UNIFORM("uniform", Uniform::class.java),
        BIASED_TO_BOTTOM("biased_to_bottom", BiasedToBottom::class.java),
        CLAMPED("clamped", Clamped::class.java),
        CLAMPED_NORMAL("clamped_normal", ClampedNormal::class.java),
        WEIGHTED_LIST("weighted_list", WeightedList::class.java);

        val key: Key = Key.key(s)
    }

    interface Type<T : IntProvider> : Transcoder<JsonObject, T>

}