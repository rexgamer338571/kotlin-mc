package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import dev.ng5m.serialization.annotation.IgnoreTransient
import dev.ng5m.serialization.util.Either
import dev.ng5m.util.IntProvider
import org.jetbrains.annotations.Range

class DimensionType {

    @field:SerializedName("fixed_time")
    val fixedTime: @Range(from = 0, to = 24000) Long? = null

    @field:SerializedName("has_skylight")
    val hasSkylight: Boolean? = null

    @field:SerializedName("has_ceiling")
    val hasCeiling: Boolean? = null

    val ultrawarm = false
    val natural = false

    @field:SerializedName("coordinate_scale")
    val coordinateScale = 0.0

    @field:SerializedName("bed_works")
    val bedWorks = false

    @field:SerializedName("respawn_anchor_works")
    val respawnAnchorWorks = false

    @field:SerializedName("min_y")
    val minY = 0
    val height = 0

    @field:SerializedName("logical_height")
    val logicalHeight = 0

    val infiniburn: String? = null
    val effects: String? = null

    @field:SerializedName("ambient_light")
    val ambientLight = 0f

    @field:SerializedName("piglin_safe")
    val piglinSafe = false

    @field:SerializedName("has_raids")
    val hasRaids = false

    @field:SerializedName("monster_spawn_block_light_limit")
    val monsterSpawnBlockLightLimit: @Range(from = 0, to = 15) Int = 0

    @field:SerializedName("monster_spawn_light_level")
    val monsterSpawnLightLevel: IntProvider? = null

}