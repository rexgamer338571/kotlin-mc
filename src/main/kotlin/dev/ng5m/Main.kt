package dev.ng5m

import de.articdive.jnoise.core.api.functions.Interpolation
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction
import de.articdive.jnoise.pipeline.JNoise
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.block.Blocks.STONE
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerJoinEvent
import dev.ng5m.event.impl.player.PlayerPreJoinEvent
import dev.ng5m.item.ItemStack
import dev.ng5m.item.Items
import dev.ng5m.item.component.ItemComponentTypes
import dev.ng5m.player.GameMode
import dev.ng5m.registry.Biomes
import dev.ng5m.registry.DimensionTypes
import dev.ng5m.registry.Registries
import dev.ng5m.serialization_kt.Codec
import dev.ng5m.serialization_kt.Either
import dev.ng5m.serialization_kt.nbt.NBT
import dev.ng5m.serialization_kt.nbt.impl.CompoundTag
import dev.ng5m.serialization_kt.nbt.impl.StringTag
import dev.ng5m.util.TypeArguments
import dev.ng5m.world.*
import net.kyori.adventure.key.Key
import kotlin.random.Random


class A {
    @TypeArguments(value = [String::class, List::class])
    var either: Either<String, List<String>>? = null
}

fun main() {
//    NBT.init()
//    val ct = CompoundTag()
//    ct.add(StringTag(name = "either", value = "hello"))
//
//    println(NBT.fromNBT(ct, A::class)!!.either)
//
//    if (true) return

    val stack = ItemStack(Items.SUGAR)
        .withComponent(ItemComponentTypes.MAX_STACK_SIZE, 99)
        .withCount(Int.MAX_VALUE)

//    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")

    val server = MinecraftServer()

//    AnvilLoader(Path.of("/home/ng5m/.local/share/multimc/instances/1.21.4 fabric/.minecraft/saves/New World"))
//        .load()

//    Thread.sleep(25000)


//    println("times up")

//    Chunk.test()

    val world = server.createWorld(DimensionTypes.THE_NETHER, Key.key("test", "test"))

    world.chunkGenerator = object : ChunkGenerator {
        val random = Random(1)

        val noise: JNoise = JNoise.newBuilder()
            .perlin(1L, Interpolation.COSINE, FadeFunction.QUINTIC_POLY)
            .scale(0.03)
            .build()

        override fun generate(context: ChunkGenerationContext) {
            val wx = context.chunkX() * 16
            val wz = context.chunkZ() * 16

            for (bx in 0 until 16) {
                for (bz in 0 until 16) {
//                    val y = 10.0 * sin(0.1 * (wx + bx)) * sin(0.1 * (wz + bz)) + 10.0
                    val y = 30 + noise.evaluateNoise((wx.toDouble() + bx), (wz.toDouble() + bz)) * 20
                    context.fillHeight(bx, bz, 0..y.toInt(),
//                        Registries.BLOCK.randomElement(random)
                        BlockState(STONE)
                    )
                }
            }

            for (cx in 0 until 16) {
                for (cy in 0 until 256) {
                    for (cz in 0 until 16) {
                        context.setBiomeAt(cx, cy, cz, Biomes.SOUL_SAND_VALLEY)
                    }
                }
            }

//            context.a()
        }
    }

    val random = Random(1)
    EventManager.register(PlayerPreJoinEvent::class.java) {
        it.player.setWorld(world)
        it.player.location = Location(world, 0.0, 42.0, 0.0)
        it.player.gameMode = GameMode.CREATIVE

        for (i in 0 until 46) {
            it.player.inventory.setItem(
                i, ItemStack(
                    Registries.ITEM.randomElement(random)
                ).withCount(Int.MAX_VALUE)
            )
        }
    }

    server.run(25565)
}

