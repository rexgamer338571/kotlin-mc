package dev.ng5m.serialization_kt

import java.io.InputStream
import java.io.OutputStream

interface ByteIO {

    fun readByte(): Byte
    fun writeByte(byte: Byte)
    fun readShort(): Short
    fun writeShort(short: Short)
    fun readInt(): Int
    fun writeInt(int: Int)
    fun readLong(): Long
    fun writeLong(long: Long)
    fun readFloat(): Float
    fun writeFloat(float: Float)
    fun readDouble(): Double
    fun writeDouble(double: Double)
    fun readBoolean(): Boolean
    fun writeBoolean(boolean: Boolean)

    fun readBytes(nBytes: Int): ByteArray
    fun writeBytes(bytes: ByteArray)

    fun readerIndex(): Int
    fun writerIndex(): Int

    fun inputStream(): InputStream = throw UnsupportedOperationException()
    fun outputStream(): OutputStream = throw UnsupportedOperationException()

}