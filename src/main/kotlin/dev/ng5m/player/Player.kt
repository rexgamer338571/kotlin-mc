package dev.ng5m.player

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.entity.EntityType
import dev.ng5m.entity.ItemEntity
import dev.ng5m.entity.LivingEntity
import dev.ng5m.entity.inventory.Inventory
import dev.ng5m.entity.inventory.PlayerInventory
import dev.ng5m.entity.inventory.TitledInventory
import dev.ng5m.item.ItemStack
import dev.ng5m.packet.common.s2c.DisconnectS2CPacket
import dev.ng5m.packet.configuration.c2s.ClientInformationC2SPacket
import dev.ng5m.packet.play.s2c.*
import dev.ng5m.util.IntTracker
import dev.ng5m.util.PacketSendContext
import dev.ng5m.world.ChunkSection
import dev.ng5m.world.Location
import dev.ng5m.world.World
import net.kyori.adventure.text.Component
import java.util.Optional
import kotlin.math.min
import kotlin.math.round
import kotlin.properties.Delegates

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
    var heldItem = 0

    lateinit var connection: MinecraftConnection
    private var identity: Identity? = null

    var clientBrand: String = "null";
    private var locale: String = "null";
    var viewDistance: Int = MinecraftServer.getInstance().serverViewDistance
        get() {
            return min(field, MinecraftServer.getInstance().serverViewDistance)
        }

    var simulationDistance: Int = MinecraftServer.getInstance().simulationDistance
    lateinit var chatMode: ChatMode
    var chatColors by Delegates.notNull<Boolean>()
    private var displayedSkinParts: SkinParts = SkinParts()
    private var mainHand: Hand = Hand.RIGHT
    private var enableTextFiltering: Boolean = false
    private var allowServerListings: Boolean = true
    private var particleStatus: ParticleStatus = ParticleStatus.ALL

    private var previousGameMode: GameMode = GameMode.UNDEFINED

    var carriedItem: ItemStack = ItemStack.AIR
    var openInventory: Inventory? = null
    internal val syncIdTracker = IntTracker()
    var gameMode: GameMode = GameMode.SURVIVAL
        set(value) {
            previousGameMode = field
            field = value
        }

    private var deathLocation: Location? = null

    private val viewedChunks: MutableSet<Long> = mutableSetOf()

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

    fun disconnectWithException(x: Exception) {
        disconnect(Component.text(x.toString()))
    }

    fun dropItem(itemStack: ItemStack) {
        getWorld().spawnEntity(location, ItemEntity(itemStack))
    }

    fun dropHeldItem(fullStack: Boolean) {
        val item = inventory.hotbar(heldItem).clone()
        val count = item.count()

        if (count - 1 <= 0 || fullStack) {
            dropItem(item)
            inventory.hotbar(heldItem, ItemStack.AIR)
        } else {
            dropItem(item.withCount(1))
            inventory.hotbar(heldItem, item.withCount(count - 1))
        }
    }

    fun getItemInHand(hand: Hand.Relative): ItemStack {
        return if (hand == Hand.Relative.MAIN_HAND)
            inventory.hotbar(heldItem)
        else inventory.offhand()
    }

    fun generateAndSendChunksAround() {
        val playerChunkXZ = location.toChunk()
        val vd = viewDistance

        MinecraftServer.workerPool.submit {
            var ctx: PacketSendContext? = null
            location.world.generateInRadius(
                playerChunkXZ.x, playerChunkXZ.y, vd
            ) { chunk ->
                ctx = connection.sendPacket(ChunkS2CPacket(chunk))
            }

            ctx?.onFinish {
                println("average: ${ChunkSection.totalTime / ChunkSection.times}ns")
            }
        }
    }

    fun sendSystemMessage(content: Component, actionBar: Boolean = false) {
        connection.sendPacket(SystemChatS2CPacket(content, actionBar))
    }

    fun openInventory(inventory: Inventory) {
        openInventory = inventory

        connection.sendPacket(
            OpenScreenS2CPacket(
                inventory.id(),
                inventory.type(),
                if (inventory is TitledInventory) inventory.title() else Component.empty()
            )
        )

        connection.sendPacket(
            SetContainerContentsS2CPacket(
                inventory.id(), inventory.revision(),
                inventory.slots(), carriedItem
            )
        )

        if (inventory.slots().any { it != ItemStack.AIR })
            println(inventory.slots().first { it != ItemStack.AIR }.item.key)
    }

    fun updateSlot(inventory: Inventory, slot: Short, stack: ItemStack) {
        connection.sendPacket(
            SetContainerSlotS2CPacket(
                inventory.id(), inventory.revision(),
                slot, stack
            )
        )
    }

    fun updateCarriedItem() {
        connection.sendPacket(SetCursorItemS2CPacket(carriedItem))
    }

    private fun packDelta(d: Double): Double = round(d * 4096.0)

    private fun chunkRadius(radius: Int, rootX: Int, rootZ: Int, set: MutableSet<Long>) {
        for (cx in rootX - radius .. rootZ + radius)
            for (cz in rootZ - radius .. rootZ + radius)
                set.add(World.packChunkCoordinates(cx, cz))
    }

    fun move() {
        val delta = location.xyz.clone() - previousLocation.xyz

        val specialDelta =
            (location.xyz.clone().transform(this::packDelta) - (previousLocation.xyz.clone().transform(this::packDelta))).toShorts()

        val rotYaw = (location.yaw - previousLocation.yaw) != 0.0F
        val rotPitch = (location.pitch - previousLocation.pitch) != 0.0F

        getOtherPlayers().forEach {
            if (delta.x > 8 || delta.x < -7.999755859375 ||
                delta.y > 8 || delta.y < -7.999755859375 ||
                delta.z > 8 || delta.z < -7.999755859375
            ) {
                it.connection.sendPacket(
                    SyncEntityPositionS2CPacket(this)
                )
            } else {
                it.connection.sendPacket(
                    MoveEntityPacket.PosRot(
                        getEntityId(), specialDelta,
                        headYaw, location.pitch, onGround
                    )
                )

                if (rotYaw) it.connection.sendPacket(RotateHeadS2CPacket(this))
            }
        }

        val playerChunkXZ = location.toChunk()
        val vd = viewDistance

        val previousChunkXZ = previousLocation.toChunk()

        if (playerChunkXZ == previousChunkXZ) return

        if (viewedChunks.isEmpty()) {
            chunkRadius(vd, previousChunkXZ.x, previousChunkXZ.y, viewedChunks)
        }

        connection.sendPacket(SetCenterChunkS2CPacket(playerChunkXZ.x, playerChunkXZ.y))

        val current = mutableSetOf<Long>()
        chunkRadius(vd, playerChunkXZ.x, playerChunkXZ.y, current)

        for (pos in viewedChunks subtract current) {
            val unpacked = World.unpackChunkCoordinates(pos)
            getWorld().unloadChunk(unpacked.first, unpacked.second)
        }

        for (pos in current subtract viewedChunks) {
            val unpacked = World.unpackChunkCoordinates(pos)
            connection.sendPacket(ChunkS2CPacket(getWorld().generateIfAbsent(unpacked.first, unpacked.second)))
        }

        viewedChunks.clear()
        viewedChunks.addAll(current)
    }

    fun getOtherPlayers(): Collection<Player> {
        return MinecraftServer.getInstance().getPlayers().filter { it != this }
    }

}