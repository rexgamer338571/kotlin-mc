package dev.ng5m.serialization_kt.nbt

abstract class Tag(val value: Any, var name: String = "") {
    constructor(name: String, value: Any) : this(value, name)

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