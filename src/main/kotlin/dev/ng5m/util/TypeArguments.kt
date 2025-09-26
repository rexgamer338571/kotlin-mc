package dev.ng5m.util

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class TypeArguments(val value: Array<KClass<out Any>>)
