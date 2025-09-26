package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import net.kyori.adventure.text.format.Style

class ChatType {
    val chat: Decoration? = null
    val narration: Decoration? = null

    class Decoration {
        @field:SerializedName("translation_key")
        val translationKey: String? = null
        val style: Style? = null
        val parameters: List<String>? = null
    }

}