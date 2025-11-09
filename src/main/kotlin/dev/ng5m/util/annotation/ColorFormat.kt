package dev.ng5m.util.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.TYPE)
annotation class ColorFormat(val format: Format) {

    enum class Format {
        ARGB,
        URGB
    }

}
