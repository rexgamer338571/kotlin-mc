package dev.ng5m.entity

import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.serialization.nbt.Tag
import dev.ng5m.util.math.Vector2i
import dev.ng5m.util.math.Vector3i

class BlockEntity(val pos: Vector3i, val type: ResourceKey<BlockEntityType>, val data: Tag<*>) {
    companion object {
        private val CODEC_PACKED_XZ: Codec<Vector2i> = Codec.of(
            { buf ->
                val byte = buf.readUnsignedByte().toInt()
                return@of Vector2i(byte shr 4, byte and 15)
            },
            { buf, vec -> buf.writeByte(((vec.x and 15) shl 4) or (vec.y and 15)) }
        )

        val CODEC: Codec<BlockEntity> = Codec.of(
            CODEC_PACKED_XZ, { Vector2i(it.pos.x, it.pos.z) },
            Codec.SHORT, { it.pos.y.toShort() },
            Registries.BLOCK_ENTITY_TYPE.idCodec, { it.type },
            NBT.UNNAMED_TAG_CODEC, { it.data },
            { xz, y, type, data -> BlockEntity(Vector3i(xz.x, y.toInt(), xz.y), type, data) }
        )
        val LIST_CODEC: Codec<List<BlockEntity>> = CODEC.list()
    }


}