package dev.ng5m.registry

import com.google.gson.annotations.SerializedName
import dev.ng5m.serialization.Codec
import dev.ng5m.util.nullable
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component

class PaintingVariant(
    val width: Int,
    val height: Int,
    assetId: Key,
    val title: Component?,
    val author: Component?
) {
    companion object {
        val CODEC: Codec<PaintingVariant> = Codec.of(
            Codec.INTEGER, { it.width },
            Codec.INTEGER, { it.height },
            Codec.KEY, { Key.key(it.assetId) },
            Codec.TEXT_COMPONENT.nullable(), { it.title },
            Codec.TEXT_COMPONENT.nullable(), { it.author },
            ::PaintingVariant
        )
    }

    @field:SerializedName("asset_id")
    val assetId: String = assetId.asString()

}