package dev.ng5m

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import dev.ng5m.MinecraftServer.StatusResponseTemplate.PlayerTemplate
import dev.ng5m.block.BlockState
import dev.ng5m.block.Blocks
import dev.ng5m.entity.Entity
import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.player.PlayerMoveEvent
import dev.ng5m.item.Items
import dev.ng5m.item.component.ItemComponentTypes
import dev.ng5m.mcio.MCDecoder
import dev.ng5m.mcio.MCEncoder
import dev.ng5m.mcio.MCHandler
import dev.ng5m.packet.common.PluginMessagePacket
import dev.ng5m.player.Player
import dev.ng5m.registry.*
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.NBTCodec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.util.*
import dev.ng5m.util.json.EitherTypeAdapterFactory
import dev.ng5m.world.Difficulty
import dev.ng5m.world.World
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readByte
import io.ktor.utils.io.readPacket
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.nio.NioIoHandler
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread
import kotlin.reflect.full.declaredMemberProperties

class MinecraftServer {

    companion object {
        internal val LOCK = Object()

        private val LOGGER: Logger = LoggerFactory.getLogger(MinecraftServer::class.java)

        const val PROTOCOL: Int = 769
        const val MINECRAFT_VERSION: String = "1.21.4"

        val GSON_BUILDER: GsonBuilder = GsonBuilder()
            .registerTypeAdapter(Component::class.java, object : TypeAdapter<Component>() {
                override fun write(out: JsonWriter?, value: Component?) {
                    out!!.jsonValue(
                        GsonComponentSerializer.gson().serialize(value!!)
                    )
                }

                override fun read(`in`: JsonReader?): Component {
                    return GsonComponentSerializer.gson().deserialize(JsonParser.parseReader(`in`).toString())
                }
            })
            .registerTypeAdapter(Key::class.java, object : TypeAdapter<Key>() {
                override fun write(out: JsonWriter?, value: Key?) {
                    out!!.jsonValue("\"${value!!.asString()}\"")
                }

                override fun read(`in`: JsonReader?): Key {
                    return Key.key(JsonParser.parseReader(`in`).asString)
                }
            })
            .registerTypeAdapterFactory(EitherTypeAdapterFactory())
            .registerTypeAdapter(Style::class.java, object : TypeAdapter<Style>() {
                private val gson = GsonComponentSerializer.gson().serializer()

                override fun write(out: JsonWriter, style: Style?) {
                    if (style == null) {
                        out.nullValue()
                        return
                    }

                    out.jsonValue(gson.toJson(style))
                }

                override fun read(`in`: JsonReader): Style? {
                    return gson.fromJson(JsonParser.parseReader(`in`), Style::class.java)
                }
            })
            .registerTypeAdapter(IntProvider::class.java, object : TypeAdapter<IntProvider>() {
                override fun write(out: JsonWriter, value: IntProvider) {
                    out.jsonValue(IntProvider.TRANSCODER.from(value).toString())
                }

                override fun read(reader: JsonReader): IntProvider {
                    return when (reader.peek()) {
                        JsonToken.NUMBER -> {
                            IntProvider.Constant(reader.nextInt())
                        }

                        JsonToken.BEGIN_OBJECT -> {
                            return IntProvider.TRANSCODER.to(JsonParser.parseReader(reader))
                        }

                        else -> {
                            reader.skipValue()
                            throw JsonParseException("expected number or object")
                        }
                    }
                }

            })

        val GSON: Gson = GSON_BUILDER.create()
        val GSON_PRETTY: Gson = GSON_BUILDER.setPrettyPrinting().create()

        private lateinit var INSTANCE: MinecraftServer

        fun getInstance(): MinecraftServer = INSTANCE
    }


    private var running: Boolean = false

    private val tickables: MutableSet<Tickable> = ConcurrentHashMap.newKeySet()

    private val connections: MutableMap<Channel, MinecraftConnection> = ConcurrentHashMap()

    var brand: String = "custom"
    var motd: Component = Component.text("A $brand server")

    private val worlds: MutableMap<Key, World> = mutableMapOf()

    val knownPacks: List<KnownPack> = listOf(
        KnownPack("minecraft", "core", MINECRAFT_VERSION)
    )

    var ticksPerSecond: Int = 20
    var maxPlayers: Int = 20
    var serverViewDistance: Int = 8
    var simulationDistance: Int = 8

    var strictDisconnect: Boolean = true

