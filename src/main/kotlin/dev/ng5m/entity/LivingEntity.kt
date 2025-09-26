package dev.ng5m.entity

import dev.ng5m.registry.ResourceKey

open class LivingEntity : Entity {
    var headYaw = 0F

    protected constructor(type: EntityType) : super(type)

    protected constructor(typeKey: ResourceKey<EntityType>, id: Int) : super(typeKey, id)

    constructor(typeKey: ResourceKey<EntityType>) : super(typeKey)
}