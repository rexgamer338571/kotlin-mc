package dev.ng5m

import com.google.gson.*
import dev.ng5m.data.computeTags
import dev.ng5m.data.json.COMPONENT_TYPE_ADAPTER
import dev.ng5m.data.json.INT_PROVIDER_TYPE_ADAPTER
import dev.ng5m.data.json.KEY_TYPE_ADAPTER
import dev.ng5m.data.json.STYLE_TYPE_ADAPTER
import dev.ng5m.data.loadBlocks
import dev.ng5m.entity.Entity
import dev.ng5m.event.EntityEvents
import dev.ng5m.event.EventManager
import dev.ng5m.event.LifecycleEvents
import dev.ng5m.event.impl.lifecycle.ServerShutdownEvent
import dev.ng5m.item.Items
import dev.ng5m.item.component.ItemComponentTypes
import dev.ng5m.packet.common.PluginMessagePacket
import dev.ng5m.player.Player
import dev.ng5m.registry.*
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.NBTCodec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization_kt.Transcoder
import dev.ng5m.server.NettyServer
import dev.ng5m.server.TCPServer
import dev.ng5m.util.*
import dev.ng5m.util.json.EitherTypeAdapterFactory
import dev.ng5m.world.Difficulty
import dev.ng5m.world.World
import io.netty.buffer.Unpooled
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread
import kotlin.math.max

class MinecraftServer {

    companion object {
        internal val LOCK = Object()

        private val LOGGER: Logger = LoggerFactory.getLogger(MinecraftServer::class.java)

        const val PROTOCOL: Int = 769
        const val MINECRAFT_VERSION: String = "1.21.4"

        val REGISTER_CHANNEL_TRANSCODER: Transcoder<ByteArray, List<Key>> = object : Transcoder<ByteArray, List<Key>> {
            override fun to(t: ByteArray): List<Key> =
                splitMapByteArray(0.toByte(), t) { Key.key(String(it)) }

            override fun from(r: List<Key>): ByteArray {
                val buf = Unpooled.buffer()
                r.forEach {
                    buf.writeBytes(it.asString().encodeToByteArray())
                    buf.writeByte(0)
                }
                return ByteArray(max(0, buf.writerIndex() - 1)).also { buf.getBytes(0, it) }
            }
        }

        val GSON_BUILDER: GsonBuilder = GsonBuilder()
            .registerTypeAdapter(Component::class.java, COMPONENT_TYPE_ADAPTER)
            .registerTypeAdapter(Key::class.java, KEY_TYPE_ADAPTER)
            .registerTypeAdapter(Style::class.java, STYLE_TYPE_ADAPTER)
            .registerTypeAdapter(IntProvider::class.java, INT_PROVIDER_TYPE_ADAPTER)
            .registerTypeAdapterFactory(EitherTypeAdapterFactory())

        val GSON: Gson = GSON_BUILDER.create()
        val GSON_PRETTY: Gson = GSON_BUILDER.setPrettyPrinting().create()

        private lateinit var INSTANCE: MinecraftServer

        fun getInstance(): MinecraftServer = INSTANCE
    }


    val pluginManager = PluginManager()

    private var running: Boolean = false


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

    private val server: TCPServer<*> = NettyServer()
    private val ticker = Ticker((1000 / ticksPerSecond).toLong(), object : Ticker.Events {
        override fun startTick() {
            Profiler.push("serverTick")
        }

        override fun endTick() {
            Profiler.pop("serverTick")
            ServerPerformanceMonitor.tick(Profiler.get("serverTick")!!)
        }
    })

    init {
        INSTANCE = this;

        NBT.init()
        NBT.TYPE_ADAPTERS[IntProvider::class.java] = NBTCodec.jsonDelegate(IntProvider.TRANSCODER)

        initRegistries()
        computeTags()

        PluginMessageManager.register(this)
        pluginManager.enablePlugins()

        Runtime.getRuntime().addShutdownHook(
            thread(
                start = false,
                block = {
                    EventManager.fire(ServerShutdownEvent)
                })
        )

        EventManager.register(LifecycleEvents)
        EventManager.register(EntityEvents)
    }

    fun run(port: Int) {
        running = true;

        ticker.start()
        server.start(port)
    }

    fun shutdown() {
        require(running) { "Server not running" }

        ticker.stop()
        server.stop()
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

    fun createWorld(type: ResourceKey<DimensionType>, key: Key): World {
        val world = World(type, key)
        worlds[key] = world

        return world
    }

    fun getWorld(key: Key): World {
        return worlds[key] ?: throw IllegalArgumentException("no such world: $key")
    }

    fun getEntity(id: Int): Entity? {
        return ticker.ticking()
            .filterIsInstance<Entity>()
            .firstOrNull { it.getEntityId() == id }
    }

    inline fun <reified T : Entity> getEntityAssert(id: Int): T {
        val entity = getEntity(id) ?: throw RuntimeException("no such entity: $id")
        if (entity !is T) throw RuntimeException("entity $id's type does not match T")

        return entity
    }

    internal fun addTicking(ticking: Ticking) {
        ticker.submit(ticking)
    }

    internal fun removeTicking(ticking: Ticking) {
        ticker.remove(ticking)
    }

    fun getWorlds(): List<World> {
        return worlds.values.toList()
    }

    internal fun getPlayingConnections(): Collection<MinecraftConnection> {
        return server.connections.values.filter { it.protocolState == ProtocolState.PLAY }
    }

    fun getPlayers(): Collection<Player> {
        return getPlayingConnections().map { it.player }
    }

    fun getPlayerCount(): Int = getPlayingConnections().size

    private fun getEncodedBrand(): ByteArray {
        val buf = Unpooled.buffer(
            Codec.VARINT.varintSize(brand.length) + brand.length
        )
        Codec.STRING.write(buf, brand)

        val array = buf.array()
        buf.release()

        return array
    }

    fun registerConnection(connection: MinecraftConnection) {
        ticker.submit(connection)
    }

    fun unregisterConnection(connection: MinecraftConnection) {
        ticker.remove(connection)
    }

    fun removeConnection(connection: MinecraftConnection) {
        server.removeConnection(connection)
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : TCPServer<*>> getServer(): S = server as S

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
        val channels = REGISTER_CHANNEL_TRANSCODER.to(data)
        LOGGER.debug(channels.toString())

        connection.sendPacket(
            PluginMessagePacket(
                Key.key("minecraft", "register"),
                REGISTER_CHANNEL_TRANSCODER.from(channels.filter {
                    PluginMessageManager.isRegistered(it)
                })
            )
        )

    }

}