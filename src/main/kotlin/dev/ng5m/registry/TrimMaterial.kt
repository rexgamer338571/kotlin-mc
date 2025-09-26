package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import net.kyori.adventure.text.Component

class TrimMaterial {
    @field:SerializedName("asset_name")
    val assetName: String? = null
    val description: Component? = null
    val ingredient: String? = null
    @field:SerializedName("override_armor_assets")
    val overrideArmorAssets: Map<String, String>? = null

}