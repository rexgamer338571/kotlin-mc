package dev.ng5m.util

fun interface TriConsumer<A, B, C> {
    fun accept(a: A, b: B, c: C)
}