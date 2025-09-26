package dev.ng5m.player

import dev.ng5m.serialization.Codec

data class SkinParts(
    val cape: Boolean,
    val jacket: Boolean,
    val leftSleeve: Boolean,
    val rightSleeve: Boolean,
    val leftPantsLeg: Boolean,
    val rightPantsLeg: Boolean,
    val hat: Boolean
) {
    companion object {
        val CODEC: Codec<SkinParts> = Codec.BYTE
            .xmap(SkinParts::unpack, SkinParts::pack)
            .forType(SkinParts::class.java)

        fun unpack(b: Byte): SkinParts {
            return SkinParts(
                (b.toInt() and 0x01) != 0,
                (b.toInt() and 0x02) != 0,
                (b.toInt() and 0x04) != 0,
                (b.toInt() and 0x08) != 0,
                (b.toInt() and 0x10) != 0,
                (b.toInt() and 0x20) != 0,
                (b.toInt() and 0x40) != 0
            )
        }
    }

    constructor() : this(
        false, false, false, false,
        false, false, false,
    )

    fun pack(): Byte {
        var b: Byte = 0
        if (cape) b = 0x01.toByte()
        if (jacket) b = (b.toInt() or 0x02).toByte()
        if (leftSleeve) b = (b.toInt() or 0x04).toByte()
        if (rightSleeve) b = (b.toInt() or 0x08).toByte()
        if (leftPantsLeg) b = (b.toInt() or 0x10).toByte()
        if (rightPantsLeg) b = (b.toInt() or 0x20).toByte()
        if (hat) b = (b.toInt() or 0x40).toByte()
        return b
    }
}