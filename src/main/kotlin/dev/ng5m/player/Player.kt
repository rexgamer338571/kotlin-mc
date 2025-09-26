package dev.ng5m.player

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.entity.EntityType
import dev.ng5m.entity.LivingEntity
import dev.ng5m.entity.inventory.PlayerInventory
import dev.ng5m.packet.common.s2c.DisconnectS2CPacket
import dev.ng5m.packet.configuration.c2s.ClientInformationC2SPacket
import dev.ng5m.packet.play.s2c.*
import dev.ng5m.util.IntTracker
import dev.ng5m.util.PacketSendContext
import dev.ng5m.util.math.Vector2i
import dev.ng5m.world.ChunkSection
import dev.ng5m.world.Location
import net.kyori.adventure.text.Component
import java.util.Optional
import kotlin.math.min
import kotlin.math.round

class Player private constructor(id: Int) : LivingEntity(EntityType.PLAYER, id) {
    companion object {
        fun makeUnsafe(id: Int): Player {
            return Player(id)
        }
    }

    constructor() : this(ID_TRACKER.next())

    val teleportIdTracker = IntTracker()
    val windowIdTracker = IntTracker()

    val inventory = PlayerInventory(this)

    lateinit var connection: MinecraftConnection
    private var identity: Identity? = null

    var clientBrand: String = "null";
    private var locale: String = "null";
    var viewDistance: Int = MinecraftServer.getInstance().serverViewDistance
        get() {
            return min(field, MinecraftServer.getInstance().serverViewDistance)
        }

    var simulationDistance: Int = MinecraftServer.getInstance().simulationDistance
    private var chatMode: ChatMode = ChatMode.ENABLED;
    private var chatColors: Boolean = true;
    private var displayedSkinParts: SkinParts = SkinParts()
    private var mainHand: Hand = Hand.RIGHT
    private var enableTextFiltering: Boolean = false
    private var allowServerListings: Boolean = true
    private var particleStatus: ParticleStatus = ParticleStatus.ALL

    private var previousGameMode: GameMode = GameMode.UNDEFINED
    var gameMode: GameMode = GameMode.SURVIVAL
        set(value) {
            previousGameMode = field
            field = value
        }

    private var deathLocation: Location? = null

    var sprinting = false
    var sneaking = false

    fun applyClientInformation(packet: ClientInformationC2SPacket) {
        locale = packet.locale
        viewDistance = min(packet.viewDistance.toInt(), MinecraftServer.getInstance().serverViewDistance)
        chatMode = packet.chatMode
        displayedSkinParts = packet.skinParts
        mainHand = packet.mainHand
        enableTextFiltering = packet.enableTextFiltering
        allowServerListings = packet.allowServerListings
        particleStatus = packet.particleStatus
    }

    fun makeConnected(identity: Identity): Player {
        this.identity = identity
        return this
    }

    fun getIdentity(): Identity {
        return identity!!
    }

    fun getPreviousGameMode(): GameMode {
        return previousGameMode
    }

    fun getDeathLocation(): Optional<Location> {
        return Optional.ofNullable(deathLocation)
    }

    fun disconnect(reason: Component) {
        connection.sendPacket(DisconnectS2CPacket(reason)).onFinish {
            connection.removePlayer()
        }
    }

    fun generateAndSendChunksAround() {
        val playerChunkXZ = location.toChunk()
        val vd = viewDistance

        var ctx: PacketSendContext? = null
        for (x in playerChunkXZ.x - vd..playerChunkXZ.x + vd) {
            for (z in playerChunkXZ.y - vd..playerChunkXZ.y + vd) {
                val chunk = location.world.generateIfAbsent(x, z)
                ctx = connection.sendPacket(ChunkS2CPacket(chunk))
            }
        }

        ctx!!.onFinish {
            println("average: ${ChunkSection.totalTime / ChunkSection.times}ns")
        }
    }

    private fun packDelta(d: Double): Double = round(d * 4096.0)

    fun moveFrom(from: Location) {
        previousLocation = from

        val delta = location.xyz.clone() - from.xyz

        val specialDelta =
            (location.xyz.clone().transform(this::packDelta) - (from.xyz.clone().transform(this::packDelta))).toShorts()

        val rotation = (location.yaw - from.yaw) != 0.0F || (location.pitch - from.pitch) != 0.0F

        getOtherPlayers().forEach {
            it.connection.sendPacket(
                if (
                    delta.x > 8 || delta.x < -7.999755859375 ||
                    delta.y > 8 || delta.y < -7.999755859375 ||
                    delta.z > 8 || delta.z < -7.999755859375
                ) SyncEntityPositionS2CPacket(this)
                else (if (rotation) MoveEntityPacket.PosRot(
                    getEntityId(), specialDelta,
                    location.yaw, location.pitch, onGround
                ) else MoveEntityPacket.Pos(getEntityId(), specialDelta, onGround))
            )
        }

        val playerChunkXZ = location.toChunk()
        val vd = viewDistance

        val previousChunkXZ = previousLocation.toChunk()
        if (playerChunkXZ == previousChunkXZ) return

        connection.sendPacket(SetCenterChunkS2CPacket(playerChunkXZ.x, playerChunkXZ.y))

        val prev = mutableSetOf<Vector2i>()
        for (cx in previousChunkXZ.x - vd..previousChunkXZ.x + vd) {
            for (cz in previousChunkXZ.y - vd..previousChunkXZ.y + vd) {
                prev.add(Vector2i(cx, cz))
            }
        }

        val current = mutableSetOf<Vector2i>()
        for (cx in playerChunkXZ.x - vd..playerChunkXZ.x + vd) {
            for (cz in playerChunkXZ.y - vd..playerChunkXZ.y + vd) {
                current.add(Vector2i(cx, cz))
            }
        }

        var ctx: PacketSendContext? = null
        for (pos in prev subtract current) {
            ctx = connection.sendPacket(UnloadChunkS2CPacket(pos.x, pos.y))
        }

        ctx!!.onFinish {
            for (pos in current subtract prev) {
                connection.sendPacket(ChunkS2CPacket(getWorld()!!.generateIfAbsent(pos.x, pos.y)))
            }
        }
    }

    fun getOtherPlayers(): Collection<Player> {
        return MinecraftServer.getInstance().getPlayers().filter { it != this }
    }

}