package dev.ng5m.util

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties

data class ReflectObject<T : Any>(
    val o: T?,
    val clazz: KClass<out T>
) {
    companion object {
        fun <T : Any> ofObject(o: T): ReflectObject<T> = ReflectObject(o, o::class)
        fun <T : Any> ofClass(clazz: KClass<out T>) = ReflectObject(null, clazz)
    }

    fun findDefaultConstructor(): ReflectConstructor<T> = ReflectConstructor(clazz.constructors.first { it.parameters.isEmpty() })

    fun getField(name: String): KProperty<*> = clazz.declaredMemberProperties.first { it.name == name }

}