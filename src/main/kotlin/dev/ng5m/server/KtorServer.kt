package dev.ng5m.server

import dev.ng5m.MinecraftConnection
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KtorServer : TCPServer<Any>(TODO()) {
    override fun start(port: Int) {
        runBlocking {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket = aSocket(selectorManager).tcp().bind("127.0.0.1", port)

            while (true) {
                val socket = serverSocket.accept()
                launch {
                    val receiveChannel = socket.openReadChannel()
                    val sendChannel = socket.openWriteChannel(autoFlush = true)

                    while (true) {
                        // TODO abandon netty, rewrite serialization in kt
                    }
                }
            }
        }
    }

    override fun stop() {
        TODO()
    }

}