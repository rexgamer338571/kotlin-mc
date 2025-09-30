package dev.ng5m.util

import dev.ng5m.serialization.Codec
import java.util.Optional

fun <T : Any> Codec<T>.nullable(): Codec<T?> = this.prefixedOptional().xmap<T>(
    { it.get() }, { Optional.ofNullable(it) }
)