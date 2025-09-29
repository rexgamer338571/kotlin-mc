package dev.ng5m.packet.play.s2c

import dev.ng5m.serialization.Packet
import dev.ng5m.serialization_kt.Codec
import java.util.UUID

data class PlayerInfoRemoveS2CPacket(val uuids: List<UUID>) : Packet {
    companion object {
        val CODEC: Codec<PlayerInfoRemoveS2CPacket> = Codec.UUID.list().xmap(::PlayerInfoRemoveS2CPacket) { it.uuids }
    }

    constructor(vararg uuids: UUID) : this(uuids.toList())
}