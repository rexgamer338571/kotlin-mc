package dev.ng5m.serialization_kt

data class Either<F, S>(val first: F?, val second: S?) {
    companion object {
        fun <F, S> ofFirst(first: F): Either<F, S> = Either(first, null)
        @Suppress("UNCHECKED_CAST")
        fun <F, S> ofFirstUnsafe(first: Any): Either<F, S> = Either(first as F, null)
        fun <F, S> ofSecond(second: S): Either<F, S> = Either(null, second)
        @Suppress("UNCHECKED_CAST")
        fun <F, S> ofSecondUnsafe(second: Any): Either<F, S> = Either(null, second as S)
    }

    fun isFirst(): Boolean = first != null
    fun isSecond(): Boolean = second != null

    fun get(): Any = (first ?: second)!!

    fun first(): F { require(isFirst()); return first!! }
    fun second(): S { require(isSecond()); return second!! }

}