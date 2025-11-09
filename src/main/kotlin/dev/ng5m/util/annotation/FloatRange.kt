package dev.ng5m.util.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.TYPE)
annotation class FloatRange(val from: Float, val to: Float)
