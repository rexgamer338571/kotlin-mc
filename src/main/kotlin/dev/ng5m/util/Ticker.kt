package dev.ng5m.util

import dev.ng5m.Ticking
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

class Ticker(val delayMS: Long, val events: Events) {
    constructor() : this(-1, Events.NONE)

    private val tickingObjects: MutableSet<Ticking> = ConcurrentHashMap.newKeySet()
    private lateinit var thread: Thread

    fun ticking(): Set<Ticking> = tickingObjects.toSet()

    fun submit(ticking: Ticking) {
        tickingObjects.add(ticking)
    }

    fun remove(ticking: Ticking) {
        tickingObjects.remove(ticking)
    }

    fun start() {
        require(delayMS != -1L) { "Ticker not initialized with an interval" }

        thread = thread(start = false, block = {
            while (true) {
                events.startTick()
                tick()
                events.endTick()
                Thread.sleep(delayMS)
            }
        })
        thread.start()
    }

    fun stop() {
        thread.interrupt()
    }

    fun tick() {
        tickingObjects.forEach { it.tick() }
    }

    interface Events {
        companion object {
            val NONE = object : Events {
                override fun startTick() {
                }

                override fun endTick() {
                }
            }
        }

        fun startTick()
        fun endTick()
    }
}