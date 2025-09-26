package dev.ng5m.packet.play.s2c

import dev.ng5m.player.Player
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

class AnimateS2CPacket(val entityId: Int, val animation: Animation) : Packet {
    companion object {
        val CODEC: Codec<AnimateS2CPacket> = Codec.of(
            Codec.VARINT, { it.entityId },
            Codec.ofEnum(Animation::class.java), { it.animation },
            ::AnimateS2CPacket
        ).forType(AnimateS2CPacket::class.java)
    }

    constructor(player: Player, animation: Animation) : this(player.getEntityId(), animation)

    enum class Animation(val id: Int) {
        SWING_MAIN_ARM(0),
        LEAVE_BED(2),
        SWING_OFFHAND(3),
        CRITICAL_EFFECT(4),
        MAGIC_CRITICAL_EFFECT(5)
        ;
    }

}