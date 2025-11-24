package dev.ng5m.entity

import dev.ng5m.block.BlockState
import dev.ng5m.entity.EntityMetadata.Type
import dev.ng5m.item.ItemStack
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Transcoder
import dev.ng5m.util.byteEnumTranscoder
import net.kyori.adventure.text.Component
import org.joml.Quaternionf
import org.joml.Vector3f

open class DisplayEntity(type: ResourceKey<EntityType>) : Entity(type) {
    companion object {
        val METADATA_INTERPOLATION_DELAY = EntityMetadata.Index(8, Type.VARINT, 0)
        val METADATA_TRANSFORMATION_INTERPOLATION_DURATION = EntityMetadata.Index(9, Type.VARINT, 0)
        val METADATA_POS_ROT_INTERPOLATION_DURATION = EntityMetadata.Index(10, Type.VARINT, 0)
        val METADATA_TRANSLATION = EntityMetadata.Index(11, Type.VECTOR3F, Vector3f(0f, 0f, 0f))
        val METADATA_SCALE = EntityMetadata.Index(12, Type.VECTOR3F, Vector3f(1f, 1f, 1f))
        val METADATA_ROTATION_LEFT = EntityMetadata.Index(13, Type.QUATERNION, Quaternionf(0f, 0f, 0f, 1f))
        val METADATA_ROTATION_RIGHT = EntityMetadata.Index(14, Type.QUATERNION, Quaternionf(0f, 0f, 0f, 1f))
        val METADATA_BILLBOARD_CONSTRAINTS = EntityMetadata.Index(15, Type.BYTE, 0)
        val METADATA_BRIGHTNESS_OVERRIDE = EntityMetadata.Index(16, Type.VARINT, -1)
        val METADATA_VIEW_RANGE = EntityMetadata.Index(17, Type.FLOAT, 1f)
        val METADATA_SHADOW_RADIUS = EntityMetadata.Index(18, Type.FLOAT, 0f)
        val METADATA_SHADOW_STRENGTH = EntityMetadata.Index(19, Type.FLOAT, 1f)
        val METADATA_WIDTH = EntityMetadata.Index(20, Type.FLOAT, 0f)
        val METADATA_HEIGHT = EntityMetadata.Index(21, Type.FLOAT, 0f)
        val METADATA_GLOW_COLOR_OVERRIDE = EntityMetadata.Index(22, Type.VARINT, -1)
    }

    var interpolationDelay by MetadataProperty.of(metadata, METADATA_INTERPOLATION_DELAY)
    var transformationInterpolationDuration by MetadataProperty.of(
        metadata,
        METADATA_TRANSFORMATION_INTERPOLATION_DURATION
    )
    var posRotInterpolationDuration by MetadataProperty.of(metadata, METADATA_POS_ROT_INTERPOLATION_DURATION)
    var translation by MetadataProperty.of(metadata, METADATA_TRANSLATION)
    var scale by MetadataProperty.of(metadata, METADATA_SCALE)
    var rotationLeft by MetadataProperty.of(metadata, METADATA_ROTATION_LEFT)
    var rotationRight by MetadataProperty.of(metadata, METADATA_ROTATION_RIGHT)
    var billboardConstraints: BillboardConstraints by MetadataProperty(
        metadata,
        METADATA_BILLBOARD_CONSTRAINTS,
        byteEnumTranscoder()
    )
    var brightnessOverride by MetadataProperty.of(metadata, METADATA_BRIGHTNESS_OVERRIDE)
    var viewRange by MetadataProperty.of(metadata, METADATA_VIEW_RANGE)
    var shadowRadius by MetadataProperty.of(metadata, METADATA_SHADOW_RADIUS)
    var shadowStrength by MetadataProperty.of(metadata, METADATA_SHADOW_STRENGTH)
    var width by MetadataProperty.of(metadata, METADATA_WIDTH)
    var height by MetadataProperty.of(metadata, METADATA_HEIGHT)
    var glowColorOverride by MetadataProperty.of(metadata, METADATA_GLOW_COLOR_OVERRIDE)

    enum class BillboardConstraints {
        FIXED,
        VERTICAL,
        HORIZONTAL,
        CENTER
    }

    open class BlockDisplayEntity() : DisplayEntity(EntityType.BLOCK_DISPLAY) {
        companion object {
            val METADATA_BLOCK_STATE = EntityMetadata.Index(23, Type.BLOCK_STATE, BlockState.AIR)
        }

        var blockState by MetadataProperty.of(metadata, METADATA_BLOCK_STATE)
    }

    open class ItemDisplayEntity() : DisplayEntity(EntityType.ITEM_DISPLAY) {
        companion object {
            val METADATA_ITEM = EntityMetadata.Index(23, Type.SLOT, ItemStack.AIR)
            val METADATA_DISPLAY_TYPE = EntityMetadata.Index(24, Type.BYTE, 0)
        }

        var item by MetadataProperty.of(metadata, METADATA_ITEM)
        var displayType: DisplayType by MetadataProperty(metadata, METADATA_DISPLAY_TYPE, byteEnumTranscoder())

        enum class DisplayType {
            NONE,
            THIRD_PERSON_LEFT_HAND,
            THIRD_PERSON_RIGHT_HAND,
            FIRST_PERSON_LEFT_HAND,
            FIRST_PERSON_RIGHT_HAND,
            HEAD,
            GUI,
            GROUND,
            FIXED
        }
    }

    open class TextDisplayEntity() : DisplayEntity(EntityType.TEXT_DISPLAY) {
        companion object {
            val METADATA_TEXT = EntityMetadata.Index(23, Type.TEXT_COMPONENT, Component.empty())
            val METADATA_LINE_WIDTH = EntityMetadata.Index(23, Type.VARINT, 200)
            val METADATA_BACKGROUND_COLOR = EntityMetadata.Index(23, Type.VARINT, 0x40000000)
            val METADATA_TEXT_OPACITY = EntityMetadata.Index(23, Type.BYTE, -1)
            val METADATA_B27 = EntityMetadata.Index(23, Type.BYTE, 0)
        }

        var text by MetadataProperty.of(metadata, METADATA_TEXT)
        var lineWidth by MetadataProperty.of(metadata, METADATA_LINE_WIDTH)
        var backgroundColor by MetadataProperty.of(metadata, METADATA_BACKGROUND_COLOR)
        var textOpacity by MetadataProperty.of(metadata, METADATA_TEXT_OPACITY)
        var hasShadow by MetadataProperty.bitMask(metadata, METADATA_B27, 0x01)
        var seeThrough by MetadataProperty.bitMask(metadata, METADATA_B27, 0x01)
        var defaultBackgroundColor by MetadataProperty.bitMask(metadata, METADATA_B27, 0x01)
        var alignment: TextAlignment
            get() {
                val b = metadata.getOrThrow(METADATA_B27).toInt()
                return if ((b and 0x8) != 0) TextAlignment.LEFT
                else if ((b and 0x10) != 0) TextAlignment.RIGHT else TextAlignment.CENTER
            }
            set(value) {
                val b = metadata.getOrThrow(METADATA_B27).toInt()
                metadata.set(
                    METADATA_B27, when (value) {
                        TextAlignment.CENTER -> b
                        TextAlignment.LEFT -> b or 0x8
                        TextAlignment.RIGHT -> b or 0x10
                    }.toByte()
                )
            }

        enum class TextAlignment {
            CENTER,
            LEFT,
            RIGHT
        }
    }

}