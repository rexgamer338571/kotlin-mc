package dev.ng5m.entity

import dev.ng5m.entity.EntityMetadata.Type
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.SetEntityDataS2CPacket
import dev.ng5m.player.Player

class ItemEntity(val stack: ItemStack) : Entity(EntityType.ITEM) {

    override fun spawnForPlayer(player: Player) {
        super.spawnForPlayer(player)

        val md = EntityMetadata.ofPairs(
            8 to (Type.SLOT to stack)
        )

        player.connection.sendPacket(SetEntityDataS2CPacket(getEntityId(), md))

    }

}