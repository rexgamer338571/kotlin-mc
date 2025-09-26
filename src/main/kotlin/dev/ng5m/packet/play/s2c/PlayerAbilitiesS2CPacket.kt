package dev.ng5m.packet.play.s2c

import dev.ng5m.player.PlayerAbilities
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class PlayerAbilitiesS2CPacket(val abilities: PlayerAbilities) : Packet {
    companion object {
        val CODEC: Codec<PlayerAbilitiesS2CPacket> = Codec.of(
            PlayerAbilities.CODEC, { it.abilities }, ::PlayerAbilitiesS2CPacket
        ).forType(PlayerAbilitiesS2CPacket::class.java)
    }
}