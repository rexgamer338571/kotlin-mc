package dev.ng5m.entity

import dev.ng5m.Ticking
import dev.ng5m.packet.play.c2s.InteractC2SPacket
import dev.ng5m.packet.play.s2c.SetEntityDataS2CPacket
import dev.ng5m.packet.play.s2c.SpawnEntityS2CPacket
import dev.ng5m.player.Hand
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.util.BitField
import dev.ng5m.util.AABB
import dev.ng5m.util.IntTracker
import org.joml.Vector3f
import org.joml.Vector3d
import dev.ng5m.world.Location
import dev.ng5m.world.World
import net.kyori.adventure.text.Component
import java.util.UUID
import kotlin.properties.Delegates

open class Entity(private val type: EntityType) : Ticking {
    companion object {
        @JvmStatic
        protected val ID_TRACKER: IntTracker = IntTracker()

        val METADATA_B0 = EntityMetadata.Index(0, EntityMetadata.Type.BYTE, 0.toByte())
        val METADATA_AIR_TICKS = EntityMetadata.Index(1, EntityMetadata.Type.VARINT, 300)
        val METADATA_CUSTOM_NAME = EntityMetadata.Index(2, EntityMetadata.Type.OPTIONAL_TEXT_COMPONENT, null)
        val METADATA_CUSTOM_NAME_VISIBLE = EntityMetadata.Index(3, EntityMetadata.Type.BOOLEAN, false)
        val METADATA_SILENT = EntityMetadata.Index(4, EntityMetadata.Type.BOOLEAN, false)
        val METADATA_NO_GRAVITY = EntityMetadata.Index(5, EntityMetadata.Type.BOOLEAN, false)
        val METADATA_POSE = EntityMetadata.Index(6, EntityMetadata.Type.POSE, Pose.STANDING)
        val METADATA_FROZEN_TICKS = EntityMetadata.Index(7, EntityMetadata.Type.VARINT, 0)
    }

    protected constructor(typeKey: ResourceKey<EntityType>, id: Int) : this(typeKey) {
        this.id = id
    }

    constructor(typeKey: ResourceKey<EntityType>) : this(Registries.ENTITY_TYPE.getOrThrow(typeKey))

    private var id = ID_TRACKER.next()
    var uuid: UUID = UUID.randomUUID()
    private lateinit var world: World

    val portalCooldown = 0
    private var age: Int = 0

    lateinit var location: Location
    lateinit var previousLocation: Location
    var velocity = Vector3d(0.0, 0.0, 0.0)

    var health: Double = type.defaultHealth

    var onGround: Boolean = true

    var pushingAgainstWall: Boolean = false

    val metadata = EntityMetadata()

    var customName by MetadataProperty.of(metadata, METADATA_CUSTOM_NAME)

    var hideNameTag by MetadataProperty.bitMask(metadata, METADATA_B0, 0x02)
    var sprinting by MetadataProperty.bitMask(metadata, METADATA_B0, 0x08)
    var invisible by MetadataProperty.bitMask(metadata, METADATA_B0, 0x20)

    fun getBoundingBox(): AABB {
        return type.boundingBox
    }

    fun setWorld(world: World) {
        this.world = world
    }

    fun getWorld(): World = world
    fun isSpawned(): Boolean = ::world.isInitialized

    fun getEntityId(): Int {
        return id
    }

    fun typeKey(): ResourceKey<EntityType> {
        return Registries.ENTITY_TYPE.resourceKeyByValue(type)
    }

    open fun spawnForPlayer(player: Player) {
        player.connection.sendPacket(SpawnEntityS2CPacket(this))
        syncMetadata(player)
    }

    open fun getEntityData(): Int = 0

    open fun onInteracted(player: Player, packet: InteractC2SPacket) {
        player.sendSystemMessage(Component.text("i was interacted: $packet"))
    }

    open fun onAttacked(player: Player, playerSneaking: Boolean) {
    }

    open fun onInteracted(player: Player, hand: Hand.Relative, playerSneaking: Boolean) {
    }

    open fun onInteractedAt(player: Player, hand: Hand.Relative, relativePos: Vector3f, playerSneaking: Boolean) {
    }

    fun syncMetadata(player: Player) {
        player.connection.sendPacket(SetEntityDataS2CPacket(this))
    }

    override fun tick() {
        age++

        if (::location.isInitialized && ::previousLocation.isInitialized)
            velocity = location.clone().xyz.sub(previousLocation.xyz)

        if (::location.isInitialized) previousLocation = location
    }


}