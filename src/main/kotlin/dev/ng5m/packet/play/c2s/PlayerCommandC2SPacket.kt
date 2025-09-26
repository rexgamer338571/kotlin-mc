package dev.ng5m.packet.play.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import org.jetbrains.annotations.Range

data class PlayerCommandC2SPacket(
    val entityID: Int,
    val action: Action,
    val jumpBoost: @Range(from = 0, to = 100) Int
) : Packet {
    companion object {
        val CODEC: Codec<PlayerCommandC2SPacket> = Codec.of(
            Codec.VARINT, { it.entityID },
            Codec.ofEnum(Action::class.java), { it.action },
            Codec.VARINT, { it.jumpBoost },
            ::PlayerCommandC2SPacket
        ).forType(PlayerCommandC2SPacket::class.java)
    }

    enum class Action {
        PRESS_SNEAK_KEY,
        RELEASE_SNEAK_KEY,
        LEAVE_BED,
        START_SPRINTING,
        STOP_SPRINTING,
        START_JUMP_WITH_HORSE,
        STOP_JUMP_WITH_HORSE,
        OPEN_VEHICLE_INVENTORY,
        START_FLYING_WITH_ELYTRA
    }

}