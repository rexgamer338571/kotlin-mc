package dev.ng5m.util

import dev.ng5m.serialization.Codec

data class U4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D) {
    companion object {
        fun <A, B, C, D> codec(ca: Codec<A>, cb: Codec<B>, cc: Codec<C>, cd: Codec<D>): Codec<U4<A, B, C, D>> {
            return Codec.of(
                ca, { it.a },
                cb, { it.b },
                cc, { it.c },
                cd, { it.d },
                ::U4
            )
        }
    }
}
