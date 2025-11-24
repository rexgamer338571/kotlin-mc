package dev.ng5m.util.mojangapi.response

import dev.ng5m.util.Property

data class PlayerSkinAndCapeResponse(
    val id: String,
    val name: String,
    val legacy: Boolean,
    val properties: List<Property>
)
