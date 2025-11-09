package dev.ng5m.api

interface Server {

    fun start(port: Int)

    fun createWorld(dimensionType: String, id: String): World

}