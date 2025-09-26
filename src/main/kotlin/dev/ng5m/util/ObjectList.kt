package dev.ng5m.util

import org.jetbrains.annotations.NotNull

class ObjectList<T>(initialSize: Int) {
    companion object {
        inline fun <reified T> new(initialSize: Int): ObjectList<T> {
            return ObjectList(initialSize)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private var array: Array<T?> = Array<Any>(initialSize) { _ -> } as Array<T?>

    fun set(index: Int, @NotNull t: T) {
        t!!
        if (index > array.size) {
            array = array.copyOf(index * 2 + 1)
        }

        array[index] = t
    }

}