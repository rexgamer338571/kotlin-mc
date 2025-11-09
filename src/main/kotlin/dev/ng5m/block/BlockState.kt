package dev.ng5m.block

import dev.ng5m.registry.Registries
import dev.ng5m.registry.Registry
import dev.ng5m.util.Properties
import dev.ng5m.util.StateManager
import net.kyori.adventure.key.Key

data class BlockState(
    val block: Block,
    val properties: Properties
) {
    companion object {
        val stateManager = StateManager<Block, BlockState>()

        fun parseState(s: String): BlockState {
            val bracketIndex = s.indexOf('[')
            val hasProperties = bracketIndex != -1
            val block = if (hasProperties) s.take(bracketIndex) else s
            val properties = mutableMapOf<String, Any>()

            if (hasProperties) {
                val propertiesString = s.substring(bracketIndex + 1, s.length - 1)
                val split = propertiesString.split(",")
                for (property in split) {
                    val equalsIndex = property.indexOf('=')
                    val key = property.take(equalsIndex)
                    val value = property.substring(equalsIndex + 1)

                    properties[key] = value // TODO convert to appropriate type based on definition
                }
            }

            return BlockState(
                Registries.BLOCK.getOrThrow(
                    Registries.BLOCK.resourceKeyByKey(Key.key(block))
                ),
                Properties.ofMap(properties)
            )
        }
    }

    constructor(block: Block) : this(block, Properties.ofMap())
//    constructor(block: Key) : this(block, Properties.ofMap())

    fun <T> withProperty(property: BlockStateProperty<T>, value: T): BlockState {
        property.setter[properties] = value

        return this
    }

    fun <T> getProperty(property: BlockStateProperty<T>): T {
        return property.getter[properties]
    }

    fun asBlock(): Block = stateManager.getParent(this)

    override fun toString(): String {
        return "${Registries.BLOCK.keyByValue(stateManager.getParent(this))}$properties"
    }
}
