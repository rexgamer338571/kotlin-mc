package dev.ng5m.entity

import dev.ng5m.Tickable
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.util.AABB
import dev.ng5m.util.IntTracker
import dev.ng5m.util.math.Vector3d
import dev.ng5m.world.Location
import dev.ng5m.world.World
import java.util.UUID
import kotlin.properties.Delegates

open class Entity(private val type: EntityType) : Tickable {
    companion object {
        @JvmStatic
        protected val ID_TRACKER: IntTracker = IntTracker()
    }

    protected constructor(typeKey: ResourceKey<EntityType>, id: Int) : this(typeKey) {
        this.id = id
    }

    constructor(typeKey: ResourceKey<EntityType>) : this(Registries.ENTITY_TYPE.getOrThrow(typeKey)) {
        this.id = ID_TRACKER.next()
    }

    private var id by Delegates.notNull<Int>()
    val uuid: UUID = UUID.randomUUID()
    private var world: World? = null

    val portalCooldown = 0
    private var age: Int = 0

    lateinit var location: Location
    lateinit var previousLocation: Location
    var velocity = Vector3d.ZERO

    var onGround: Boolean = true
    var pushingAgainstWall: Boolean = false


    fun getBoundingBox(): AABB {
        return type.boundingBox
    }

    internal fun setWorld(world: World) {
        this.world = world
    }

    fun getWorld(): World? {
        return world
    }

    fun getEntityId(): Int {
        return id
    }

    fun typeKey(): ResourceKey<EntityType> {
        return Registries.ENTITY_TYPE.resourceKeyByValue(type)
    }

    open fun getEntityData(): Int = 0

    override fun tick() {
        age++

        if (::location.isInitialized && ::previousLocation.isInitialized)
            velocity = location.xyz.clone() - previousLocation.xyz

        if (::location.isInitialized) previousLocation = location
    }


}