    val onlineMode: Boolean = false // TODO

    var difficultyLocked: Boolean = false
    var difficulty: Difficulty = Difficulty.NORMAL
        set(value) {
            if (difficultyLocked) {
                LOGGER.warn("Trying to change difficulty while it is locked")
                return
            }

            field = value
        }

    init {
        INSTANCE = this;

        PluginMessageManager.register(this)

        NBT.init()
        NBT.TYPE_ADAPTERS[IntProvider::class.java] = NBTCodec.jsonDelegate(IntProvider.TRANSCODER)

        initRegistries()
        computeTags()

        EventManager.register(PlayerMoveEvent::class.java) { event ->
            event.player.moveFrom(event.from)
        }
    }

    fun run(port: Int) {
        running = true;

        thread {
            val mspt = 1000 / ticksPerSecond;

            while (running) {
                tick();

                Thread.sleep(mspt.toLong());
            }
        }

        startNettyServer(port)
    }

    private fun startKtorServer(port: Int) {
        runBlocking {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket = aSocket(selectorManager).tcp().bind("127.0.0.1", port)

            while (true) {
                val socket = serverSocket.accept()
                launch {
                    val receiveChannel = socket.openReadChannel()
                    val sendChannel = socket.openWriteChannel(autoFlush = true)

                    while (true) {
                        // TODO abandon netty, rewrite serialization in kt
                    }
                }
            }
        }
    }

