package dev.ng5m.util

fun interface Getter<T, O> {
    fun get(instance: T): O
}