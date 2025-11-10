package dev.ng5m.util

fun interface Setter<T, O> {
    operator fun set(instance: T, value: O)
}