package dev.ng5m.serialization_kt

fun interface Decoder<T> {
    fun read(io: ByteIO): T
}