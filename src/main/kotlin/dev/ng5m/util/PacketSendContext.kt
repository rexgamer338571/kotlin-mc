package dev.ng5m.util

import dev.ng5m.serialization.Packet

class PacketSendContext(val packet: Packet) {
    private var ready = false;
    private val finishCallbacks = mutableSetOf<() -> Unit>()

    fun onFinish(callback: () -> Unit): PacketSendContext {
        finishCallbacks.add(callback)
        return this
    }

    fun finish() {
        finishCallbacks.forEach { it() }
        finishCallbacks.clear()
    }
}