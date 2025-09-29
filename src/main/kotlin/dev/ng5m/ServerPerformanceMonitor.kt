package dev.ng5m

object ServerPerformanceMonitor {
    private var tick = -1L

    fun tick(ns: Long) {
        tick = ns
    }

    override fun toString(): String = "${tick / 1_000_000}MSPT"
}