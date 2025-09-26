package dev.ng5m.packet.configuration.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerJoinEvent
import dev.ng5m.event.impl.player.PlayerPreJoinEvent
import dev.ng5m.packet.configuration.KnownPacksPacket
import dev.ng5m.packet.play.s2c.*
import net.kyori.adventure.text.Component
import java.util.*

object ConfigurationC2SHandlers {

    fun clientInformation(connection: MinecraftConnection, packet: ClientInformationC2SPacket) {
        connection.player.applyClientInformation(packet)
    }

    fun ackFinishConfiguration(connection: MinecraftConnection, packet: AckFinishConfigurationC2SPacket) {
        connection.protocolState = ProtocolState.PLAY
        val player = connection.player

        EventManager.fire(PlayerPreJoinEvent(player))

        connection.sendPacket(JoinS2CPacket(player)).onFinish {
            require(player.getWorld() != null)

            val map = mutableMapOf<UUID, Set<PlayerInfoUpdateS2CPacket.PlayerAction>>()
            MinecraftServer.getInstance().getPlayers().forEach {
                val set = mutableSetOf(
                    PlayerInfoUpdateS2CPacket.PlayerAction.AddPlayer(
                        it.getIdentity().username, it.getIdentity().properties
                    ),
                    PlayerInfoUpdateS2CPacket.PlayerAction.InitializeChat(),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateGameMode(it.gameMode),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateListed(true),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateLatency(0),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateDisplayName(),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateListPriority(0),
                    PlayerInfoUpdateS2CPacket.PlayerAction.UpdateOuterLayer(true)
                )

                map[it.getIdentity().getAdequateUUID()] = set
            }

            val infoPacket = PlayerInfoUpdateS2CPacket(
                EnumSet.allOf(PlayerInfoUpdateS2CPacket.PlayerAction.Type::class.java),
                map
            )

            connection.sendPacket(PlayerPosS2CPacket(player, PlayerPosS2CPacket.Flags.ABSOLUTE)).onFinish {
                connection.synchronizePosition {
                    connection.sendPacket(GameEventS2CPacket.START_WAITING_FOR_CHUNKS).onFinish {
                        player.generateAndSendChunksAround()
                    }

                    connection.sendPacket(infoPacket).onFinish {
                        player.getWorld()!!.addEntity(player)
                    }
                    MinecraftServer.getInstance().getPlayingConnections().forEach { it.sendPacket(infoPacket) }
                }
            }

            connection.sendPacket(player.inventory.getContentsPacket())
            player.inventory.activate()
            EventManager.fire(PlayerJoinEvent(player))
        }
    }

    fun knownPacks(connection: MinecraftConnection, packet: KnownPacksPacket) {
        connection.synchronizeRegistries().onFinish {
            connection.updateTags().onFinish { connection.finishConfiguration() }
        }
    }

}