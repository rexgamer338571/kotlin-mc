package dev.ng5m.serialization_kt

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readByteArray
import kotlinx.io.readDouble
import kotlinx.io.readFloat
import kotlinx.io.writeDouble
import kotlinx.io.writeFloat
import java.io.InputStream
import java.io.OutputStream

data class KtByteIO(private val source: Source, private val sink: Sink) : ByteIO {
    private var writerIndex = 0
    private var readerIndex = 0

    private fun advanceRead(nBytes: Int) {
        readerIndex += nBytes
    }

    private fun advanceWrite(nBytes: Int) {
        writerIndex += nBytes
    }

    override fun readByte(): Byte = source.readByte().also { advanceRead(1) }
    override fun writeByte(byte: Byte) = sink.writeByte(byte).also { advanceWrite(1) }
    override fun readShort(): Short = source.readShort().also { advanceRead(2) }
    override fun writeShort(short: Short) = sink.writeShort(short).also { advanceWrite(2) }
    override fun readInt(): Int = source.readInt().also { advanceRead(4) }
    override fun writeInt(int: Int) = sink.writeInt(int).also { advanceWrite(4) }
    override fun readLong(): Long = source.readLong().also { advanceRead(8) }
    override fun writeLong(long: Long) = sink.writeLong(long).also { advanceWrite(8) }
    override fun readFloat(): Float = source.readFloat().also { advanceRead(4) }
    override fun writeFloat(float: Float) = sink.writeFloat(float).also { advanceWrite(4) }
    override fun readDouble(): Double = source.readDouble().also { advanceRead(8) }
    override fun writeDouble(double: Double) = sink.writeDouble(double).also { advanceWrite(8) }
    override fun readBoolean(): Boolean = readByte() == 0x01.toByte().also { advanceRead(1) }
    override fun writeBoolean(boolean: Boolean) = writeByte(if (boolean) 0x01 else 0x00).also { advanceWrite(1) }
    override fun readBytes(nBytes: Int): ByteArray = source.readByteArray(nBytes).also { advanceRead(it.size) }
    override fun writeBytes(bytes: ByteArray) = sink.write(bytes).also { advanceWrite(bytes.size) }
    override fun readerIndex(): Int = readerIndex
    override fun writerIndex(): Int = writerIndex

    override fun inputStream(): InputStream = object : InputStream() {
        override fun read(): Int = readByte().toInt()
    }

    override fun outputStream(): OutputStream = object : OutputStream() {
        override fun write(b: Int) = writeByte(b.toByte())
    }

    constructor(buffer: Buffer) : this(buffer, buffer)
}