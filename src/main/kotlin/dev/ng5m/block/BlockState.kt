package dev.ng5m.block

import dev.ng5m.util.Properties
import net.kyori.adventure.key.Key

data class BlockState(
    val id: Key,
    val properties: Properties
) {
    constructor(id: Key) : this(id, Properties.ofMap())

    fun <T> withProperty(property: BlockStateProperty<T>, value: T): BlockState {
        property.setter[properties] = value

        return this
    }

    fun <T> getProperty(property: BlockStateProperty<T>): T {
        return property.getter[properties]
    }

    override fun toString(): String {
        return "$id$properties"
    }
}