    private fun startNettyServer(port: Int) {
        val factory = NioIoHandler.newFactory()

        val bossGroup = MultiThreadIoEventLoopGroup(factory)
        val workerGroup = MultiThreadIoEventLoopGroup(factory)

        try {
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch?.let {
                            ch.pipeline().addLast(
                                MCDecoder(),
                                MCEncoder(),
                                MCHandler()
                            )
                        }
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            val future: ChannelFuture = bootstrap.bind(port).sync()
            future.channel().closeFuture().sync()
        } catch (x: Exception) {
            throw RuntimeException(x)
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }

    private fun initRegistries() {
        Registries.init()

        Biomes.populate()
        TrimMaterials.populate()
        TrimPatterns.populate()
        BannerPatterns.populate()
        ChatTypes.populate()
        DamageTypes.populate()
        DimensionTypes.populate()
        WolfVariants.populate()
        PaintingVariants.populate()

        initClass(Items::class)
        initClass(ItemComponentTypes::class)

        loadBlocks()
    }

    private fun loadBlocks() {
        val obj = GSON.fromJson(Files.readString(Registry.DATA_PATH.resolve("blocks.json")),
            object : TypeToken<Map<String, BlocksReportTemplate>>() {})

        for (field in Blocks::class.declaredMemberProperties) {
            val v = field.get(Blocks) as Key

            val blockObj = obj[v.asString()] ?: continue

            for (state in blockObj.states) {
                val properties = Properties.ofMap(state.properties ?: mapOf<String, Any>())

                Registries.BLOCK.registerAt(state.id, v, BlockState(v, properties))
            }
        }

        LOGGER.debug(Registries.BLOCK.toString())
    }

    private data class BlocksReportTemplate(
        val states: List<State>
    ) {
        data class State(
            val id: Int,
            val properties: Map<String, String>?
        )
    }

    private fun flattenTags(map: Map<String, List<String>>): MutableMap<String, List<String>> {
        val cache = mutableMapOf<String, List<String>>()
        val visited = mutableSetOf<String>()

        fun resolveTag(id: String, currentPath: Set<String> = emptySet()): List<String> {
            if (id in cache) {
                return cache[id] ?: emptyList()
            }

            val tagValue = map[id] ?: return emptyList()
            val res = mutableListOf<String>()

            for (s in tagValue)
                if (s.startsWith('#'))
                    res.addAll(resolveTag(s.substring(1), currentPath + id))
                else
                    res.add(s)

            val distinct = res.distinct()
            cache[id] = distinct
            return distinct
        }

        val flat = mutableMapOf<String, List<String>>()
        for (key in map.keys) {
            visited.clear()
            flat[key] = resolveTag(key)
        }

        return flat
    }


    private fun computeTags() {
        for (registry in Registry.getAllRegistries()) {
            val outPath = Registry.DATA_PATH.resolve("tags").resolve(registry.id.value() + ".json")

            if (!outPath.toFile().exists()) continue

            val map: Map<String, List<String>> = MinecraftServer.GSON.fromJson(
                Files.readString(outPath),
                object : TypeToken<Map<String, List<String>>>() {}
            )

            val flat: Map<String, List<String>> = flattenTags(map)

            fun <T : Any> registerTagsTypeSafe(registry: Registry<T>) {
                for (entry in flat) {
                    registry.tags[Key.key(entry.key)] = mapTags<T>(registry, entry.value)
                }
            }

            registerTagsTypeSafe(registry)
        }
    }

    fun createWorld(type: ResourceKey<DimensionType>, key: Key): World {
        val world = World(type, key)
        worlds[key] = world

        return world
    }

    fun getWorld(key: Key): World {
        return worlds[key] ?: throw IllegalArgumentException("no such world: $key")
    }

    fun getEntity(id: Int): Entity? {
        return tickables
            .filterIsInstance<Entity>()
            .firstOrNull { it.getEntityId() == id }
    }

    inline fun <reified T : Entity> getEntityAssert(id: Int): T {
        val entity = getEntity(id) ?: throw RuntimeException("no such entity: $id")
        if (entity !is T) throw RuntimeException("entity $id's type does not match T")

        return entity
    }

    internal fun addTickable(tickable: Tickable) {
        tickables.add(tickable)
    }

    internal fun removeTickable(tickable: Tickable) {
        tickables.remove(tickable)
    }

    fun getWorlds(): List<World> {
        return worlds.values.toList()
    }

    private fun tick() {
        tickables.forEach { it.tick() }
    }

    internal fun getPlayingConnections(): Collection<MinecraftConnection> {
        return connections.values.filter { it.protocolState == ProtocolState.PLAY }
    }

    fun getPlayers(): Collection<Player> {
        return getPlayingConnections().map { it.player }
    }

    fun getPlayerCount(): Int = getPlayingConnections().size

    fun getStatusResponse(): String {
        return GSON.toJson(
            StatusResponseTemplate(
                StatusResponseTemplate.Version(MINECRAFT_VERSION, PROTOCOL),
                StatusResponseTemplate.Players(maxPlayers, getPlayerCount(), getSample()),
                motd, false
            )
        )
    }

    data class StatusResponseTemplate(
        val version: Version,
        val players: Players,
        val description: Component,
        val enforcesSecureChat: Boolean
    ) {
        data class Version(
            val name: String,
            val protocol: Int
        )

        data class Players(
            val max: Int,
            val online: Int,
            val sample: Collection<PlayerTemplate>
        )

        data class PlayerTemplate(
            val name: String,
            val uuid: UUID
        )
    }

    private fun getSample(): Collection<PlayerTemplate> {
        val set: MutableSet<PlayerTemplate> = mutableSetOf()

        for ((i, connection) in getPlayingConnections().withIndex()) {
            if (i >= 10) break

            val identity = connection.player.getIdentity()
            set.add(PlayerTemplate(identity.username, identity.uuid))
        }

        return set
    }

    fun removeConnection(channel: Channel) {
        val connection: MinecraftConnection = connections.remove(channel) ?: return
        connection.close()
        tickables.remove(connection)
    }

    fun removeConnection(connection: MinecraftConnection) {
        for (entry in connections) {
            if (entry.value == connection) {
                removeConnection(entry.key)
                return
            }
        }
    }

    fun getOrRegisterConnection(channel: Channel): MinecraftConnection {
        return connections.computeIfAbsent(channel) { _ -> NettyConnection(channel) }.also { tickables.add(it) }
    }

    fun getConnectionNullable(channel: Channel): MinecraftConnection? {
        return connections[channel]
    }

    private fun getEncodedBrand(): ByteArray {
        val buf = Unpooled.buffer(
            Codec.VARINT.varintSize(brand.length) + brand.length
        )
        Codec.STRING.write(buf, brand)

        val array = buf.array()
        buf.release()

        return array
    }

    @PluginMessageManager.Subscribe("minecraft:brand")
    fun onBrand(connection: MinecraftConnection, data: ByteArray) {
        connection.player.clientBrand = Codec.STRING.read(data, 0)

        connection.sendPacket(
            PluginMessagePacket(
                Key.key("brand"),
                getEncodedBrand()
            )
        )

    }

    @PluginMessageManager.Subscribe("minecraft:register")
    fun onRegister(connection: MinecraftConnection, data: ByteArray) {
        println(splitMapByteArray(0.toByte(), data) { String(it) })
    }

}