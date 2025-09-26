package dev.ng5m.util

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import java.util.zip.InflaterInputStream

fun decompressZL(input: ByteBuf, output: ByteBuf) {
    val iis = InflaterInputStream(ByteBufInputStream(input))
    val buffer = ByteArray(1024)
    var len: Int

    while ((iis.read(buffer).also { len = it }) != -1) {
        output.writeBytes(buffer.copyOf(len))
    }
}