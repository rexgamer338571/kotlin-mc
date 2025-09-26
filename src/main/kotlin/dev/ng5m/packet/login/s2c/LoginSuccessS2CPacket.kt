package dev.ng5m.packet.login.s2c

import dev.ng5m.player.Identity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class LoginSuccessS2CPacket(val identity: Identity) : Packet {
    companion object {
        val CODEC: Codec<LoginSuccessS2CPacket> = Codec.of(
            Identity.CODEC_REVERSE, LoginSuccessS2CPacket::identity,
            ::LoginSuccessS2CPacket
        ).forType(LoginSuccessS2CPacket::class.java)
    }
}
