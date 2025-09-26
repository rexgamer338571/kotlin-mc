package dev.ng5m.serialization_kt

@Suppress("UNCHECKED_CAST")
class DoubleMap<KV1, KV2>(factory: () -> Map<Any, Any>) {
    private val normal = factory() as MutableMap<KV1, KV2>
    private val reverse = factory() as MutableMap<KV2, KV1>

    constructor() : this(::HashMap)

    fun put(k: KV1, v: KV2) {
        normal[k] = v
        reverse[v] = k
    }

    fun getA(k: KV1): KV2? = normal[k]
    fun getB(k: KV2): KV1? = reverse[k]

    fun clear() {
        normal.clear()
        reverse.clear()
    }

    fun kv1Set(): MutableSet<KV1> = normal.keys

    fun kv2Set(): MutableSet<KV2> = reverse.keys

    fun entrySet1(): Set<Map.Entry<KV1, KV2>> = normal.entries
    fun entrySet2(): Set<Map.Entry<KV2, KV1>> = reverse.entries

    fun size(): Int = normal.size

    override fun toString(): String {
        val sb = StringBuilder("DoubleMap( ")

        for ((k, v) in normal.entries) {
            sb.append("(")
                .append(k)
                .append(" <-> ")
                .append(v)
                .append(")")
        }

        sb.append(" )")
        return sb.toString()
    }

}