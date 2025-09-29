package dev.ng5m.event

import java.util.function.Consumer
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.jvmErasure

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

    fun <L : EventListeners> register(instance: L) {
        for (method in instance::class.declaredMemberFunctions) {
            if (!method.hasAnnotation<EventHandler>()) continue
            val params = method.parameters
            if (params.size != 2) continue
            if (!params[1].type.isSubtypeOf(Event::class.starProjectedType)) continue

            @Suppress("UNCHECKED_CAST")
            register(method.parameters[1].type.jvmErasure.java as Class<out Event>) { ev ->
                method.call(instance, ev)
            }
        }
    }
}