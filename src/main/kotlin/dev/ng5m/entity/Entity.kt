package dev.ng5m.entity

import dev.ng5m.Ticking
import dev.ng5m.packet.play.s2c.SpawnEntityS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.AABB
import dev.ng5m.util.IntTracker
import org.joml.Vector3d
import dev.ng5m.world.Location
import dev.ng5m.world.World
import java.util.UUID
import kotlin.properties.Delegates

open class Entity(private val type: EntityType) : Ticking {
    companion object {
        @JvmStatic
        protected val ID_TRACKER: IntTracker = IntTracker()
    }

    protected constructor(typeKey: ResourceKey<EntityType>, id: Int) : this(typeKey) {
        this.id = id
    }

    constructor(typeKey: ResourceKey<EntityType>) : this(Registries.ENTITY_TYPE.getOrThrow(typeKey))

    private var id = ID_TRACKER.next()
    val uuid: UUID = UUID.randomUUID()
    private lateinit var world: World

    val portalCooldown = 0
    private var age: Int = 0

    lateinit var location: Location
    lateinit var previousLocation: Location
    var velocity = Vector3d(0.0, 0.0, 0.0)

    var health: Double = type.defaultHealth

    var onGround: Boolean = true
    var pushingAgainstWall: Boolean = false


    fun getBoundingBox(): AABB {
        return type.boundingBox
    }

    internal fun setWorld(world: World) {
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
    }

    open fun getEntityData(): Int = 0

    override fun tick() {
        age++

        if (::location.isInitialized && ::previousLocation.isInitialized)
            velocity = location.clone().xyz.sub(previousLocation.xyz)

        if (::location.isInitialized) previousLocation = location
    }


}