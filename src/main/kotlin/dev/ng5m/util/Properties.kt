package dev.ng5m.util

interface Properties {
    companion object {
        fun ofMap(map: Map<String, *>): Properties = MapBacked(map)
        fun ofMap(): Properties = MapBacked(mapOf<String, Any>())
    }

    fun getSection(name: String): Properties?

    fun getNumber(name: String): Number?

    fun <T : Number> getNumber(name: String, transformer: (Number) -> T): T? {
        return transformer.invoke(getNumber(name) ?: return null)
    }

    fun getByte(name: String): Byte? { return getNumber(name, Number::toByte) }
    fun getShort(name: String): Short? { return getNumber(name, Number::toShort) }
    fun getInt(name: String): Int? { return getNumber(name, Number::toInt) }
    fun getLong(name: String): Long? { return getNumber(name, Number::toLong) }
    fun getFloat(name: String): Float? { return getNumber(name, Number::toFloat) }
    fun getDouble(name: String): Double? { return getNumber(name, Number::toDouble) }

    fun getBoolean(name: String): Boolean?
    fun getString(name: String): String?
    fun <T> getList(name: String): List<T>?


    @Suppress("UNCHECKED_CAST")
    private data class MapBacked(
        private val map: Map<String, *>
    ) : Properties {
        private fun get(name: String): Any? {
            return map[name]
        }

        override fun getSection(name: String): Properties? {
            val got = get(name) ?: return null
            return MapBacked(got as Map<String, *>)
        }

        override fun getNumber(name: String): Number? {
            return (get(name) ?: return null) as Number
        }

        override fun getBoolean(name: String): Boolean {
            return (get(name) ?: return false) as Boolean
        }

        override fun getString(name: String): String? {
            return (get(name) ?: return null) as String
        }

        override fun <T> getList(name: String): List<T>? {
            return (get(name) ?: return null) as List<T>
        }

        override fun toString(): String {
            val sb = StringBuilder("[")
            for ((k, v) in map) sb.append("$k=$v")
            return sb.append("]").toString()
        }

    }

}