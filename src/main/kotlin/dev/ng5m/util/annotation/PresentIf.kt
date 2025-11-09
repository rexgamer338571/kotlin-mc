package dev.ng5m.util.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
annotation class PresentIf(val condition: String)
