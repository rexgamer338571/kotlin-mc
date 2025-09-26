package dev.ng5m.util

import kotlin.reflect.KFunction

data class ReflectConstructor<T>(private val constructor: KFunction<T>) {

    fun newInstance(vararg args: Any): T = constructor.call(args)
    fun newInstance(): T = constructor.call()

}