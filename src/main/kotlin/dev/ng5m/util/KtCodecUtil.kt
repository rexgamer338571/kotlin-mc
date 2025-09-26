package dev.ng5m.util

import dev.ng5m.serialization.Codec

fun <T: Any> nullable(base: Codec<T>): Codec<T?> {
    return Codec.of(
        { buf -> if (buf.readBoolean()) base.read(buf) else null },
        { buf, value ->
            buf.writeBoolean(value == null)
            value?.let {
                base.write(buf, value)
            }
        }
    )
}