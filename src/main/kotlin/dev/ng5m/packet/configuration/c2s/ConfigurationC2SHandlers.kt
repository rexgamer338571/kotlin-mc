package dev.ng5m.packet.configuration.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.ProtocolState
import dev.ng5m.Teams
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerJoinEvent
import dev.ng5m.event.impl.player.PlayerPreJoinEvent
import dev.ng5m.pack.ResourcePackManager
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
            val entries = MinecraftServer.getInstance()
                .getPlayers()
                .map {
                    PlayerInfoUpdateS2CPacket.Entry(
                        it.getIdentity().getAdequateUUID(),
                        it.getIdentity(), Optional.empty(),
                        it.gameMode, true, 0, Optional.empty(),
                        0, true
                    )
                }
                .toList()

            val infoPacket = PlayerInfoUpdateS2CPacket(
                EnumSet.allOf(PlayerInfoUpdateS2CPacket.Action::class.java), entries
            )

            connection.sendPacket(PlayerPosS2CPacket(player, PlayerPosS2CPacket.Flags.ABSOLUTE)).onFinish {
                connection.synchronizePosition {
                    connection.sendPacket(GameEventS2CPacket.START_WAITING_FOR_CHUNKS).onFinish {
                        player.generateAndSendChunksAround()
                    }

                    connection.sendPacket(infoPacket).onFinish {
                        player.getWorld().addEntity(player)
                    }
                    MinecraftServer.getInstance().getPlayingConnections().forEach { it.sendPacket(infoPacket) }

                    player.getWorld().entities().forEach {
                        it.spawnForPlayer(player)
                    }
                }
            }

            connection.sendPacket(SetHeldSlotS2CPacket(player.heldItem))
            connection.sendPacket(SetContainerContentsS2CPacket(
                player.inventory.id, player.inventory.revision(),
                player.inventory.slots(), player.carriedItem
            ))

            Teams.teams.forEach {
                connection.sendPacket(it.createPacket())
            }

            EventManager.fire(PlayerJoinEvent(player))

            ResourcePackManager.send(connection)
        }
    }

    fun knownPacks(connection: MinecraftConnection, packet: KnownPacksPacket) {
        connection.synchronizeRegistries().onFinish {
            connection.updateTags().onFinish {
                connection.finishConfiguration()
            }
        }
    }

}