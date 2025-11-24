package dev.ng5m.util.mojangapi.response

data class PlayerUUIDResponse(
    val id: String,
    val name: String,
    val legacy: Boolean,
    val demo: Boolean
)