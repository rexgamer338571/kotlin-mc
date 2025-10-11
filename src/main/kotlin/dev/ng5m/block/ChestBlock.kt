package dev.ng5m.block

import dev.ng5m.entity.BlockEntity
import dev.ng5m.entity.BlockEntityType
import dev.ng5m.entity.inventory.ArrayBackedInventory
import dev.ng5m.entity.inventory.Inventory
import dev.ng5m.entity.inventory.InventoryType
import dev.ng5m.entity.inventory.TitledInventory
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetContainerContentsS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.impl.StringTag
import dev.ng5m.util.math.Vector3f
import dev.ng5m.util.math.Vector3i
import dev.ng5m.util.toNBT
import net.kyori.adventure.text.Component

object ChestBlock : Block() {
    private val name: Component = Component.text("Chest")

    override fun createBlockEntity(x: Int, y: Int, z: Int, state: BlockState): BlockEntity {
        return BlockEntity(x, y, z, BlockEntityType.CHEST, CompoundTag())
    }

    override fun onInteract(player: Player, hand: Hand.Relative, face: Face, cursorPos: Vector3f,
                            blockEntity: BlockEntity?) {
        require(blockEntity != null) { "blockEntity is null" }
        require(blockEntity is ChestBlockEntity) { "blockEntity is not ChestBlockEntity" }

        player.sendSystemMessage(Component.text("clicked on chest"))

        if (blockEntity.inventory.slots().any { it != ItemStack.AIR })
            println(blockEntity.inventory.slots().first { it != ItemStack.AIR }.item.key)

        player.openInventory(blockEntity.inventory)
    }

    class ChestBlockEntity(x: Int, y: Int, z: Int, data: CompoundTag) : BlockEntity(x, y, z, BlockEntityType.CHEST, data) {
        val inventory: Inventory = Inventory.createGeneric9x3(name)

    }

}