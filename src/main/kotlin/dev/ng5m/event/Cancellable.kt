package dev.ng5m.event

interface Cancellable {
    fun cancelled(): Boolean
}