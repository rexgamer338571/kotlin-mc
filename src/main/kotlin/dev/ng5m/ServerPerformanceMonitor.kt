package dev.ng5m

object ServerPerformanceMonitor {
    private var tick = -1L

    fun tick(ns: Long) {
        tick = ns
    }

    fun getMemoryUsage(): Long {
        val rt = Runtime.getRuntime()
        return rt.totalMemory() - rt.freeMemory()
    }

    override fun toString(): String = "${tick / 1_000_000} MSPT Mem: ${getMemoryUsage() / 1_000_000} MB"
}