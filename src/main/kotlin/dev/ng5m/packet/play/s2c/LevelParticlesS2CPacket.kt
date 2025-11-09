package dev.ng5m.packet.play.s2c

import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.world.particle.ParticleOptions
import dev.ng5m.world.particle.ParticleType

data class LevelParticlesS2CPacket(
    val longDistance: Boolean,
    val alwaysVisible: Boolean,
    val x: Double,
    val y: Double,
    val z: Double,
    val offsetX: Float,
    val offsetY: Float,
    val offsetZ: Float,
    val maxSpeed: Float,
    val particleCount: Int,
    val particleType: ResourceKey<ParticleType<*>>,
    val data: ParticleOptions
) : Packet {
    companion object {
        val CODEC: Codec<LevelParticlesS2CPacket> = Codec.of(
            { buf ->
                val longDistance = buf.readBoolean()
                val alwaysVisible = buf.readBoolean()
                val x = buf.readDouble()
                val y = buf.readDouble()
                val z = buf.readDouble()
                val offsetX = buf.readFloat()
                val offsetY = buf.readFloat()
                val offsetZ = buf.readFloat()
                val maxSpeed = buf.readFloat()
                val particleCount = buf.readInt()
                val type = Registries.PARTICLE_TYPE.idCodec.read(buf)
                val data = type.value().optionsCodec.read(buf)

                LevelParticlesS2CPacket(
                    longDistance, alwaysVisible, x, y, z,
                    offsetX, offsetY, offsetZ,
                    maxSpeed, particleCount, type, data
                )
            },
            { buf, it ->
                buf.writeBoolean(it.longDistance)
                buf.writeBoolean(it.alwaysVisible)
                buf.writeDouble(it.x)
                buf.writeDouble(it.y)
                buf.writeDouble(it.z)
                buf.writeFloat(it.offsetX)
                buf.writeFloat(it.offsetY)
                buf.writeFloat(it.offsetZ)
                buf.writeFloat(it.maxSpeed)
                buf.writeInt(it.particleCount)
                Registries.PARTICLE_TYPE.idCodec.write(buf, it.particleType)
                @Suppress("UNCHECKED_CAST")
                (it.particleType.value().optionsCodec as Codec<ParticleOptions>).write(buf, it.data)
            }
        ).forType(LevelParticlesS2CPacket::class.java)
    }
}
