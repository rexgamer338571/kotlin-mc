package dev.ng5m.api

import dev.ng5m.MinecraftServer
import dev.ng5m.block.BlockState
import dev.ng5m.event.Event
import dev.ng5m.event.EventManager
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.world.ChunkGenerationContext
import net.kyori.adventure.key.Key
import java.util.function.Consumer

object KMc {

    fun createServer(): Server {
        val server = MinecraftServer()
        return object : Server {
            override fun start(port: Int) {
                server.run(port)
            }

            override fun createWorld(dimensionType: String, id: String): World {
                val world = server.createWorld(
                    ResourceKey(Registries.DIMENSION_TYPE, Key.key(dimensionType)),
                    Key.key(id)
                )

                return object : World {
                    override fun setChunkGenerator(generator: ChunkGenerator) {
                        world.chunkGenerator = dev.ng5m.world.ChunkGenerator { ctx ->
                            generator.generate(object : ChunkAccess {
                                override fun setBlock(
                                    x: Int,
                                    y: Int,
                                    z: Int,
                                    state: String
                                ) {
                                    ctx.setBlockStateAt(x, y, z, BlockState.parseState(state))
                                }
                            })
                        }
                    }

                }
            }
        }
    }

    fun getRegistry(id: String): Registry {
        val reg = dev.ng5m.registry.Registry.getRegistry(Key.key(id))
        return object : Registry {
            override fun get(id: String): Any =
                reg.map.getA(Key.key(id))
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun on(event: String, consumer: Consumer<out Event>) {
        val className = "dev.ng5m.event.impl.$event"
        EventManager.register(Class.forName(className) as Class<out Event>) { ev ->
            (consumer as Consumer<Any>).accept(ev)
        }
    }

}