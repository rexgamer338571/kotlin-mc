package dev.ng5m.packet.status.c2s

import dev.ng5m.MinecraftServer
import dev.ng5m.MinecraftServer.Companion.GSON
import dev.ng5m.MinecraftServer.Companion.MINECRAFT_VERSION
import dev.ng5m.MinecraftServer.Companion.PROTOCOL
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import net.kyori.adventure.text.Component
import java.util.UUID

data object StatusRequestC2SPacket : Packet {
    val CODEC: Codec<StatusRequestC2SPacket> = Codec.empty(StatusRequestC2SPacket)
        .forType(StatusRequestC2SPacket.javaClass)

    fun getStatusResponse(): String {
        return GSON.toJson(
            StatusResponseTemplate(
                StatusResponseTemplate.Version(MINECRAFT_VERSION, PROTOCOL),
                StatusResponseTemplate.Players(MinecraftServer.getInstance().maxPlayers, MinecraftServer.getInstance().getPlayerCount(), getSample()),
                MinecraftServer.getInstance().motd, false
            )
        )
    }

    data class StatusResponseTemplate(
        val version: Version,
        val players: Players,
        val description: Component,
        val enforcesSecureChat: Boolean
    ) {
        data class Version(
            val name: String,
            val protocol: Int
        )

        data class Players(
            val max: Int,
            val online: Int,
            val sample: Collection<PlayerTemplate>
        )

        data class PlayerTemplate(
            val name: String,
            val uuid: UUID
        )
    }

    private fun getSample(): Collection<StatusResponseTemplate.PlayerTemplate> {
        val set: MutableSet<StatusResponseTemplate.PlayerTemplate> = mutableSetOf()

        for ((i, connection) in MinecraftServer.getInstance().getPlayingConnections().withIndex()) {
            if (i >= 10) break

            val identity = connection.player.getIdentity()
            set.add(StatusResponseTemplate.PlayerTemplate(identity.username, identity.uuid))
        }

        return set
    }
}
