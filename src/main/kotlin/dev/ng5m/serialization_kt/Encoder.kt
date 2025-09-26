package dev.ng5m.serialization_kt

fun interface Encoder<T> {
    fun write(io: ByteIO, t: T)
}