package dev.ng5m.packet.play.s2c

import dev.ng5m.block.BlockState
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.math.Vector3i

data class BlockUpdateS2CPacket(
    val blockPos: Vector3i,
    val blockState: Int
) : Packet {
    companion object {
        val CODEC: Codec<BlockUpdateS2CPacket> = Codec.of(
            Vector3i.POSITION, { it.blockPos },
            Codec.VARINT, { it.blockState },
            ::BlockUpdateS2CPacket
        ).forType(BlockUpdateS2CPacket::class.java)
    }

    constructor(blockPos: Vector3i, blockState: BlockState) : this(
        blockPos, BlockState.stateManager.idBy(blockState)
    )
}