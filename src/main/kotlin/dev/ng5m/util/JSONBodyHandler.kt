package dev.ng5m.util

import dev.ng5m.MinecraftServer
import java.io.InputStreamReader
import java.net.http.HttpResponse
import java.util.function.Supplier

class JSONBodyHandler<T>(val clazz: Class<T>) : HttpResponse.BodyHandler<Supplier<T>> {
    override fun apply(responseInfo: HttpResponse.ResponseInfo): HttpResponse.BodySubscriber<Supplier<T>> {
        val upstream = HttpResponse.BodySubscribers.ofInputStream()

        return HttpResponse.BodySubscribers.mapping(upstream) { stream ->
            Supplier<T> { MinecraftServer.GSON.fromJson(InputStreamReader(stream), clazz) }
        }
    }
}