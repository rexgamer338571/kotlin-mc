package dev.ng5m.world

import dev.ng5m.MinecraftServer
import dev.ng5m.block.BlockState
import dev.ng5m.entity.Entity
import dev.ng5m.packet.play.s2c.RemoveEntitiesS2CPacket
import dev.ng5m.packet.play.s2c.SpawnEntityS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.Biome
import dev.ng5m.registry.DimensionType
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.math.Vector2i
import dev.ng5m.world.GameRules.DO_IMMEDIATE_RESPAWN
import dev.ng5m.world.GameRules.DO_LIMITED_CRAFTING
import dev.ng5m.world.GameRules.REDUCED_DEBUG_INFO
import net.kyori.adventure.key.Key
import java.nio.ByteBuffer
import java.security.MessageDigest

class World(val typeKey: ResourceKey<DimensionType>, val id: Key) {
    private val type: DimensionType = Registries.DIMENSION_TYPE.getOrThrow(typeKey)
    private val gameRules = mutableMapOf(
        REDUCED_DEBUG_INFO to false,
        DO_IMMEDIATE_RESPAWN to false,
        DO_LIMITED_CRAFTING to false,
    )
    private val entities: MutableSet<Entity> = mutableSetOf()

    private val chunks: MutableMap<Vector2i, Chunk> = mutableMapOf()
    var chunkProvider: ChunkProvider = ChunkProvider.EMPTY
    var chunkGenerator: ChunkGenerator = ChunkGenerator.EMPTY

    var hardcore = false
    var debug = false
    var flat = false

    var seed: Long = 1
    var seaLevel: Int = 64


    fun setGameRule(name: String, value: Boolean) {
        gameRules[name] = value
    }

    fun getGameRule(name: String): Boolean? {
        return gameRules[name]
    }

    fun addEntity(entity: Entity) {
        MinecraftServer.getInstance().removeTicking(entity)
        if (entity.getWorld() != null) entity.getWorld()!!.removeEntityRaw(entity)

        entity.setWorld(this)

        entities.filter { it != entity && it is Player }.map { it as Player }.forEach {
            it.connection.sendPacket(SpawnEntityS2CPacket(entity))
        }

        entities.add(entity)
        MinecraftServer.getInstance().addTicking(entity)
    }

    fun generateChunkIfAbsent(x: Int, z: Int) {
        val vec = Vector2i(x, z)
        val chunk = chunks.computeIfAbsent(vec) { _ -> chunkProvider.get(this, x, z) }
        val ctx = object : ChunkGenerationContext {
            override fun chunkX(): Int = x
            override fun chunkZ(): Int = z

            override fun fillHeight(x: Int, z: Int, yRange: IntRange, state: BlockState) {
                for (y in yRange) chunk.setBlockStateAt(x, y, z, state)
            }

            override fun setBiomeAt(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
                chunk.setBiomeAt(x, y, z, biome)
            }

            override fun setBiomeAtCell(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
                chunk.setBiomeAtCell(x, y, z, biome)
            }

            override fun setBlockStateAt(x: Int, y: Int, z: Int, state: BlockState) {
                chunk.setBlockStateAt(x, y, z, state)
            }

            override fun setBlockAt(
                x: Int,
                y: Int,
                z: Int,
                block: Key
            ) {
                setBlockStateAt(x, y, z, BlockState(block))
            }
        }

        chunkGenerator.generate(ctx)
    }

    fun generateIfAbsent(x: Int, z: Int): Chunk {
        generateChunkIfAbsent(x, z)
        return chunks[Vector2i(x, z)]!!
    }

    fun getHashedSeed(): Long {
        val buf = ByteBuffer.allocate(8)
        buf.putLong(seed)
        val bytes = buf.array()
        val md = MessageDigest.getInstance("SHA-256")

        buf.flip()
        buf.put(md.digest(bytes).sliceArray(IntRange(0, 7)))
        buf.flip()

        return buf.getLong()
    }

    internal fun removeEntityRaw(entity: Entity) {
        entities.remove(entity)
    }

    fun removeEntity(entity: Entity) {
        removeEntityRaw(entity)

        MinecraftServer.getInstance().getPlayingConnections().forEach {
            it.sendPacket(RemoveEntitiesS2CPacket(entity))
        }
    }

    fun entities(): Set<Entity> = entities.toSet()

}