package dev.ng5m.item

import dev.ng5m.block.Face
import dev.ng5m.entity.Entity
import dev.ng5m.entity.EntityType
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.copy
import dev.ng5m.util.toDoubles
import dev.ng5m.util.toInts
import dev.ng5m.world.Location
import net.kyori.adventure.key.Key

class SpawnEggItem(key: Key, val entityType: EntityType) : Item(key) {
    constructor(key: Key, entityType: ResourceKey<EntityType>) : this(key, entityType.value())

    override fun onInteractBlock(
        stack: ItemStack,
        player: Player,
        location: Location,
        hand: Hand.Relative,
        blockFace: Face
    ) {
        val newLoc = location.withXYZ(location.xyz
            .copy()
            .add(blockFace.direction.toDoubles())
        )
        location.world.spawnEntity(newLoc, entityType.factory(entityType))
    }

}