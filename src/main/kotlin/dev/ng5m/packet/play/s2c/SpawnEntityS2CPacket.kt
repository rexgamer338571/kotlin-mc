package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.Entity
import dev.ng5m.entity.EntityType
import dev.ng5m.entity.LivingEntity
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.math.Vector3d
import dev.ng5m.util.math.Vector3s
import dev.ng5m.util.sneakyThrow
import dev.ng5m.world.Location
import java.util.UUID

data class SpawnEntityS2CPacket(
    val eid: Int, val uuid: UUID, val type: ResourceKey<EntityType>,
    val location: Location, val headYaw: Float, val data: Int,
    val velocity: Vector3s
) : Packet {
    companion object {
        val CODEC: Codec<SpawnEntityS2CPacket> = Codec.of(
            Codec.VARINT, { it.eid },
            Codec.UUID, { it.uuid },
            Registries.ENTITY_TYPE.idCodec, { it.type },
            Vector3d.CODEC_3_DOUBLES, { it.location.xyz },
            Codec.ANGLE, { it.location.pitch },
            Codec.ANGLE, { it.location.yaw },
            Codec.ANGLE, { it.headYaw },
            Codec.VARINT, { it.data },
            Vector3s.CODEC_3_SHORTS, { it.velocity },
            { _, _, _, _, _, _, _, _, _ -> sneakyThrow<SpawnEntityS2CPacket>(UnsupportedOperationException()) }
        ).forType(SpawnEntityS2CPacket::class.java)
    }

    constructor(entity: Entity) : this(
        entity.getEntityId(), if (entity is Player) entity.getIdentity().getAdequateUUID() else entity.uuid, entity.typeKey(),
        entity.location, if (entity is LivingEntity) entity.headYaw else 0.0F, entity.getEntityData(),
        entity.velocity.clone().mul(8000.0).toShorts()
    )

}