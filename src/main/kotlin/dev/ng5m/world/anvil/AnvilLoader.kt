package dev.ng5m.world.anvil

import dev.ng5m.MinecraftServer
import dev.ng5m.block.BlockState
import dev.ng5m.mcio.PacketCompression
import dev.ng5m.registry.Biome
import dev.ng5m.registry.DimensionType
import dev.ng5m.registry.DimensionTypes
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.Tag
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.serialization.nbt.impl.StringTag
import dev.ng5m.util.Properties
import dev.ng5m.util.bitsToRepresent
import dev.ng5m.util.decompressZL
import dev.ng5m.util.math.Vector2i
import dev.ng5m.world.*
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.longs.LongArrayList
import net.kyori.adventure.key.Key
import org.bouncycastle.util.Arrays
import org.bouncycastle.util.Integers
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream
import kotlin.math.floor
import kotlin.math.max

class AnvilLoader(val rootWorldDir: Path) {
    companion object {
        private val CT_CODEC: Codec<CompressionType> =
            Codec.ofEnum(
                CompressionType::class.java,
                Codec.of({ it.readByte().toInt() }, ByteBuf::writeByte)
            ) { it.id }

        fun extractNBT(mcaDir: Path, cx: Int, cz: Int): CompoundTag {
            NBT.init();

            val mcaPath = mcaDir.resolve("r.${cx shr 5}.${cz shr 5}.mca")

            val split = mcaPath.fileName.toString().split(".")

            val buf = Unpooled.wrappedBuffer(Files.readAllBytes(mcaPath))

            if (buf.readableBytes() == 0) return CompoundTag()

            buf.readerIndex((cx % 32) + (cz % 32) * 32)
            val entry = buf.readInt()
            val offsetBytes = ((entry shr 8) and 0xffffff) * 4096
            val length = (entry and 0xFF) * 4096

            buf.readerIndex(offsetBytes)

            val lengthBytes = buf.readInt()
            val compression = CT_CODEC.read(buf)!!

            if (compression == CompressionType.CUSTOM) {
                val l = buf.readUnsignedShort()
                val algo = String(buf.readBytes(l).array(), StandardCharsets.UTF_8)

                throw RuntimeException("compressed using custom algorithm: $algo")
            }

            val data = buf.readBytes(lengthBytes - 1)
            val decompressed = Unpooled.buffer()

            when (compression) {
                CompressionType.GZIP -> {
                    val gzIS = GZIPInputStream(ByteBufInputStream(data))

                    var len: Int
                    val buffer = ByteArray(1024)

                    while ((gzIS.read(buffer).also { len = it }) != -1) {
                        decompressed.writeBytes(Arrays.copyOf(buffer, len))
                    }
                }

                CompressionType.ZLIB -> {
                    decompressZL(data, decompressed)
                }

                CompressionType.NONE -> decompressed.writeBytes(data)
                CompressionType.LZ4 -> TODO()
                CompressionType.CUSTOM -> TODO()
            }

            return NBT.readTagT(decompressed, true)
        }
    }


    private fun loadSingle(mcaDir: Path, worldKey: Key, dimensionType: ResourceKey<DimensionType>) {
        val world = World(dimensionType, worldKey)

        val chunkMap = mutableMapOf<Long, Chunk>()

        Files.list(mcaDir).forEach { mcaPath ->
            val split = mcaPath.fileName.toString().split(".")

            val buf = Unpooled.wrappedBuffer(Files.readAllBytes(mcaPath))

            if (buf.readableBytes() == 0) return@forEach

            for (i in 0 until 1024) {
                buf.readerIndex(i * 4)
                val entry = buf.readInt()

                if (entry == 0) continue // not present

                buf.readerIndex(4096 + i * 4)
                val lastModified = buf.readInt() // unix timestamp

//                val offsetBytes = ((entry.toULong() and 0xFFFFFF00UL) shr 8) * 4096UL
                val offsetBytes = ((entry shr 8) and 0xffffff) * 4096
                val length = (entry and 0xFF) * 4096

                buf.readerIndex(offsetBytes)
                val lengthBytes = buf.readInt()
                val compression = CT_CODEC.read(buf)!!

                if (compression == CompressionType.CUSTOM) {
                    val l = buf.readUnsignedShort()
                    val algo = String(buf.readBytes(l).array(), StandardCharsets.UTF_8)

                    throw RuntimeException("compressed using custom algorithm: $algo")
                }

                val data = buf.readBytes(lengthBytes - 1)
                val decompressed = Unpooled.buffer()

                when (compression) {
                    CompressionType.GZIP -> {
                        val gzIS = GZIPInputStream(ByteBufInputStream(data))

                        var len: Int
                        val buffer = ByteArray(1024)

                        while ((gzIS.read(buffer).also { len = it }) != -1) {
                            decompressed.writeBytes(Arrays.copyOf(buffer, len))
                        }
                    }

                    CompressionType.ZLIB -> {
                        decompressZL(data, decompressed)
                    }

                    CompressionType.NONE -> decompressed.writeBytes(data)
                    CompressionType.LZ4 -> TODO()
                    CompressionType.CUSTOM -> TODO()
                }

                val compound: CompoundTag = NBT.readTagT(decompressed, true)


                val cx = compound.getInt("xPos")
                val cz = compound.getInt("yPos")

                if (compound.has("status") && compound.getString("status") != "minecraft:full") {
                    throw RuntimeException("found incomplete chunk ($cx, $cz)")
                }

                val sectionMap = mutableMapOf<Int, ChunkSection>()


                val aSections = compound.getList<CompoundTag>("sections")

                for (cSection in aSections) {
                    val section = ChunkSection()
                    val sectionY = cSection.getByte("Y").toInt()

                    section.blocks = convertPaletteContainer(cSection["block_states"], true)
                    section.biomes = convertPaletteContainer(cSection["biomes"], false)

                    sectionMap[sectionY] = section
                }

                chunkMap[World.packChunkCoordinates(cx, cz)] = Chunk(
                    cx, cz, dimensionType,
                    { y -> sectionMap[y] ?: ChunkSection() }, compound["heightmaps"]?: CompoundTag()
                )

                data.release()
            }

            buf.release()
        }

        world.chunkProvider =
            ChunkProvider { world, x, z -> chunkMap[World.packChunkCoordinates(x, z)] ?: Chunk(x, z, world.typeKey) }

        MinecraftServer.getInstance().addWorld(worldKey, world)
    }

