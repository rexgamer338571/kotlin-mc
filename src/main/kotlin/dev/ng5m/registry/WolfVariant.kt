package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import dev.ng5m.serialization.util.Either

class WolfVariant {
    @field:SerializedName("wild_texture")
    val wildTexture: String? = null

    @field:SerializedName("tame_texture")
    val tameTexture: String? = null

    @field:SerializedName("angry_texture")
    val angryTexture: String? = null

    val biomes: Either<String, List<String>>? = null
}