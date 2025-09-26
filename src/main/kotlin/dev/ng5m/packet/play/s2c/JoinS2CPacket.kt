package dev.ng5m.packet.play.s2c

import dev.ng5m.MinecraftServer
import dev.ng5m.player.GameMode
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.world.GameRules
import dev.ng5m.world.Location
import kotlin.math.max

data class JoinS2CPacket(
    val player: Player
) : Packet {
    companion object {
        val CODEC: Codec<JoinS2CPacket> = Codec.of(
            Codec.INTEGER, { it.player.getEntityId() },
            Codec.BOOLEAN, { it.player.getWorld()!!.hardcore },
            Codec.KEY.list(), { MinecraftServer.getInstance().getWorlds().map { it.id } },
            Codec.VARINT, { _ -> MinecraftServer.getInstance().maxPlayers },
            Codec.VARINT, { max(it.player.viewDistance, MinecraftServer.getInstance().serverViewDistance) },
            Codec.VARINT, { max(it.player.simulationDistance, MinecraftServer.getInstance().simulationDistance) },
            Codec.BOOLEAN, { it.player.getWorld()!!.getGameRule(GameRules.REDUCED_DEBUG_INFO)!! },
            Codec.BOOLEAN, { !it.player.getWorld()!!.getGameRule(GameRules.DO_IMMEDIATE_RESPAWN)!! },
            Codec.BOOLEAN, { it.player.getWorld()!!.getGameRule(GameRules.DO_LIMITED_CRAFTING)!! },
            Registries.DIMENSION_TYPE.idCodec, { it.player.getWorld()!!.typeKey },
            Codec.KEY, { it.player.getWorld()!!.id },
            Codec.LONG, { it.player.getWorld()!!.getHashedSeed() },
            Codec.ofEnum(GameMode::class.java), { it.player.gameMode },
            Codec.ofEnum(GameMode::class.java), { it.player.getPreviousGameMode() },
            Codec.BOOLEAN, { it.player.getWorld()!!.debug },
            Codec.BOOLEAN, { it.player.getWorld()!!.flat },
            Codec.of(
                Codec.KEY, { it.world.typeKey.key },
                Location.POSITION_CODEC, { it.xyz },
                { key, pos -> Location(MinecraftServer.getInstance().getWorld(key), pos) }
            ).prefixedOptional(), { it.player.getDeathLocation() },
            Codec.VARINT, { it.player.portalCooldown },
            Codec.VARINT, { it.player.getWorld()!!.seaLevel },
            Codec.BOOLEAN, { _ -> false },
            { eid, hardcore, worldList, maxPlayers, viewDistance, simulationDistance, rdi, dir, dlc,
              dimensionType, worldKey, _, gameMode, previousGameMode, debug, flat, deathLocation,
              portalCooldown, seaLevel, enforcesSecureChat, -> TODO()
            }
        )

        init {
            CODEC.forType(JoinS2CPacket::class.java)
        }
    }
}