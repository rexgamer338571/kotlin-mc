package dev.ng5m.util

import dev.ng5m.serialization_kt.DoubleMap
import java.util.function.Supplier
import kotlin.random.Random

class StateManager<P, S> {
    internal val ids = DoubleMap<Int, S>()
    private val ownershipMap = mutableMapOf<S, P>()
    private val defaultStates = mutableMapOf<P, S>()

    fun register(parent: P, defaultState: S, states: List<S>, idSupplier: (S) -> Int) {
        defaultStates[parent] = defaultState
        for (state in states) {
            ids.put(idSupplier(state), state)
            ownershipMap[state] = parent
        }
    }

    fun getParent(state: S): P = ownershipMap[state]!!

    fun getDefaultState(parent: P): S = defaultStates[parent]!!

    fun byId(id: Int): S = ids.getA(id)!!
    fun idBy(state: S): Int = ids.getB(state)!!

    fun randomState(random: Random): S = ids.getA(random.nextInt(ids.size()))!!
}