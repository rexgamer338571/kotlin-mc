package dev.ng5m.util

import dev.ng5m.serialization.Codec
import java.util.Optional
import kotlin.reflect.KClass

fun <T : Any> Codec<T>.nullable(): Codec<T?> = this.prefixedOptional().xmap<T>(
    { it.get() }, { Optional.ofNullable(it) }
)

inline fun <reified E : Enum<E>> ofEnum(): Codec<E> = Codec.ofEnum(E::class.java)

fun <T : Any> Codec<T>.forType(kClass: KClass<T>): Codec<T> = this.forType(kClass.java)