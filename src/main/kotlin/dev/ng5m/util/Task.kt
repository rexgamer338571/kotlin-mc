package dev.ng5m.util

data class Task(
    val runnable: () -> Unit
) {
    private val callbacks = mutableSetOf<() -> Unit>()

    fun callback(callback: () -> Unit): Task {
        callbacks.add(callback)

        return this
    }

    fun run(): Task {
        runnable()
        callbacks.forEach { it() }

        return this
    }
}