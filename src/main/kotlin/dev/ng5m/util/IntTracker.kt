package dev.ng5m.util

import kotlin.math.max

class IntTracker(private val start: Int, private val step: IntProvider = IntProvider.Constant(1), private val max: Int = Int.MAX_VALUE) {
    private var i: Int = start
    private val map: MutableMap<Int, () -> Unit> = mutableMapOf()


    constructor() : this(-1)

    fun next(callback: () -> Unit): Int {
        i = max(start, i % max + 1)
        map[i] = callback
        return i
    }

    fun next(): Int = next { }

    fun current(): Int = i

    fun finish(i: Int) {
        (map[i] ?: return)()
    }

    fun validate(v: Int): Boolean = map[v] != null

}