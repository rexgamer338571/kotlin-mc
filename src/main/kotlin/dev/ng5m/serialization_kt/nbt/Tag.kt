package dev.ng5m.serialization_kt.nbt

abstract class Tag<V>(val value: V, var name: String = "") {
    constructor(name: String, value: V) : this(value, name)

    abstract fun type(): TagType

    enum class TagType {
        END,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BYTE_ARRAY,
        STRING,
        LIST,
        COMPOUND,
        INT_ARRAY,
        LONG_ARRAY,

        UNDEFINED;
    }

    override fun toString(): String = "${type().name}(${value.toString()})"


}