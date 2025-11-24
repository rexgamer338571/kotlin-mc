package dev.ng5m.entity

import dev.ng5m.entity.EntityMetadata.Type
import org.joml.Vector3f

open class ArmorStandEntity : LivingEntity(EntityType.ARMOR_STAND) {
    companion object {
        val METADATA_B15 = EntityMetadata.Index(15, Type.BYTE, 0)
        val METADATA_HEAD_ROTATION = EntityMetadata.Index(16, Type.ROTATIONS, Vector3f(0f, 0f, 0f))
        val METADATA_BODY_ROTATION = EntityMetadata.Index(17, Type.ROTATIONS, Vector3f(0f, 0f, 0f))
        val METADATA_LEFT_ARM_ROTATION = EntityMetadata.Index(18, Type.ROTATIONS, Vector3f(-10f, 0f, -10f))
        val METADATA_RIGHT_ARM_ROTATION = EntityMetadata.Index(19, Type.ROTATIONS, Vector3f(-15f, 0f, 10f))
        val METADATA_LEFT_LEG_ROTATION = EntityMetadata.Index(20, Type.ROTATIONS, Vector3f(-1f, 0f, -1f))
        val METADATA_RIGHT_LEG_ROTATION = EntityMetadata.Index(21, Type.ROTATIONS, Vector3f(1f, 0f, 1f))
    }



}