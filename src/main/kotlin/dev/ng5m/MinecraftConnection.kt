package dev.ng5m

import dev.ng5m.event.EventManager
import dev.ng5m.event.impl.S2CPacketEvent
import dev.ng5m.mcio.PacketCompression
import dev.ng5m.packet.PacketHandler
import dev.ng5m.packet.common.KeepAlivePacket
import dev.ng5m.packet.common.s2c.DisconnectS2CPacket
import dev.ng5m.packet.configuration.KnownPacksPacket
import dev.ng5m.packet.configuration.s2c.FinishConfigurationS2CPacket
import dev.ng5m.packet.configuration.s2c.RegistryDataS2CPacket
import dev.ng5m.packet.configuration.s2c.UpdateTagsS2CPacket
import dev.ng5m.packet.login.s2c.SetCompressionS2CPacket
import dev.ng5m.packet.play.s2c.PlayerInfoRemoveS2CPacket
import dev.ng5m.packet.play.s2c.PlayerPosS2CPacket
import dev.ng5m.player.Player
import dev.ng5m.registry.Registry
import dev.ng5m.serialization.Packet
import dev.ng5m.util.PacketSendContext
import kotlinx.coroutines.CompletableDeferred
import net.kyori.adventure.text.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.LinkedList
import java.util.Queue
import java.util.UUID

abstract class MinecraftConnection : Ticking {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MinecraftConnection::class.java)
    }

    lateinit var player: Player

    protected val queuedPackets: Queue<PacketSendContext> = LinkedList()
    protected val receivedPackets: Queue<Packet> = LinkedList()

    var protocolState: ProtocolState = ProtocolState.HANDSHAKE;

    val awaitedPackResponses = mutableMapOf<UUID, CompletableDeferred<Unit>>()

    var compression: PacketCompression? = null

    var disconnectMessage: Component? = null

    var open = true

    var waitingForKeepAlive = false
    var keepAliveId = -1L
    var lastKeepAliveTime = -1L

    fun sendPacket(packet: Packet): PacketSendContext {
        val ctx = PacketSendContext(packet)

//        if (protocolState.shouldHandleImmediately(packet::class.java)) {
//            internalSend(ctx)
//            return ctx
//        }

        queuedPackets.add(ctx)

        return ctx
    }

    fun receive(packet: Packet) {
        receivedPackets.add(packet)
    }

    fun enableCompression(threshold: Int) {
        sendPacket(SetCompressionS2CPacket(threshold)).onFinish {
            compression = PacketCompression(threshold)
        }
    }

    fun disconnect(reason: Component) {
        sendPacket(DisconnectS2CPacket(reason)).onFinish {
            removePlayer()
            open = false
        }
    }

    private fun expectConfiguration() {
        require(protocolState == ProtocolState.CONFIGURATION) { "not in CONFIGURATION" }
    }

    fun updateKnownPacks() {
        expectConfiguration()

        sendPacket(KnownPacksPacket(MinecraftServer.getInstance().knownPacks))
    }


    fun synchronizeRegistries(): PacketSendContext {
        expectConfiguration()

        val registries = Registry.getAllRegistries().filter { it.synced }
        require(registries.isNotEmpty()) { "Registries were not properly initialized" }

        var ctx: PacketSendContext? = null

        for (registry in registries) {
            ctx = sendPacket(RegistryDataS2CPacket(registry))
        }

        return ctx!!
    }

    fun updateTags(): PacketSendContext {
        expectConfiguration()

        return sendPacket(UpdateTagsS2CPacket.ofRegistries())
    }

    fun finishConfiguration() {
        expectConfiguration()

        sendPacket(FinishConfigurationS2CPacket)

        // await ack
    }

    internal var syncingPosition = false

    fun synchronizePosition(callback: () -> Unit) {
        syncingPosition = true
        sendPacket(PlayerPosS2CPacket(player, PlayerPosS2CPacket.Flags.ABSOLUTE, callback))
    }

    internal fun internalReceive(packet: Packet) {
        if (closed()) return

        val handler: PacketHandler<Packet> =
            protocolState.handlerFor(packet::class.java) ?: return
        handler.handle(this, packet)
    }

    internal fun removePlayer() {
        close()
        if (::player.isInitialized) {
            MinecraftServer.getInstance().removeConnection(this)
            if (player.isSpawned()) {
                player.getWorld().removeEntity(player)
                player.getOtherPlayers().forEach {
                    it.connection.sendPacket(PlayerInfoRemoveS2CPacket(player.getIdentity().getAdequateUUID()))
                }
            }
        }
    }

    protected abstract fun internalSend(ctx: PacketSendContext)

    abstract fun close()

    abstract fun closed(): Boolean

    override fun tick() {
        while (receivedPackets.isNotEmpty()) {
            internalReceive(receivedPackets.poll())
        }

        if (protocolState == ProtocolState.PLAY)
            keepAlive()

        while (queuedPackets.isNotEmpty()) {
            if (closed()) {
                queuedPackets.clear()
                break
            }

            val ctx = queuedPackets.poll()
            internalSend(ctx)
            EventManager.fire(S2CPacketEvent(this, ctx.packet))
        }
    }

    private fun keepAlive() {
        val time = System.currentTimeMillis()
        if (time - lastKeepAliveTime >= 15000L) {
            if (waitingForKeepAlive) {
                disconnect(Component.text("Slimed out"))
            } else {
                waitingForKeepAlive = true
                lastKeepAliveTime = time
                keepAliveId = time
                sendPacket(KeepAlivePacket(keepAliveId))
            }
        }
    }
}