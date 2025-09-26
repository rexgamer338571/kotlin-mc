package dev.ng5m.packet.configuration.s2c

import dev.ng5m.registry.Registry
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet

data class RegistryDataS2CPacket<T : Any>(
    val registry: Registry<T>
) : Packet {
    companion object {
        val CODEC: Codec<RegistryDataS2CPacket<*>> =
            Registry.CODEC.xmap({ RegistryDataS2CPacket(it) }, RegistryDataS2CPacket<*>::registry)
                .forType(RegistryDataS2CPacket::class.java)
    }

}