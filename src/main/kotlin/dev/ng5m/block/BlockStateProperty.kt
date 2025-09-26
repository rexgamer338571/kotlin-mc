package dev.ng5m.block

import dev.ng5m.util.Getter
import dev.ng5m.util.Properties
import dev.ng5m.util.Setter

data class BlockStateProperty<T>(
    val key: String,
    val getter: Getter<Properties, T>,
    val setter: Setter<Properties, T>
)