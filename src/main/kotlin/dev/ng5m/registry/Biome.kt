package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import dev.ng5m.serialization.annotation.TypeArguments
import dev.ng5m.serialization.util.Either

class Biome {
    @TypeArguments(String::class, List::class)
    var carvers: Either<String, List<String>>? = null

    @field:SerializedName("creature_spawn_probability")
    var creatureSpawnProbability = 0f
    var downfall = 0f
    var temperature = 0f

    var features: List<List<String>>? = null

    @field:SerializedName("has_precipitation")
    var hasPrecipitation = false
    var spawners: Spawners? = null
    var effects: Effects? = null

    class Effects {
        @field:SerializedName("fog_color")
        var fogColor = 0

        @field:SerializedName("foliage_color")
        var foliageColor = 0

        @field:SerializedName("grass_color")
        var grassColor = 0

        @field:SerializedName("sky_color")
        var skyColor = 0

        @field:SerializedName("water_color")
        var waterColor = 0

        @field:SerializedName("water_fog_color")
        var waterFogColor = 0

        @field:SerializedName("mood_sound")
        var moodSound: MoodSound? = null
        var music: List<Music>? = null

        @field:SerializedName("music_volume")
        var musicVolume = 0f
    }

    class MoodSound {
        @field:SerializedName("block_search_extent")
        var blockSearchExtent = 0
        var offset = 0f
        var sound: String? = null

        @field:SerializedName("tick_delay")
        var tickDelay = 0
    }

    class Music {
        var weight = 0
        var data: Data? = null

        class Data {
            @field:SerializedName("max_delay")
            var maxDelay = 0

            @field:SerializedName("min_delay")
            var minDelay = 0

            @field:SerializedName("replace_current_music")
            var replaceCurrentMusic = false
            var sound: String? = null
        }
    }

    class Spawners {
        var ambient: List<Spawner>? = null
        var axolotls: List<Spawner>? = null
        var creature: List<Spawner>? = null
        var misc: List<Spawner>? = null
        var monster: List<Spawner>? = null

        @field:SerializedName("underground_water_creature")
        var undergroundWaterCreature: List<Spawner>? = null

        @field:SerializedName("water_ambient")
        var waterAmbient: List<Spawner>? = null

        @field:SerializedName("water_creature")
        var waterCreature: List<Spawner>? = null
    }

    class Spawner {
        var type: String? = null
        var maxCount = 0
        var minCount = 0
        var weight = 0
    }

}