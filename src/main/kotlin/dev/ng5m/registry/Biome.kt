package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import dev.ng5m.serialization.annotation.TypeArguments
import dev.ng5m.serialization.util.Either

class Biome {

    @TypeArguments(String::class, List::class)
    var carvers: Either<String, List<String>>? = null

    @field:SerializedName("creature_spawn_probability")
    val creatureSpawnProbability = 0f
    val downfall = 0f
    val temperature = 0f

    val features: List<List<String>>? = null

    @field:SerializedName("has_precipitation")
    val hasPrecipitation = false
    val spawners: Spawners? = null
    val effects: Effects? = null

    class Effects {
        @field:SerializedName("fog_color")
        val fogColor = 0

        @field:SerializedName("foliage_color")
        val foliageColor = 0

        @field:SerializedName("grass_color")
        val grassColor = 0

        @field:SerializedName("sky_color")
        val skyColor = 0

        @field:SerializedName("water_color")
        val waterColor = 0

        @field:SerializedName("water_fog_color")
        val waterFogColor = 0

        @field:SerializedName("mood_sound")
        val moodSound: MoodSound? = null
        val music: List<Music>? = null

        @field:SerializedName("music_volume")
        val musicVolume = 0f
    }

    class MoodSound {
        @field:SerializedName("block_search_extent")
        val blockSearchExtent = 0
        val offset = 0f
        val sound: String? = null

        @field:SerializedName("tick_delay")
        val tickDelay = 0
    }

    class Music {
        val weight = 0
        val data: Data? = null

        class Data {
            @field:SerializedName("max_delay")
            val maxDelay = 0

            @field:SerializedName("min_delay")
            val minDelay = 0

            @field:SerializedName("replace_current_music")
            val replaceCurrentMusic = false
            val sound: String? = null
        }
    }

    class Spawners {
        val ambient: List<Spawner>? = null
        val axolotls: List<Spawner>? = null
        val creature: List<Spawner>? = null
        val misc: List<Spawner>? = null
        val monster: List<Spawner>? = null

        @field:SerializedName("underground_water_creature")
        val undergroundWaterCreature: List<Spawner>? = null

        @field:SerializedName("water_ambient")
        val waterAmbient: List<Spawner>? = null

        @field:SerializedName("water_creature")
        val waterCreature: List<Spawner>? = null
    }

    class Spawner {
        val type: String? = null
        val maxCount = 0
        val minCount = 0
        val weight = 0
    }

}