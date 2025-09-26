package dev.ng5m.serialization_kt

interface Transcoder<T, R> {
    fun to(t: T): R
    fun from(r: R): T
}