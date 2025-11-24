package dev.ng5m.player

import dev.ng5m.MinecraftServer
import dev.ng5m.serialization.Codec
import dev.ng5m.util.JSONBodyHandler
import dev.ng5m.util.Property
import dev.ng5m.util.mojangapi.response.PlayerSkinAndCapeResponse
import dev.ng5m.util.mojangapi.response.PlayerUUIDResponse
import dev.ng5m.util.unTrimUUID
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.util.UUID

data class Identity(
    val username: String,
    val uuid: UUID,
    val properties: List<Property>
) {
    constructor(username: String, uuid: UUID, vararg properties: Property)
            : this(username, uuid, properties.toList())

    companion object {
        val CODEC: Codec<Identity> = Codec.of(
            Codec.STRING, Identity::username,
            Codec.UUID, Identity::uuid,
            Property.CODEC.list(), Identity::properties,
            ::Identity
        ).forType(Identity::class.java)

        val CODEC_UNSIGNED: Codec<Identity> = Codec.of(
            Codec.STRING, Identity::username,
            Codec.UUID, Identity::uuid,
            ::Identity
        )

        val CODEC_REVERSE = Codec.of(
            Codec.UUID, Identity::uuid,
            Codec.STRING, Identity::username,
            Property.CODEC.list(), Identity::properties
        ) { u, s, p -> Identity(s, u, p) }

        fun fetch(uuid: UUID): Identity {
            val client = HttpClient.newHttpClient()
            val request = HttpRequest.newBuilder(
                URI.create("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
            ).build()

            val response = client.send(request,
                JSONBodyHandler(PlayerSkinAndCapeResponse::class.java))
                .body()
                .get()

            return Identity(
                response.name,
                UUID.fromString(unTrimUUID(response.id)),
                response.properties
            )
        }

        fun fetch(name: String): Identity {
            val client = HttpClient.newHttpClient()
            val request = HttpRequest.newBuilder(
                URI.create("https://api.minecraftservices.com/minecraft/profile/lookup/name/$name")
            ).build()

            val response = client.send(request,
                JSONBodyHandler(PlayerUUIDResponse::class.java))
                .body()
                .get()

            return fetch(UUID.fromString(unTrimUUID(response.id)))
        }
    }

    fun getAdequateUUID(): UUID {
        return if (MinecraftServer.getInstance().onlineMode) uuid
        else UUID.nameUUIDFromBytes("OfflinePlayer:$username".encodeToByteArray())
    }

}