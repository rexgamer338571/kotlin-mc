package dev.ng5m.world.particle

import dev.ng5m.block.BlockState
import dev.ng5m.item.ItemStack
import dev.ng5m.serialization.Codec
import dev.ng5m.util.CODEC_POSITION
import dev.ng5m.util.annotation.ColorFormat
import dev.ng5m.util.annotation.FloatRange
import dev.ng5m.util.annotation.PresentIf
import org.joml.Vector3i

sealed interface ParticleOptions {

    data object Empty : ParticleOptions {
        val CODEC: Codec<Empty> = Codec.empty(Empty)
    }

    data class Block(val blockStateId: Int) : ParticleOptions {
        companion object {
            val CODEC: Codec<Block> = Codec.of(
                Codec.VARINT, { it.blockStateId }, ::Block
            )
        }

        constructor(blockState: BlockState) :
                this(BlockState.stateManager.idBy(blockState))
    }

    data class Dust(
        val color: @ColorFormat(ColorFormat.Format.URGB) Int,
        val scale: @FloatRange(0.01f, 4f) Float
    ) : ParticleOptions {
        companion object {
            val CODEC: Codec<Dust> = Codec.of(
                Codec.INTEGER, { it.color },
                Codec.FLOAT, { it.scale },
                ::Dust
            )
        }
    }

    data class DustColorTransition(
        val fromColor: @ColorFormat(ColorFormat.Format.URGB) Int,
        val toColor: @ColorFormat(ColorFormat.Format.URGB) Int,
        val scale: @FloatRange(0.01f, 4f) Float
    ) : ParticleOptions {
        companion object {
            val CODEC: Codec<DustColorTransition> = Codec.of(
                Codec.INTEGER, { it.fromColor },
                Codec.INTEGER, { it.toColor },
                Codec.FLOAT, { it.scale },
                ::DustColorTransition
            )
        }
    }

    data class Color(
        val color: @ColorFormat(ColorFormat.Format.ARGB) Int
    ) : ParticleOptions {
        companion object {
            val CODEC: Codec<Color> = Codec.of(
                Codec.INTEGER, { it.color }, ::Color
            )
        }

        constructor(color: java.awt.Color) : this(color.rgb) // Color#value, which is returned by Color#getRGB contains alpha too
    }

    data class Roll(val roll: Float) : ParticleOptions {
        companion object {
            val CODEC: Codec<Roll> = Codec.of(
                Codec.FLOAT, { it.roll }, ::Roll
            )
        }
    }

    data class Item(val stack: ItemStack) : ParticleOptions {
        companion object {
            val CODEC: Codec<Item> = Codec.of(
                ItemStack.CODEC, { it.stack }, ::Item
            )
        }
    }

    data class Vibration(
        val positionSourceType: Source,
        val blockPosition: @PresentIf("positionSourceType == BLOCK") Vector3i?,
        val entityId: @PresentIf("positionSourceType == ENTITY") Int?,
        val entityEyeHeight: @PresentIf("positionSourceType == ENTITY") Float?,
        val travelTicks: Int
    ) : ParticleOptions {
        companion object {
            val CODEC: Codec<Vibration> = Codec.of(
                { buf ->
                    val type = Source.entries[Codec.VARINT.read(buf)]
                    Vibration(
                        type,
                        if (type == Source.BLOCK) CODEC_POSITION.read(buf) else null,
                        if (type == Source.ENTITY) Codec.VARINT.read(buf) else null,
                        if (type == Source.ENTITY) buf.readFloat() else null,
                        Codec.VARINT.read(buf)
                    )
                },
                { buf, it ->
                    Codec.VARINT.write(buf, it.positionSourceType.ordinal)
                    if (it.positionSourceType == Source.BLOCK) {
                        CODEC_POSITION.write(buf, it.blockPosition)
                    } else if (it.positionSourceType == Source.ENTITY) { // for the sake of readability
                        Codec.VARINT.write(buf, it.entityId)
                        buf.writeFloat(it.entityEyeHeight!!)
                    }

                    Codec.VARINT.write(buf, it.travelTicks)
                }
            )
        }

        enum class Source {
            BLOCK,
            ENTITY
        }
    }

    data class Delay(val delay: Int) : ParticleOptions {
        companion object {
            val CODEC: Codec<Delay> = Codec.of(
                Codec.VARINT, { it.delay }, ::Delay
            )
        }
    }

}