package dev.ng5m.event.impl.player

import dev.ng5m.event.Event
import dev.ng5m.player.Player
import dev.ng5m.world.Location

data class PlayerMoveEvent(val player: Player, val from: Location, val to: Location) : Event
