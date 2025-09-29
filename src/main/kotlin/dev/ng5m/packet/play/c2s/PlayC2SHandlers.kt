package dev.ng5m.packet.play.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerMoveEvent
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.AnimateS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.world.Location
import net.kyori.adventure.text.Component

object PlayC2SHandlers {

    fun acceptTeleportation(connection: MinecraftConnection, packet: AcceptTeleportationC2SPacket) {
        if (!connection.player.teleportIdTracker.validate(packet.teleportId)) {
            connection.player.disconnect(Component.text("Received unknown teleport id: ${packet.teleportId}"))
            return
        }

        connection.syncingPosition = false
        connection.player.teleportIdTracker.finish(packet.teleportId)
    }

    fun movePos(connection: MinecraftConnection, packet: PlayerMoveC2SPacket.Pos) {
        if (!validateMove(connection)) return

        val player = connection.player
        player.previousLocation = player.location.clone()

        player.location.xyz = packet.xyz
        player.onGround = packet.flags.onGround
        player.pushingAgainstWall = packet.flags.pushingAgainstWall

        fireMove(player)
    }

    fun movePosRot(connection: MinecraftConnection, packet: PlayerMoveC2SPacket.PosRot) {
        if (!validateMove(connection)) return

        val player = connection.player
        player.previousLocation = player.location.clone()
        player.location.xyz = packet.xyz
        player.location.yaw = packet.yaw
        player.headYaw = packet.yaw
        player.location.pitch = packet.pitch
        player.onGround = packet.flags.onGround
        player.pushingAgainstWall = packet.flags.pushingAgainstWall
        fireMove(player)
    }

    fun moveRot(connection: MinecraftConnection, packet: PlayerMoveC2SPacket.Rot) {
        if (!validateMove(connection)) return

        val player = connection.player
        player.previousLocation = player.location.clone()
        player.location.yaw = packet.yaw
        player.headYaw = packet.yaw
        player.location.pitch = packet.pitch
        fireMove(player)
    }

    fun moveStatus(connection: MinecraftConnection, packet: PlayerMoveC2SPacket.Status) {
        if (!validateMove(connection)) return

        val player = connection.player
        player.previousLocation = player.location.clone()
        player.onGround = packet.flags.onGround
        player.pushingAgainstWall = packet.flags.pushingAgainstWall
        fireMove(player)
    }

    fun playerCommand(connection: MinecraftConnection, packet: PlayerCommandC2SPacket) {
        val player = connection.player
        if (packet.action == PlayerCommandC2SPacket.Action.START_SPRINTING) player.sprinting = true
        if (packet.action == PlayerCommandC2SPacket.Action.STOP_SPRINTING) player.sprinting = false
    }

    fun input(connection: MinecraftConnection, packet: PlayerInputC2SPacket) {

    }

    fun loaded(connection: MinecraftConnection, packet: PlayerLoadedC2SPacket) {
        println("loaded!! woa!")
    }

    fun setCreativeModeSlot(connection: MinecraftConnection, packet: SetCreativeModeSlotC2SPacket) {
        val slot = packet.slot.toInt()
        val inv = connection.player.inventory

        if (slot == -1) {
            // TODO drop item

            return
        }

        if (packet.itemStack == ItemStack.UNDEFINED) {
            inv.carriedItem = inv.getItem(slot)
            inv.clearItem(slot)
        } else {
            inv.carriedItem = null
            inv.setItem(slot, packet.itemStack)
        }
    }

    fun swingArm(connection: MinecraftConnection, packet: SwingArmC2SPacket) {
        val animation =
            if (packet.hand == Hand.Relative.MAIN_HAND)
                AnimateS2CPacket.Animation.SWING_MAIN_ARM
            else AnimateS2CPacket.Animation.SWING_OFFHAND
        connection.player.getOtherPlayers().forEach {
            it.connection.sendPacket(AnimateS2CPacket(connection.player, animation))
        }
    }

    private fun fireMove(player: Player) {
        EventManager.fire(PlayerMoveEvent(player, player.previousLocation, player.location))
    }

    private fun validateMove(connection: MinecraftConnection): Boolean = !connection.syncingPosition

}