package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import net.kyori.adventure.text.Component

class TrimPattern {
    @field:SerializedName("asset_id")
    val assetId: String? = null
    val decal: Boolean? = null
    val description: Component? = null
    @field:SerializedName("template_item")
    val templateItem: String? = null

}