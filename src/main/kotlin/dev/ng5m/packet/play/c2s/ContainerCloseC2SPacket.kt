package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class ContainerCloseC2SPacket(
    val windowId: Int
) : Packet {
    companion object {
        val CODEC: Codec<ContainerCloseC2SPacket> = Codec.of(
            Codec.VARINT, { it.windowId }, ::ContainerCloseC2SPacket
        ).forType(ContainerCloseC2SPacket::class.java)
    }
}