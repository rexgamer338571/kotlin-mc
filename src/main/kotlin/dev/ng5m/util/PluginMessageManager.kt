package dev.ng5m.util

import dev.ng5m.MinecraftConnection
import dev.ng5m.serialization.Codec
import net.kyori.adventure.key.Key
import java.util.function.BiConsumer
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.starProjectedType

class PluginMessageManager {
    companion object {
        private val channelToHandlerSetMap:
                MutableMap<Key, MutableSet<BiConsumer<MinecraftConnection, ByteArray>>> = mutableMapOf()
        private val specialCodecs: MutableMap<Key, Codec<ByteArray>> = mutableMapOf()

        fun register(channel: Key, consumer: BiConsumer<MinecraftConnection, ByteArray>) {
            channelToHandlerSetMap.computeIfAbsent(channel) { _ -> mutableSetOf() }.add(consumer)
        }

        fun codecFor(channel: Key): Codec<ByteArray>? {
            return specialCodecs[channel]
        }

        fun fire(connection: MinecraftConnection, channel: Key, data: ByteArray) {
            channelToHandlerSetMap.computeIfAbsent(channel) { _ -> mutableSetOf() }
                .forEach { it.accept(connection, data) }
        }

        fun <T> register(instance: T) {
            for (method in instance!!::class.declaredMemberFunctions) {
                if (method.visibility != KVisibility.PUBLIC) continue
                if (!method.hasAnnotation<Subscribe>()) continue
                val params = method.parameters

                if (params.size != 3) continue
                if (params[1].type != MinecraftConnection::class.starProjectedType) continue
                if (params[2].type != ByteArray::class.starProjectedType) continue

                register(Key.key(method.findAnnotation<Subscribe>()!!.value)) { connection, data ->
                    try {
                        method.call(instance, connection, data)
                    } catch (x: Exception) {
                        throw RuntimeException(x)
                    }
                }
            }
        }


    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION)
    annotation class Subscribe(val value: String)

}