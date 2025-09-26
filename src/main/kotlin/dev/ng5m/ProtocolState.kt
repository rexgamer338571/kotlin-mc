package dev.ng5m

import dev.ng5m.packet.common.CommonHandlers
import dev.ng5m.packet.common.PluginMessagePacket
import dev.ng5m.packet.configuration.KnownPacksPacket
import dev.ng5m.packet.configuration.c2s.AckFinishConfigurationC2SPacket
import dev.ng5m.packet.configuration.c2s.ClientInformationC2SPacket
import dev.ng5m.packet.configuration.c2s.ConfigurationC2SHandlers
import dev.ng5m.packet.configuration.s2c.FinishConfigurationS2CPacket
import dev.ng5m.packet.configuration.s2c.RegistryDataS2CPacket
import dev.ng5m.packet.configuration.s2c.UpdateTagsS2CPacket
import dev.ng5m.packet.handshake.c2s.HandshakeC2SHandlers
import dev.ng5m.packet.handshake.c2s.HandshakeC2SPacket
import dev.ng5m.packet.login.c2s.HelloC2SPacket
import dev.ng5m.packet.login.c2s.LoginAckC2SPacket
import dev.ng5m.packet.login.c2s.LoginC2SHandlers
import dev.ng5m.packet.login.s2c.LoginSuccessS2CPacket
import dev.ng5m.packet.play.c2s.*
import dev.ng5m.packet.play.s2c.*
import dev.ng5m.packet.status.c2s.PingRequestC2SPacket
import dev.ng5m.packet.status.c2s.StatusC2SHandlers
import dev.ng5m.packet.status.c2s.StatusRequestC2SPacket
import dev.ng5m.packet.status.s2c.PongResponseS2CPacket
import dev.ng5m.packet.status.s2c.StatusResponseS2CPacket
import dev.ng5m.serialization.DoubleMap
import dev.ng5m.serialization.Packet
import dev.ng5m.util.NetworkFlow
import dev.ng5m.util.initClass
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.BiConsumer
import kotlin.reflect.KClass

