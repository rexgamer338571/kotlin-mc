package dev.ng5m.pack

import com.sun.net.httpserver.HttpServer
import dev.ng5m.util.readFileOrResource
import java.net.InetSocketAddress
import java.nio.file.Path
import kotlin.concurrent.thread

class ResourcePackServer {

    fun start(port: Int) {
        thread {
            val server = HttpServer.create(InetSocketAddress(port), 0)
            server.createContext("/") { exchange ->
                val path = Path.of(exchange.requestURI.path)
                val hash = path.fileName.toString()

                val pack = ResourcePackManager.getPack(hash)
                if (pack == null || pack !is ResourcePack.Local) {
                    exchange.close()
                } else {
                    val res = readFileOrResource(pack.localPath)
                    exchange.sendResponseHeaders(200, res.size.toLong())
                    val os = exchange.responseBody
                    os.write(res)
                    os.close()
                }
            }

            server.start()
        }
    }

}