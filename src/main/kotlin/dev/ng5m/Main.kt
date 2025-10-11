package dev.ng5m

import de.articdive.jnoise.core.api.functions.Interpolation
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction
import de.articdive.jnoise.pipeline.JNoise
import dev.ng5m.block.Blocks
import dev.ng5m.entity.BlockEntity
import dev.ng5m.entity.BlockEntityType
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.S2CPacketEvent
import dev.ng5m.event.impl.player.PlayerJoinEvent
import dev.ng5m.event.impl.player.PlayerPreJoinEvent
import dev.ng5m.item.ItemStack
import dev.ng5m.item.Items
import dev.ng5m.player.GameMode
import dev.ng5m.registry.Biome
import dev.ng5m.registry.DimensionTypes
import dev.ng5m.registry.Registries
import dev.ng5m.serialization_kt.Either
import dev.ng5m.util.TypeArguments
import dev.ng5m.world.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import kotlin.random.Random


class A {
    @TypeArguments(value = [String::class, List::class])
    var either: Either<String, List<String>>? = null
}

fun main() {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")


//    val chunkNBT = AnvilLoader.extractNBT(Path.of("/home/ng5m/.local/share/multimc/instances/1.21.4 fabric/.minecraft/saves/New World/region"), 0, 0)
//    val blockEntities = chunkNBT.getList<dev.ng5m.serialization.nbt.impl.CompoundTag>("block_entities")
//
//    println(blockEntities)

//    limboServer()

//    if (true) return


    val server = MinecraftServer()

    server.pluginManager.registerPlugin("test", object : Plugin {
        override fun onEnable() {

        }

        override fun onDisable() {
        }
    })

//    AnvilLoader(Path.of("/home/ng5m/.local/share/multimc/instances/1.21.4 fabric/.minecraft/saves/New World"))
//        .load()

//    if (true) return

//    Thread.sleep(25000)


//    println("times up")

//    Chunk.test()

    val world = server.createWorld(DimensionTypes.OVERWORLD, Key.key("test", "test"))

    val tabList = TabList()

    val customBiome = Registries.BIOME.register(Key.key("test", "test_biome"), Biome().also { biome ->
        biome.effects = Biome.Effects().also { effects ->
            effects.music = listOf(Biome.Music().also { music ->
                music.data = Biome.Music.Data().also { data ->
                    data.sound = "music.overworld.deep_dark"
                    data.maxDelay = 12000
                    data.minDelay = 6000
                    data.replaceCurrentMusic = true
                }
            })
        }
    })


    val random = Random(1)
    val noiseRandomChunkGenerator = object : ChunkGenerator {
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
                    context.fillHeight(
                        bx, bz, 0..y.toInt(),
                        Registries.BLOCK.randomElement(random).defaultBlockState()
//                        BlockState(STONE)
                    )
                }
            }

            for (cx in 0 until 16) {
                for (cy in 0 until 256) {
                    for (cz in 0 until 16) {
                        context.setBiomeAt(cx, cy, cz, customBiome)
                    }
                }
            }

            context.chunk().addBlockEntity(
                BlockEntity(0, 50, 0, BlockEntityType.CHEST, dev.ng5m.serialization.nbt.impl.CompoundTag())
            )
        }
    }

    val noise: JNoise = JNoise.newBuilder()
        .perlin(1L, Interpolation.COSINE, FadeFunction.QUINTIC_POLY)
        .scale(0.03)
        .build()

    val coolGenerator = ChunkGenerator { context ->
        val wx = context.chunkX() * 16
        val wz = context.chunkZ() * 16

        for (bx in 0 until 16) {
            for (bz in 0 until 16) {
                val y = (30 + noise.evaluateNoise((wx.toDouble() + bx), (wz.toDouble() + bz)) * 20).toInt()
                context.fillHeight(
                    bx, bz, 0..y, Blocks.STONE.defaultBlockState()
                )

                context.fillHeight(bx, bz, y..y + 2, Blocks.DIRT.defaultBlockState())
//                    context.setBlockStateAt(bx, bz, y + 3..y+4, Blocks.GRASS_BLOCK.defaultBlockState())
                context.setBlockAt(bx, y + 10, bz, Blocks.GOLD_BLOCK)

            }
        }

//            context.fillBiome(customBiome)
        context.setBlockAt(0, 50, 0, Blocks.CHEST)
    }

    val randomChunkGenerator = ChunkGenerator { context ->
        val wx = context.chunkX() * 16
        val wz = context.chunkZ() * 16

        for (bx in 0 until 16) {
            for (by in 0 until 16) {
                for (bz in 0 until 16) {
                    context.setBlockStateAt(bx, by, bz, Blocks.BEDROCK.defaultBlockState())
                }
            }
        }

        for (cx in 0 until 16) {
            for (cy in 0 until 256) {
                for (cz in 0 until 16) {
                    context.setBiomeAt(cx, cy, cz, customBiome)
                }
            }
        }

        context.setBlockAt(0, 40, 0, Blocks.CHEST)
    }

    val flatGenerator = ChunkGenerator { context ->
        for (bx in 0 until 16) {
            for (bz in 0 until 16) {
                context.setBlockAt(bx, 0, bz, Blocks.BEDROCK)
                context.fillHeight(bx, bz, 1..4, Blocks.DIRT)
                context.setBlockAt(bx, 5, bz, Blocks.GRASS_BLOCK)
            }
        }

        context.setBlockAt(0, 6, 0, Blocks.CHEST)
    }

    world.chunkGenerator = flatGenerator

//    val world = MinecraftServer.getInstance().getWorlds().first()

    EventManager.register(PlayerPreJoinEvent::class.java) {
        it.player.setWorld(world)
        it.player.location = Location(world, 0.0, 7.0, 0.0)
        it.player.gameMode = GameMode.CREATIVE

//        for (i in 0 until 46) {
//            it.player.inventory.setItem(
//                i, ItemStack(
//                    Registries.ITEM.randomElement(random)
//                ).withCount(10)
//            )
//        }

        it.player.inventory.hotbar(0, ItemStack(Items.STONE).withCount(64))
        it.player.inventory.hotbar(1, ItemStack(Items.WATER_BUCKET))
//        it.player.inventory.chest(ItemStack.AIR)
    }

    EventManager.register(PlayerJoinEvent::class.java) {
        tabList.setPlayerIndex(it.player.getIdentity().getAdequateUUID(), 0)
    }

    EventManager.register(S2CPacketEvent::class.java) {
        if (it.connection.protocolState != ProtocolState.PLAY) return@register
        if (!it.connection.protocolState.shouldLog(it.packet::class.java)) return@register

        tabList.footer = Component.text("S -> C ").color(NamedTextColor.DARK_AQUA)
            .append(
                Component.text("${it.packet::class.simpleName}").color(NamedTextColor.AQUA)
                    .append(
                        Component.newline()
                            .append(
                                Component.text(ServerPerformanceMonitor.toString()).color(NamedTextColor.GRAY)
                            )
                    )
            )

        tabList.update(it.connection)
    }

    server.run(25565)
}

fun limboServer() {
    val server = MinecraftServer()
    server.serverViewDistance = 2

    val world = server.createWorld(DimensionTypes.THE_END, Key.key("limbo"))

    EventManager.register(PlayerPreJoinEvent::class.java) {
        it.player.setWorld(world)
        it.player.location = Location(world, Double.MAX_VALUE, Double.MAX_VALUE, 0.0)
        it.player.gameMode = GameMode.CREATIVE
    }

    server.run(25565)
}

