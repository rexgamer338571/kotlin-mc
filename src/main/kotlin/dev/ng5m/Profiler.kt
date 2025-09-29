package dev.ng5m

object Profiler {
    private val map = mutableMapOf<String, Long>()

    fun push(name: String) {
        map[name] = System.nanoTime()
    }

    fun pop(name: String) {
        map[name] = System.nanoTime() - map[name]!!
    }

    fun get(name: String): Long? = map[name]

}