package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.Entity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class RemoveEntitiesS2CPacket(val eIDs: List<Int>) : Packet {
    companion object {
        val CODEC: Codec<RemoveEntitiesS2CPacket> = Codec.of(
            Codec.VARINT_LIST, { it.eIDs }, ::RemoveEntitiesS2CPacket
        ).forType(RemoveEntitiesS2CPacket::class.java)
    }

    constructor(vararg entities: Entity) : this(entities.map { it.getEntityId() })

}