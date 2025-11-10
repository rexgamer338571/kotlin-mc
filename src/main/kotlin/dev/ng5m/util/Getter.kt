package dev.ng5m.util

fun interface Getter<T, O> {
    operator fun get(instance: T): O
}