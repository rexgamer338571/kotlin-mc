package dev.ng5m.event.impl.player

import dev.ng5m.event.Event
import dev.ng5m.player.Player

data class PlayerJoinEvent(val player: Player) : Event