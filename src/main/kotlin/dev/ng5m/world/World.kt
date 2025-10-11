package dev.ng5m.world

import dev.ng5m.MinecraftServer
import dev.ng5m.block.Block
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.entity.BlockEntity
import dev.ng5m.entity.Entity
import dev.ng5m.entity.ItemEntity
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.play.s2c.RemoveEntitiesS2CPacket
import dev.ng5m.packet.play.s2c.UnloadChunkS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.Biome
import dev.ng5m.registry.DimensionType
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.math.Vector3d
import dev.ng5m.util.math.Vector3i
import dev.ng5m.world.GameRules.DO_IMMEDIATE_RESPAWN
import dev.ng5m.world.GameRules.DO_LIMITED_CRAFTING
import dev.ng5m.world.GameRules.REDUCED_DEBUG_INFO
import net.kyori.adventure.key.Key
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.function.Consumer
import kotlin.math.floor

class World(val typeKey: ResourceKey<DimensionType>, val id: Key) {
    companion object {
        fun packChunkCoordinates(x: Int, z: Int, precision: Int = 32): Long =
            (x.toLong() shl precision) or (z.toLong() and ((1L shl precision) - 1L))

        fun unpackChunkCoordinates(packed: Long, precision: Int = 32): Pair<Int, Int> =
            (packed shr precision).toInt() to (packed and ((1L shl precision) - 1L)).toInt()
    }

    private val type: DimensionType = Registries.DIMENSION_TYPE.getOrThrow(typeKey)
    private val gameRules = mutableMapOf(
        REDUCED_DEBUG_INFO to false,
        DO_IMMEDIATE_RESPAWN to false,
        DO_LIMITED_CRAFTING to false,
    )
    private val entities: MutableSet<Entity> = mutableSetOf()

    private val chunks: MutableMap<Long, Chunk?> = mutableMapOf()
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
        if (entity.isSpawned())
            entity.getWorld().removeEntityRaw(entity)

        entity.setWorld(this)

        entities.filter { it != entity && it is Player }
            .map { it as Player }
            .forEach {
                entity.spawnForPlayer(it)
            }

        entities.add(entity)
        MinecraftServer.getInstance().addTicking(entity)
    }

    fun spawnEntity(location: Location, entity: Entity) {
        entity.location = location.clone()
        addEntity(entity)
    }

    fun generateChunkIfAbsent(x: Int, z: Int) {
        val chunk = chunks.computeIfAbsent(packChunkCoordinates(x, z)) { _ -> chunkProvider.get(this, x, z) }!!
        val ctx = object : ChunkGenerationContext {
            override fun chunkX(): Int = x
            override fun chunkZ(): Int = z

            override fun fillHeight(x: Int, z: Int, yRange: IntRange, state: BlockState) {
                for (y in yRange) chunk.setBlockStateAt(x, y, z, state)
            }

            override fun fillHeight(
                x: Int,
                z: Int,
                yRange: IntRange,
                block: Block
            ) {
                fillHeight(x, z, yRange, block.defaultBlockState())
            }

            override fun setBiomeAt(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
                chunk.setBiomeAt(x, y, z, biome)
            }

            override fun setBiomeAtCell(x: Int, y: Int, z: Int, biome: ResourceKey<Biome>) {
                chunk.setBiomeAtCell(x, y, z, biome)
            }

            override fun fillBiome(biome: ResourceKey<Biome>) {
                val id = Registries.BIOME.idByKey(biome)
                for ((_, section) in chunk.sections)
                    section.biomes = PalettedContainer(256, 1, 3, 6, id)
            }

            override fun setBlockStateAt(x: Int, y: Int, z: Int, state: BlockState) {
                chunk.setBlockStateAt(x, y, z, state)
            }

            override fun setBlockAt(
                x: Int,
                y: Int,
                z: Int,
                block: Block
            ) {
                setBlockStateAt(x, y, z, block.defaultBlockState())
            }

            override fun chunk(): Chunk = chunk
        }

        chunkGenerator.generate(ctx)
    }

    fun generateIfAbsent(x: Int, z: Int): Chunk {
        generateChunkIfAbsent(x, z)
        return chunks[packChunkCoordinates(x, z)]!!
    }

    fun generateInRadius(rootX: Int, rootZ: Int, radius: Int, callback: Consumer<Chunk> = Consumer<Chunk> {}) {
        for (x in rootX - radius..rootX + radius) {
            for (z in rootZ - radius..rootZ + radius) {
                callback.accept(generateIfAbsent(x, z))
            }
        }
    }

    fun unloadChunk(x: Int, z: Int) {
        entities
            .filter { it is Player }
            .map { it as Player }
            .forEach { it.connection.sendPacket(UnloadChunkS2CPacket(x, z)) }

        chunks[packChunkCoordinates(x, z, 16)] = null
    }



    fun getBlockAt(x: Int, y: Int, z: Int): Block {
        val cx = floor(x / 16.0).toInt()
        val cz = floor(z / 16.0).toInt()
        val chunk = chunks[packChunkCoordinates(cx, cz)] ?: return Blocks.AIR
        val state = chunk.getBlockStateAt(x % 16, y, z % 16)

        return state.block
    }

    fun setBlockStateAt(x: Int, y: Int, z: Int, state: BlockState) {
        val cx = floor(x / 16.0).toInt()
        val cz = floor(z / 16.0).toInt()

        chunks[packChunkCoordinates(cx, cz)]?.setBlockStateAt(x % 16, y, z % 16, state)
    }

    fun setBlockStateAt(pos: Vector3i, state: BlockState) = setBlockStateAt(pos.x, pos.y, pos.x, state)

    fun getBlockAt(pos: Vector3i): Block? = getBlockAt(pos.x, pos.y, pos.z)

    fun getBlockEntityAt(x: Int, y: Int, z: Int): BlockEntity? {
        val cx = floor(x / 16.0).toInt()
        val cz = floor(z / 16.0).toInt()

        return (chunks[packChunkCoordinates(cx, cz)]?.getBlockEntity(x % 16, y, z % 16))
    }

    fun dropItem(pos: Vector3d, stack: ItemStack) {
        val item = ItemEntity(stack)
        item.location = Location(this, pos)

        addEntity(item)
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