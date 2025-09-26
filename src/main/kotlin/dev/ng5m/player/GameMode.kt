package dev.ng5m.player

import dev.ng5m.serialization.annotation.Shift

@Shift(1)
enum class GameMode {
    UNDEFINED,
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;
}