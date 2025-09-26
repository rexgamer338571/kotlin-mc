package dev.ng5m.packet.play.s2c

import dev.ng5m.player.GameMode
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class GameEventS2CPacket(
    val event: Event,
    val value: Float
) : Packet {
    companion object {
        val CODEC: Codec<GameEventS2CPacket> = Codec.of(
            Codec.ofEnum(Event::class.java), { it.event },
            Codec.FLOAT, { it.value },
            ::GameEventS2CPacket
        ).forType(GameEventS2CPacket::class.java)

        val NO_RESPAWN_BLOCK_AVAILABLE = GameEventS2CPacket(Event.NO_RESPAWN_BLOCK_AVAILABLE)
        val BEGIN_RAINING = GameEventS2CPacket(Event.BEGIN_RAINING)
        val END_RAINING = GameEventS2CPacket(Event.END_RAINING)

        fun changeGameMode(gameMode: GameMode): GameEventS2CPacket =
            GameEventS2CPacket(Event.CHANGE_GAME_MODE, gameMode.ordinal.toFloat())

        fun winGame(subEvent: WinGameEvent): GameEventS2CPacket =
            GameEventS2CPacket(Event.WIN_GAME, subEvent.ordinal.toFloat())

        fun demoEvent(subEvent: DemoEvent): GameEventS2CPacket =
            GameEventS2CPacket(Event.DEMO_EVENT, subEvent.i.toFloat())

        val ARROW_HIT_PLAYER = GameEventS2CPacket(Event.ARROW_HIT_PLAYER)
        val RAIN_LEVEL_CHANGE = GameEventS2CPacket(Event.RAIN_LEVEL_CHANGE)

        fun thunderLevelChange(level: Float) =
            GameEventS2CPacket(Event.RAIN_LEVEL_CHANGE, level)

        val PUFFERFISH_STING_SOUND = GameEventS2CPacket(Event.PUFFERFISH_STING_SOUND)
        val ELDER_GUARDIAN_APPEARANCE = GameEventS2CPacket(Event.ELDER_GUARDIAN_APPEARANCE)

        fun enableRespawnScreen(enable: Boolean) =
            GameEventS2CPacket(Event.ENABLE_RESPAWN_SCREEN, if (enable) 0.0f else 1.0f)

        fun limitedCrafting(enable: Boolean) =
            GameEventS2CPacket(Event.LIMITED_CRAFTING, if (enable) 1.0f else 0.0f)

        val START_WAITING_FOR_CHUNKS = GameEventS2CPacket(Event.START_WAITING_FOR_CHUNKS)
    }

    constructor(event: Event) : this(event, 0.0f)

    enum class Event {
        NO_RESPAWN_BLOCK_AVAILABLE,
        BEGIN_RAINING,
        END_RAINING,
        CHANGE_GAME_MODE,
        WIN_GAME,
        DEMO_EVENT,
        ARROW_HIT_PLAYER,
        RAIN_LEVEL_CHANGE,
        THUNDER_LEVEL_CHANGE,
        PUFFERFISH_STING_SOUND,
        ELDER_GUARDIAN_APPEARANCE,
        ENABLE_RESPAWN_SCREEN,
        LIMITED_CRAFTING,
        START_WAITING_FOR_CHUNKS
    }

    enum class WinGameEvent {
        RESPAWN,
        CREDITS_AND_RESPAWN
    }

    enum class DemoEvent(internal val i: Int) {
        WELCOME_TO_DEMO(0),
        MOVEMENT_CONTROLS(101),
        JUMP_CONTROL(102),
        INVENTORY_CONTROL(103),
        DEMO_OVER_PLUS_SCREENSHOT_TUTORIAL(104)
    }

}