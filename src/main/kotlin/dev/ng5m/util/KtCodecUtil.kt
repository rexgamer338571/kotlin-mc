package dev.ng5m.util

import dev.ng5m.serialization.Codec
import java.util.Optional

fun <T : Any> nullable(base: Codec<T>): Codec<T?> = base.prefixedOptional().xmap<T>(
    { it.get() }, { Optional.ofNullable(it) }
)