package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.Entity
import dev.ng5m.entity.EntityMetadata
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class SetEntityDataS2CPacket(
    val entityId: Int,
    val metadata: EntityMetadata
) : Packet {
    companion object {
        val CODEC: Codec<SetEntityDataS2CPacket> = Codec.of(
            Codec.VARINT, { it.entityId },
            EntityMetadata.CODEC, { it.metadata },
            ::SetEntityDataS2CPacket
        ).forType(SetEntityDataS2CPacket::class.java)
    }

    constructor(entity: Entity) : this(entity.getEntityId(), entity.metadata)
}