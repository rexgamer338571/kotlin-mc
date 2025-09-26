package dev.ng5m.serialization_kt

import net.kyori.adventure.key.Key
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.BitSet
import java.util.UUID
import java.util.function.Supplier
import kotlin.experimental.and

interface Codec<T> : Decoder<T>, Encoder<T> {
    companion object {
        private val TYPES = mutableMapOf<Class<*>, Codec<*>>()

        val VARINT by lazy { VarInt() }
        val VARINT_LIST by lazy { VARINT.list() }

        val BYTE = of(ByteIO::readByte, ByteIO::writeByte)
        val SHORT = of(ByteIO::readShort, ByteIO::writeShort)
        val INT = of(ByteIO::readInt, ByteIO::writeInt)
        val LONG = of(ByteIO::readLong, ByteIO::writeLong)
        val LONG_ARRAY = of(
            { LongArray(VARINT.read(it)) { _ -> it.readLong() } },
            { io, arr ->
                VARINT.write(io, arr.size)
                arr.forEach(io::writeLong)
            }
        )
        val FLOAT = of(ByteIO::readFloat, ByteIO::writeFloat)
        val DOUBLE = of(ByteIO::readDouble, ByteIO::writeDouble)
        val BOOLEAN = of(ByteIO::readBoolean, ByteIO::writeBoolean)

        val BYTE_ARRAY = of(
            { it.readBytes(VARINT.read(it)) },
            { buf, bytes ->
                VARINT.write(buf, bytes.size)
                buf.writeBytes(bytes)
            }
        )

        val STRING by lazy { BYTE_ARRAY.xmap(::String, String::encodeToByteArray) }

        val BEDROCK_STRING = of(
            { String(it.readBytes(it.readShort().toInt())) },
            { buf, s ->
                s.encodeToByteArray().also {
                    buf.writeShort(it.size.toShort())
                    buf.writeBytes(it)
                }
            }
        )

        val MUTF8 = of(
            {
                val `is` = it.inputStream()
                val dis = DataInputStream(`is`)

                return@of dis.readUTF()
            },
            { buf, s ->
                val os = buf.outputStream()
                val dos = DataOutputStream(os)

                dos.writeUTF(s)
            }
        )

        val UUID = of(
            LONG, java.util.UUID::getMostSignificantBits,
            LONG, java.util.UUID::getLeastSignificantBits,
            ::UUID
        )

        val REMAINING = of(
            { it.readBytes(it.writerIndex() - it.readerIndex()) },
            ByteIO::writeBytes
        )

        val KEY by lazy { STRING.xmap(Key::key, Key::asString) }

        val BIT_SET by lazy { LONG.list().xmap(
            { BitSet.valueOf(it.toLongArray()) },
            { it.toLongArray().toList() }
        ) }


        fun <T> of(reader: Decoder<T>, writer: Encoder<T>): Codec<T> = object : Codec<T> {
            override fun read(io: ByteIO): T = reader.read(io)
            override fun write(io: ByteIO, t: T) = writer.write(io, t)
        }

        inline fun <C1, C2, T> of(
            c1: Codec<C1>, crossinline s1: (T) -> C1,
            c2: Codec<C2>, crossinline s2: (T) -> C2,
            crossinline factory: (C1, C2) -> T
        ) = of(
            { factory(c1.read(it), c2.read(it)) },
            { buf, t ->
                c1.write(buf, s1(t))
                c2.write(buf, s2(t))
            }
        )

        fun <T> empty(value: T): Codec<T> = of(
            { value },
            { _, _ -> }
        )

        @Suppress("UNCHECKED_CAST")
        fun <T> codecFor(clazz: Class<T>): Codec<T>? = TYPES[clazz] as? Codec<T>
    }

    fun forType(clazz: Class<T>): Codec<T> {
        TYPES[clazz] = this
        return this
    }

    fun list(prefix: Codec<Int>): Codec<List<T>> = of(
        {
            val len = prefix.read(it)
            val list = mutableListOf<T>()
            for (i in 0 until len) list.add(read(it))

            return@of list
        },
        { io, list ->
            prefix.write(io, list.size)
            list.forEach { write(io, it) }
        }
    )

    fun list(): Codec<List<T>> = list(VARINT)

    fun <R> xmap(to: (T) -> R, from: (R) -> T): Codec<R> = of(
        { to(read(it)) },
        { buf, r -> write(buf, from(r)) }
    )

    fun <R> xmap(transcoder: Transcoder<T, R>): Codec<R> = xmap(transcoder::to, transcoder::from)



    class VarInt : Codec<Int> {
        private var lastOperationLength = 0

        override fun read(io: ByteIO): Int {
            var i = 0
            var j = 0
            while (true) {
                val byte = io.readByte()
                i = i or (byte and 0x7F.toByte()).toInt() shl j++ * 7
                require(j <= 5) { "VarInt too big" }

                if ((byte and 0x80.toByte()) != 128.toByte()) break
            }

            lastOperationLength = j
            return i
        }

        override fun write(io: ByteIO, i: Int) {
            lastOperationLength = 0

            while (true) {
                if ((i.toLong() and 0xFFFFFF80) == 0L) {
                    io.writeByte(i.toByte())
                    lastOperationLength = 1
                    return
                }

                io.writeByte((i and 0x7F or 0x80).toByte())
                lastOperationLength++
                i ushr 7
            }
        }

        fun varintSize(value: Int): Int {
            if (value == 0) return 1

            var size = 0
            while (true) {
                if ((value.toLong() and 0xFFFFFF80) == 0L) {
                    return ++size
                }

                size++
                value ushr 7
            }
        }

    }

}