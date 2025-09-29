package dev.ng5m.event

import dev.ng5m.event.impl.player.PlayerMoveEvent

object EntityEvents : EventListeners {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        event.player.move()
    }

}