class ProtocolState {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ProtocolState::class.java)

        private fun state(init: ProtocolState.() -> Unit): ProtocolState {
            val state = ProtocolState()
            state.init()
            return state
        }

        val HANDSHAKE: ProtocolState = state {
            strictErrorHandling(true)

            register(0x00, HandshakeC2SPacket::class).immediateHandling().handler(HandshakeC2SHandlers::handshake)
        }

        val STATUS: ProtocolState = state {
            strictErrorHandling(true)

            register(StatusRequestC2SPacket::class).handler(StatusC2SHandlers::statusRequest)
            register(PingRequestC2SPacket::class).handler(StatusC2SHandlers::pingRequest)

            switchFlow()

            register(StatusResponseS2CPacket::class)
            register(PongResponseS2CPacket::class)
        }


        val LOGIN: ProtocolState = state {
            strictErrorHandling(true)

            register(HelloC2SPacket::class).handler(LoginC2SHandlers::hello)
            register(0x03, LoginAckC2SPacket::class).immediateHandling().handler(LoginC2SHandlers::loginAck)

            switchFlow()

            register(0x02, LoginSuccessS2CPacket::class)
        }


        val CONFIGURATION: ProtocolState = state {
            strictErrorHandling(true)

            register(ClientInformationC2SPacket::class).handler(ConfigurationC2SHandlers::clientInformation)
            register(0x02, PluginMessagePacket::class).immediateHandling().handler(CommonHandlers::pluginMessage)
            register(0x03, AckFinishConfigurationC2SPacket::class.java).immediateHandling().handler(ConfigurationC2SHandlers::ackFinishConfiguration)
            register(0x07, KnownPacksPacket::class).handler(ConfigurationC2SHandlers::knownPacks)

            switchFlow()

            register(0x01, PluginMessagePacket::class).immediateHandling().handler(CommonHandlers::pluginMessage)
            register(0x03, FinishConfigurationS2CPacket::class).immediateHandling()
            register(0x07, RegistryDataS2CPacket::class)
            register(0x0D, UpdateTagsS2CPacket::class)
            register(0x0E, KnownPacksPacket::class)
        }


        val PLAY: ProtocolState = state {
//            strictErrorHandling(true)

            register(AcceptTeleportationC2SPacket::class).handler(PlayC2SHandlers::acceptTeleportation)
            register(0x0B, ClientEndTickC2SPacket::class).excludeFromLogging()
            register(0x14, PluginMessagePacket::class).immediateHandling().handler(CommonHandlers::pluginMessage)
            register(0x1C, PlayerMoveC2SPacket.Pos::class)/*.excludeFromLogging()*/.handler(PlayC2SHandlers::movePos)
            register(0x1D, PlayerMoveC2SPacket.PosRot::class)/*.excludeFromLogging()*/.handler(PlayC2SHandlers::movePosRot)
            register(0x1E, PlayerMoveC2SPacket.Rot::class).excludeFromLogging().handler(PlayC2SHandlers::moveRot)
            register(0x1F, PlayerMoveC2SPacket.Status::class).excludeFromLogging().handler(PlayC2SHandlers::moveStatus)
            register(0x26, PlayerAbilitiesC2SPacket::class)
            register(0x28, PlayerCommandC2SPacket::class).handler(PlayC2SHandlers::playerCommand)
            register(0x29, PlayerInputC2SPacket::class).handler(PlayC2SHandlers::input)
            register(0x2A, PlayerLoadedC2SPacket::class).handler(PlayC2SHandlers::loaded)
            register(0x36, SetCreativeModeSlotC2SPacket::class).handler(PlayC2SHandlers::setCreativeModeSlot)
            register(0x3A, SwingArmC2SPacket::class).handler(PlayC2SHandlers::swingArm)

            switchFlow()

            register(0x01, SpawnEntityS2CPacket::class)
            register(0x03, AnimateS2CPacket::class)
            register(0x13, SetContainerContentsS2CPacket::class)
            register(0x15, SetContainerSlotS2CPacket::class)
            register(0x20, SyncEntityPositionS2CPacket::class)
            register(0x22, UnloadChunkS2CPacket::class)
            register(0x23, GameEventS2CPacket::class)
            register(0x28, ChunkS2CPacket::class)
            register(0x2C, JoinS2CPacket::class)
            register(0x2F, MoveEntityPacket.Pos::class)
            register(0x30, MoveEntityPacket.PosRot::class)
            register(0x3A, PlayerAbilitiesS2CPacket::class)
            register(0x40, PlayerInfoUpdateS2CPacket::class)
            register(0x42, PlayerPosS2CPacket::class)
            register(0x47, RemoveEntitiesS2CPacket::class)
            register(0x58, SetCenterChunkS2CPacket::class)
        }

    }

    private val requiresImmediateHandling: MutableSet<Class<out Packet>> = mutableSetOf()
    private val packetIdToTypeDoubleMap: Map<NetworkFlow, DoubleMap<MutableMap<Any, Any>, Int, Class<out Packet>>> =
        mapOf(
            Pair(NetworkFlow.CLIENTBOUND, DoubleMap(::HashMap)),
            Pair(NetworkFlow.SERVERBOUND, DoubleMap(::HashMap))
        )
    private val handlers: MutableMap<Class<out Packet>, BiConsumer<MinecraftConnection, out Packet>> = mutableMapOf()
    private var lastRegisteredClass: Class<out Packet>? = null
    private var strictErrorHandling: Boolean = false
    private var flow: NetworkFlow = NetworkFlow.SERVERBOUND
    private val loggingExclusions: MutableSet<Class<out Packet>> = mutableSetOf()

    private var id = 0

    fun <T : Packet> register(id: Int, clazz: KClass<T>): ProtocolState {
        return register(id, clazz.java)
    }

    fun <T : Packet> register(id: Int, clazz: Class<T>): ProtocolState {
        initClass(clazz)

        packetIdToTypeDoubleMap[flow]!!.put(id, clazz)
        lastRegisteredClass = clazz

        return this
    }

    fun <T : Packet> register(clazz: KClass<T>): ProtocolState {
        return register(id++, clazz)
    }

    private fun ensureRegisteredClassExists() {
        lastRegisteredClass ?: throw IllegalStateException("No class has been registered yet")
    }

    fun immediateHandling(): ProtocolState {
        ensureRegisteredClassExists()
        requiresImmediateHandling.add(lastRegisteredClass!!)

        return this
    }

    fun excludeFromLogging(): ProtocolState {
        ensureRegisteredClassExists()
        loggingExclusions.add(lastRegisteredClass!!)

        return this
    }

    fun <T : Packet> shouldLog(clazz: Class<T>): Boolean {
        return !loggingExclusions.contains(clazz)
    }

    fun <T : Packet> shouldHandleImmediately(clazz: Class<T>): Boolean {
        return requiresImmediateHandling.contains(clazz)
    }

    fun <T : Packet> handler(handler: BiConsumer<MinecraftConnection, T>): ProtocolState {
        ensureRegisteredClassExists()
        handlers[lastRegisteredClass!!] = handler

        return this
    }

    fun strictErrorHandling(value: Boolean): ProtocolState {
        this.strictErrorHandling = value

        return this
    }

    fun flow(flow: NetworkFlow): ProtocolState {
        this.flow = flow

        return this
    }

    fun switchFlow(): ProtocolState {
        this.flow = if (flow == NetworkFlow.CLIENTBOUND) NetworkFlow.SERVERBOUND else NetworkFlow.CLIENTBOUND
        id = 0

        return this
    }

    fun typeForId(flow: NetworkFlow, id: Int): Class<out Packet>? {
        val type = packetIdToTypeDoubleMap[flow]!!.getA(id)

        type ?: run {
            val message = String.format("Packet ID 0x%02x not registered in state $this", id)

            if (strictErrorHandling) throw RuntimeException(message)

            LOGGER.error(message)
            return null
        }

        return type
    }

    fun <T : Packet> idForType(flow: NetworkFlow, clazz: Class<T>): Int {
        return packetIdToTypeDoubleMap[flow]!!.getB(clazz)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> handlerFor(clazz: Class<T>): BiConsumer<MinecraftConnection, Packet>? {
        return (handlers[clazz] ?: return null) as BiConsumer<MinecraftConnection, Packet>
    }



    override fun toString(): String {
        return "ProtocolState($packetIdToTypeDoubleMap)"
    }

}