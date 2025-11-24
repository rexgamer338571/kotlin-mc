package dev.ng5m.packet.common.c2s

import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.forType
import java.util.UUID

data class ResourcePackResponseC2SPacket(
    val uuid: UUID,
    val status: Status
) : Packet {
    companion object {
        val CODEC: Codec<ResourcePackResponseC2SPacket> = Codec.of(
            Codec.UUID, { it.uuid },
            Codec.ofEnum(Status::class.java), { it.status },
            ::ResourcePackResponseC2SPacket
        ).forType(ResourcePackResponseC2SPacket::class)
    }

    enum class Status {
        SUCCESS,
        DECLINED,
        FAILED_TO_DOWNLOAD,
        ACCEPTED,
        DOWNLOADED,
        INVALID_URL,
        FAILED_TO_RELOAD,
        DISCARDED
    }
}