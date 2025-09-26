package dev.ng5m.event

import java.util.function.Consumer

object EventManager {
    private val handlers: MutableMap<Class<out Event>, MutableSet<Consumer<out Event>>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T : Event> fire(event: T) {
        handlers[event.javaClass] ?. run {
            handlers[event.javaClass]!!.forEach { (it as Consumer<T>).accept(event) }
        }
    }

    fun <T : Event> register(clazz: Class<T>, handler: Consumer<T>) {
        handlers.computeIfAbsent(clazz) { _ -> mutableSetOf() }.add(handler)
    }
}