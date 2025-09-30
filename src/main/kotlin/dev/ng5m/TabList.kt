package dev.ng5m

import dev.ng5m.packet.play.s2c.PlayerInfoRemoveS2CPacket
import dev.ng5m.packet.play.s2c.PlayerInfoUpdateS2CPacket
import dev.ng5m.packet.play.s2c.TabListS2CPacket
import net.kyori.adventure.text.Component
import java.util.EnumSet
import java.util.UUID

class TabList {
    var header: Component = Component.empty()
    var footer: Component = Component.empty()
    private val players = mutableMapOf<UUID, Int>()

    fun setPlayerIndex(uuid: UUID, index: Int) {
        players[uuid] = index
    }

    fun getPlayerIndex(uuid: UUID): Int? = players[uuid]

    fun update(connection: MinecraftConnection) {
        connection.sendPacket(TabListS2CPacket(header, footer))
        for ((uuid, index) in players) {
            connection.sendPacket(
                PlayerInfoUpdateS2CPacket(
                    EnumSet.of(
                        PlayerInfoUpdateS2CPacket.PlayerAction.Type.UPDATE_LIST_PRIORITY
                    ),
                    mapOf(
                        uuid to setOf(PlayerInfoUpdateS2CPacket.PlayerAction.UpdateListPriority(index))
                    )
                )
            )
        }
    }
}