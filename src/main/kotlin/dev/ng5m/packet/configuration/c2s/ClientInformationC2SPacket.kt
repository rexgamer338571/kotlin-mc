package dev.ng5m.packet.configuration.c2s

import dev.ng5m.player.ChatMode
import dev.ng5m.player.Hand
import dev.ng5m.player.ParticleStatus
import dev.ng5m.player.SkinParts
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class ClientInformationC2SPacket(
    val locale: String,
    val viewDistance: Byte,
    val chatMode: ChatMode,
    val chatColors: Boolean,
    val skinParts: SkinParts,
    val mainHand: Hand,
    val enableTextFiltering: Boolean,
    val allowServerListings: Boolean,
    val particleStatus: ParticleStatus
) : Packet {
    companion object {
        val CODEC: Codec<ClientInformationC2SPacket> = Codec.of(
            Codec.STRING, ClientInformationC2SPacket::locale,
            Codec.BYTE, ClientInformationC2SPacket::viewDistance,
            Codec.ofEnum(ChatMode::class.java), ClientInformationC2SPacket::chatMode,
            Codec.BOOLEAN, ClientInformationC2SPacket::chatColors,
            SkinParts.CODEC, ClientInformationC2SPacket::skinParts,
            Codec.ofEnum(Hand::class.java), ClientInformationC2SPacket::mainHand,
            Codec.BOOLEAN, ClientInformationC2SPacket::enableTextFiltering,
            Codec.BOOLEAN, ClientInformationC2SPacket::allowServerListings,
            Codec.ofEnum(ParticleStatus::class.java), ClientInformationC2SPacket::particleStatus,
            ::ClientInformationC2SPacket
        ).forType(ClientInformationC2SPacket::class.java)
    }
}
