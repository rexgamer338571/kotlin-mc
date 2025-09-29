package dev.ng5m.serialization_kt

data class Either<F, S>(val first: F?, val second: S?) {
    companion object {
        fun <F, S> ofFirst(first: F): Either<F, S> = Either(first, null)
        @Suppress("UNCHECKED_CAST")
        fun ofFirstUnsafe(first: Any?): Either<*, *> = Either(first, null)
        fun <F, S> ofSecond(second: S): Either<F, S> = Either(null, second)
        @Suppress("UNCHECKED_CAST")
        fun ofSecondUnsafe(second: Any?): Either<*, *> = Either(null, second)
    }

    fun isFirst(): Boolean = first != null
    fun isSecond(): Boolean = second != null

    fun get(): Any = (first ?: second)!!

    fun first(): F { require(isFirst()); return first!! }
    fun second(): S { require(isSecond()); return second!! }

}