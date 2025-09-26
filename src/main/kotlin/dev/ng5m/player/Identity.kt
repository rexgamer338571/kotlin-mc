package dev.ng5m.player

import dev.ng5m.MinecraftServer
import dev.ng5m.serialization.Codec
import dev.ng5m.util.Property
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
    }

    fun getAdequateUUID(): UUID {
        return if (MinecraftServer.getInstance().onlineMode) uuid
        else UUID.nameUUIDFromBytes("OfflinePlayer:$username".encodeToByteArray())
    }

}