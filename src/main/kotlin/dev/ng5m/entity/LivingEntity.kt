package dev.ng5m.entity

import dev.ng5m.packet.play.s2c.RotateHeadS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.ResourceKey

open class LivingEntity : Entity {
    companion object {
        val METADATA_B8 = EntityMetadata.Index(8, EntityMetadata.Type.BYTE, 0.toByte())
        val METADATA_HEALTH = EntityMetadata.Index(9, EntityMetadata.Type.FLOAT, 1f)
        val METADATA_POTION_EFFECT_COLOR = EntityMetadata.Index(10, EntityMetadata.Type.PARTICLES, listOf())
        val METADATA_AMBIENT_POTION_EFFECT = EntityMetadata.Index(11, EntityMetadata.Type.BOOLEAN, false)
        val METADATA_ARROWS = EntityMetadata.Index(12, EntityMetadata.Type.VARINT, 0)
        val METADATA_BEE_STINGERS = EntityMetadata.Index(13, EntityMetadata.Type.VARINT, 0)
        val METADATA_BED_LOCATION = EntityMetadata.Index(14, EntityMetadata.Type.OPTIONAL_POSITION, null)
    }

    var headYaw = 0F

    protected constructor(type: EntityType) : super(type)

    protected constructor(typeKey: ResourceKey<EntityType>, id: Int) : super(typeKey, id)

    constructor(typeKey: ResourceKey<EntityType>) : super(typeKey)

    override fun spawnForPlayer(player: Player) {
        super.spawnForPlayer(player)

        player.connection.sendPacket(RotateHeadS2CPacket(this))
    }

}