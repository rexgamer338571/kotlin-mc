package dev.ng5m.world.anvil

import dev.ng5m.mcio.PacketCompression
import dev.ng5m.registry.DimensionType
import dev.ng5m.registry.DimensionTypes
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.util.decompressZL
import dev.ng5m.util.math.Vector2i
import dev.ng5m.world.*
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.Unpooled
import net.kyori.adventure.key.Key
import org.bouncycastle.util.Arrays
import org.bouncycastle.util.Integers
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream

class AnvilLoader(val rootWorldDir: Path) {
    companion object {
        private val CT_CODEC: Codec<CompressionType> =
            Codec.ofEnum(
                CompressionType::class.java,
                Codec.of({ it.readByte().toInt() }, ByteBuf::writeByte)
            ) { it.id }
    }

    private fun loadSingle(mcaDir: Path, worldKey: Key, dimensionType: ResourceKey<DimensionType>) {
        val world = World(dimensionType, worldKey)

        val chunkMap = mutableMapOf<Vector2i, Chunk>()

        Files.list(mcaDir).forEach { mcaPath ->
            val split = mcaPath.fileName.toString().split(".")
            val mcaXZ = Vector2i(
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
            )

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

                    convertPaletteContainer(section.blocks, cSection["block_states"], true)
                    convertPaletteContainer(section.biomes, cSection["biomes"], false)

                    sectionMap[sectionY] = section
                }

                chunkMap[Vector2i(cx, cz)] = Chunk(cx, cz, dimensionType, object : ChunkSectionLoader {
                    override fun get(y: Int): ChunkSection = sectionMap[y] ?: ChunkSection()
                }, compound["heightmaps"])
            }
        }

        world.chunkProvider = object : ChunkProvider {
            override fun get(world: World, x: Int, z: Int): Chunk = chunkMap[Vector2i(x, z)] ?: Chunk(x, z, world.typeKey)
        }
    }

    private fun convertPaletteContainer(output: IntArray, compound: CompoundTag, blocks: Boolean) {
        val registry = if (blocks) Registries.BLOCK else Registries.BIOME
        val cPalette = compound.getList<CompoundTag>("palette")

        if (cPalette.size == 1) {
            output.fill(registry.idByRawKey(Key.key(cPalette[0].getString("Name"))))
        } else {
            val palette = cPalette.map { registry.idByRawKey(Key.key(it.getString("Name"))) }
            val data = compound.getLongArray("data")

            println(data.size)
            println(data.contentToString())

            var bpe = -1
            palette.forEach {
                val cur = Integer.SIZE - Integer.numberOfLeadingZeros(it)
                if (cur > bpe) bpe = cur
            }

            ChunkSection.unpackDataArray(blocks, data, bpe)
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