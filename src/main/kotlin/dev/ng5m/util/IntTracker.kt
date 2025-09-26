package dev.ng5m.util

class IntTracker(private val start: Int, private val step: IntProvider) {
    private var i: Int = start
    private val map: MutableMap<Int, () -> Unit> = mutableMapOf()

    constructor() : this(-1, IntProvider.Constant(1))

    fun next(callback: () -> Unit): Int {
        map[++i] = callback
        return i
    }

    fun next(): Int = next { }

    fun current(): Int = i

    fun finish(i: Int) {
        (map[i] ?: return)()
    }

    fun validate(v: Int): Boolean = v == i

}