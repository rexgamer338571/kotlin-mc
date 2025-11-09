package dev.ng5m.util

fun interface Setter<T, O> {
    fun set(instance: T, value: O)
}