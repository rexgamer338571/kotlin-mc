package dev.ng5m.packet.configuration

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.KnownPack

data class KnownPacksPacket(
    val packs: List<KnownPack>
) : Packet {
    companion object {
        val CODEC: Codec<KnownPacksPacket> = Codec.of(
            KnownPack.CODEC.list(), KnownPacksPacket::packs,
            ::KnownPacksPacket
        ).forType(KnownPacksPacket::class.java)
    }
}
