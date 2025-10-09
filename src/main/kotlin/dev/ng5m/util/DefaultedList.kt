package dev.ng5m.util

class DefaultedList<T>(size: Int, val default: T) : ArrayList<T>(size) {

    init {
        for (i in 0 until size) add(default)
    }

    fun clear(index: Int) {
        set(index, default)
    }

}