package dev.ng5m.pack

import dev.ng5m.MinecraftConnection
import dev.ng5m.packet.common.s2c.ResourcePackPushS2CPacket
import kotlinx.coroutines.CompletableDeferred
import java.util.UUID

object ResourcePackManager {
    private val map = mutableMapOf<UUID, ResourcePack>()
    private val mapOfHash = mutableMapOf<String, UUID>()

    fun addPack(pack: ResourcePack) {
        map[pack.uuid] = pack
        mapOfHash[pack.hash] = pack.uuid
    }

    fun getPack(uuid: UUID): ResourcePack? = map[uuid]
    fun getPack(hash: String): ResourcePack? = map[mapOfHash[hash]]
    fun packs(): Collection<ResourcePack> = map.values

    fun send(connection: MinecraftConnection) {
        for ((uuid, pack) in map) {
            val deferred = CompletableDeferred<Unit>()
            connection.awaitedPackResponses[uuid] = deferred

            connection.sendPacket(ResourcePackPushS2CPacket(
                uuid, pack.url.toString(), pack.hash, pack.forced
            ))
        }
    }

}