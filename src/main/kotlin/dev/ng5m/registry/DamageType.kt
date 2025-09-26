package dev.ng5m.registry

import com.google.gson.annotations.SerializedName

class DamageType {

    @field:SerializedName("message_id")
    val messageId: String? = null
    val scaling: String? = null
    val exhaustion: Float? = null
    val effects: String? = null
    @field:SerializedName("death_message_type")
    val deathMessageType: String? = null

}