    private fun blockStateFromTag(tag: Tag<*>): BlockState {
        if (tag is CompoundTag) {
            val name = tag.getString("Name")
            val properties = if (tag.has("Properties")) {
                val nm = mutableMapOf<String, Any>()
                val m = tag.getCompound("Properties")
                for ((k, v) in m) nm[k] = v.value

                Properties.ofMap(nm)
            }
            else Properties.ofMap()

            return BlockState(Registries.BLOCK.getOrThrow(Registries.BLOCK.resourceKeyByKey(Key.key(name))), properties)
        } else if (tag is StringTag) {
            return BlockState(Registries.BLOCK.getOrThrow(Registries.BLOCK.resourceKeyByKey(Key.key(tag.value))))
        }

        throw IllegalArgumentException()
    }

    private fun convertPaletteContainer(compound: CompoundTag, blocks: Boolean): PalettedContainer {
        val registry = if (blocks) Registries.BLOCK else Registries.BIOME
        val cPalette = compound.getList<Tag<*>>("palette")
        val size = if (blocks) 4096 else 256
        val minBits = if (blocks) 4 else 1
        val maxBits = if (blocks) 8 else 3
        val directBits = if (blocks) 15 else 6

        val palette = if (cPalette.size == 1) {
            if (blocks) {
                val bst = blockStateFromTag(cPalette[0])
                Palette(
                    size, mutableListOf(BlockState.stateManager.idBy(bst)),
                    minBits, maxBits, directBits
                )
            } else {
                val biome = cPalette[0] as StringTag
                Palette(
                    size, mutableListOf(Registries.BIOME.idByRawKey(Key.key(biome.value))),
                    minBits, maxBits, directBits
                )
            }
        } else {
            val palette = (if (blocks) {
                cPalette.map {
                    val bst = blockStateFromTag(it)
                    BlockState.stateManager.idBy(bst)
                }
            } else {
                cPalette.map {
                    Registries.BIOME.idByRawKey(Key.key((it as StringTag).value))
                }
            }).toMutableList()
            val data = compound.getLongArray("data")

            Palette(
                size, palette, data, minBits, maxBits, directBits
            )
        }

        return PalettedContainer(size, minBits, maxBits, directBits).also {
            it.palette = palette
        }
    }

    fun load() {
        val region = rootWorldDir.resolve("region")
        val nether = rootWorldDir.resolve("DIM-1").resolve("region")
        val end = rootWorldDir.resolve("DIM1").resolve("region")

        if (!region.toFile().exists()) throw RuntimeException("$region doesn't exist")

        loadSingle(region, Key.key("overworld"), DimensionTypes.OVERWORLD)
        if (nether.toFile().exists()) loadSingle(nether, Key.key("the_nether"), DimensionTypes.THE_NETHER)
        if (end.toFile().exists()) loadSingle(end, Key.key("the_end"), DimensionTypes.THE_END)
    }

    enum class CompressionType(val id: Int) {
        GZIP(1),
        ZLIB(2),
        NONE(3),
        LZ4(4),
        CUSTOM(127)
    }


}