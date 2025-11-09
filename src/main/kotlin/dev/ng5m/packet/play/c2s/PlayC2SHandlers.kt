package dev.ng5m.packet.play.c2s

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerMoveEvent
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.AnimateS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.util.math.Vector3i
import dev.ng5m.util.toDoubles
import dev.ng5m.util.toInts
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

    fun chatMessage(connection: MinecraftConnection, packet: ChatMessageC2SPacket) {
//        MinecraftServer.getInstance().getPlayingConnections()
//            .filter { it.player.chatMode == ChatMode.ENABLED }
//            .forEach {
//
//            }
    }

    fun containerClick(connection: MinecraftConnection, packet: ContainerClickC2SPacket) {
        if (packet.windowId == 0 && connection.player.openInventory != null) return
        if (packet.windowId != 0 && packet.windowId != connection.player.openInventory!!.id()) return

        val player = connection.player
        val inv = player.openInventory ?: player.inventory

//        if (inv.revision() != packet.revision) {
//            connection.sendPacket(
//                SetContainerContentsS2CPacket(
//                    inv.id(), inv.revision(), inv.slots(), player.carriedItem
//                )
//            )
//
//            return
//        }


        val updatedSlots = mutableMapOf<Int, ItemStack>()

        fun getItem(slot: Int): ItemStack {
            return (
                    if (player.openInventory == null)
                        player.inventory.getItem(slot)
                    else
                        if (slot >= player.openInventory!!.slots().size)
                            player.inventory.getItem(slot - player.openInventory!!.slots().size + 9)
                        else
                            player.openInventory!!.getItem(slot)
                    )
        }

        fun setItem(slot: Int, stack: ItemStack) {
            if (player.openInventory == null)
                player.inventory.setItem(slot, stack)
            else
                if (slot >= player.openInventory!!.slots().size)
                    player.inventory.setItem(slot - player.openInventory!!.slots().size + 9, stack)
                else
                    player.openInventory!!.setItem(slot, stack)

            updatedSlots[slot] = stack
        }

        val slot = packet.slot.toInt()
        val button = packet.button.toInt()
        when (packet.mode) {
            0 -> {
                if (slot == -999) {
                    if (player.carriedItem == ItemStack.AIR) return

                    when (button) {
                        0 -> {
                            player.dropItem(player.carriedItem)
                            player.carriedItem = ItemStack.AIR
                        }

                        1 -> {
                            player.dropItem(player.carriedItem.clone().withCount(1))
                            player.carriedItem.withCount(player.carriedItem.count() - 1)
                        }
                    }
                } else {
                    when (button) {
                        0 -> {
                            val itemAt = getItem(slot)
                            if (!(player.carriedItem == ItemStack.AIR && itemAt == ItemStack.AIR)) {
                                if (player.carriedItem == ItemStack.AIR) {
                                    player.carriedItem = itemAt
                                    setItem(slot, ItemStack.AIR)
                                } else {
                                    if (itemAt == ItemStack.AIR) {
                                        setItem(slot, player.carriedItem)
                                        player.carriedItem = ItemStack.AIR
                                    } else {
                                        if (itemAt.isSimilar(player.carriedItem)) {
                                            if (itemAt.count() < itemAt.maxStackSize()) {
                                                val left =
                                                    player.carriedItem.count() - (itemAt.maxStackSize() - itemAt.count())
                                                itemAt.withCount(if (left <= 0) player.carriedItem.count() + itemAt.count() else itemAt.maxStackSize())
                                                player.carriedItem.withCount(left)
                                            }
                                        } else {
                                            val tmp = player.carriedItem.clone()
                                            player.carriedItem = itemAt
                                            setItem(slot, tmp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        MinecraftServer.getInstance().getPlayers()
            .filter { it.openInventory == inv }
            .forEach { player ->
                updatedSlots.forEach {
                    player.updateSlot(inv, it.key.toShort(), it.value)
                }
            }

        player.updateCarriedItem()
        inv.incrementRevision()
    }

    fun containerClose(connection: MinecraftConnection, packet: ContainerCloseC2SPacket) {
        connection.player.openInventory = null
    }

    fun interact(connection: MinecraftConnection, packet: InteractC2SPacket) {
        val entity = MinecraftServer.getInstance().getEntity(packet.entityId) ?: return

        connection.player.sneaking = packet.sneaking

        entity.onInteracted(connection.player, packet)
        when (packet.type) {
            InteractC2SPacket.Type.ATTACK -> entity.onAttacked(connection.player, packet.sneaking)
            InteractC2SPacket.Type.INTERACT -> entity.onInteracted(connection.player, packet.hand!!, packet.sneaking)
            InteractC2SPacket.Type.INTERACT_AT -> entity.onInteractedAt(connection.player, packet.hand!!, packet.relativePos!!, packet.sneaking)
        }
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

    fun playerAction(connection: MinecraftConnection, packet: PlayerActionC2SPacket) {
        val player = connection.player

        when (packet.action) {
            PlayerActionC2SPacket.Action.DROP_ITEM -> player.dropHeldItem(false)
            PlayerActionC2SPacket.Action.DROP_ITEM_STACK -> player.dropHeldItem(true)

            else -> {}
        }
    }

    fun playerCommand(connection: MinecraftConnection, packet: PlayerCommandC2SPacket) {
        val player = connection.player
        if (packet.action == PlayerCommandC2SPacket.Action.START_SPRINTING) player.sprinting = true
        if (packet.action == PlayerCommandC2SPacket.Action.STOP_SPRINTING) player.sprinting = false
    }

    fun input(connection: MinecraftConnection, packet: PlayerInputC2SPacket) {
        val player = connection.player
        player.sneaking = packet.flags.sneak
    }

    fun loaded(connection: MinecraftConnection, packet: PlayerLoadedC2SPacket) {
        println("loaded!! woa!")
    }

    fun setCarriedItem(connection: MinecraftConnection, packet: SetCarriedItemC2SPacket) {
        connection.player.heldItem = packet.slot.toInt()
    }

    fun setCreativeModeSlot(connection: MinecraftConnection, packet: SetCreativeModeSlotC2SPacket) {
        val slot = packet.slot.toInt()
        val player = connection.player
        val inv = player.inventory

        if (slot == -1) {
            player.dropItem(player.carriedItem)
            player.carriedItem = ItemStack.AIR
        } else {
            if (packet.itemStack == ItemStack.UNDEFINED) {
                player.carriedItem = inv.getItem(slot)
                inv.clearItem(slot)
            } else {
                player.carriedItem = ItemStack.AIR
                inv.setItem(slot, packet.itemStack)
                connection.player.updateSlot(connection.player.inventory, packet.slot, packet.itemStack)
            }
        }

        player.updateCarriedItem()
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

    fun useItemOn(connection: MinecraftConnection, packet: UseItemOnC2SPacket) {
        val world = connection.player.getWorld()
        val pos = packet.blockPos
        val block = world.getBlockAt(pos)

        val heldStack = connection.player.getItemInHand(packet.hand)
        heldStack.item.onInteractBlock(heldStack, connection.player, Location(world, pos), packet.hand, packet.face)
        if (!connection.player.sneaking) {
            block.onInteract(connection.player, packet.hand, packet.face, packet.cursorPos,
                world.getBlockEntityAt(pos.x, pos.y, pos.z))
        }
    }

    private fun fireMove(player: Player) {
        val ev = PlayerMoveEvent(player, player.previousLocation, player.location)
        EventManager.fire(ev)

        if (!ev.cancelled)
            player.move()
    }

    private fun validateMove(connection: MinecraftConnection): Boolean = !connection.syncingPosition

}