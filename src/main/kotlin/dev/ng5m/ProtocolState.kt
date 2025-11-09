package dev.ng5m

import dev.ng5m.packet.PacketHandler
import dev.ng5m.packet.PacketHandlerContext
import dev.ng5m.packet.common.CommonHandlers
import dev.ng5m.packet.common.PluginMessagePacket
import dev.ng5m.packet.common.s2c.DisconnectS2CPacket
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
import dev.ng5m.serialization.Packet
import dev.ng5m.util.NetworkFlow
import dev.ng5m.util.TriConsumer
import dev.ng5m.util.initClass
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.BiConsumer
import kotlin.math.max
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

            register(0x01, PluginMessagePacket::class)
            register(0x03, FinishConfigurationS2CPacket::class)
            register(0x07, RegistryDataS2CPacket::class)
            register(0x0D, UpdateTagsS2CPacket::class)
            register(0x0E, KnownPacksPacket::class)
        }


        val PLAY: ProtocolState = state {
//            strictErrorHandling(true)

            register(AcceptTeleportationC2SPacket::class).handler(PlayC2SHandlers::acceptTeleportation)
            register(0x07, ChatMessageC2SPacket::class).handler(PlayC2SHandlers::chatMessage)
            register(0x0B, ClientEndTickC2SPacket::class).excludeFromLogging()
            register(0x10, ContainerClickC2SPacket::class).handler(PlayC2SHandlers::containerClick)
            register(0x11, ContainerCloseC2SPacket::class).handler(PlayC2SHandlers::containerClose)
            register(0x14, PluginMessagePacket::class).immediateHandling().handler(CommonHandlers::pluginMessage)
            register(0x18, InteractC2SPacket::class).handler(PlayC2SHandlers::interact)
            register(0x1C, PlayerMoveC2SPacket.Pos::class).excludeFromLogging().handler(PlayC2SHandlers::movePos)
            register(0x1D, PlayerMoveC2SPacket.PosRot::class).excludeFromLogging().handler(PlayC2SHandlers::movePosRot)
            register(0x1E, PlayerMoveC2SPacket.Rot::class).excludeFromLogging().handler(PlayC2SHandlers::moveRot)
            register(0x1F, PlayerMoveC2SPacket.Status::class).excludeFromLogging().handler(PlayC2SHandlers::moveStatus)
            register(0x26, PlayerAbilitiesC2SPacket::class)
            register(0x27, PlayerActionC2SPacket::class).handler(PlayC2SHandlers::playerAction)
            register(0x28, PlayerCommandC2SPacket::class).handler(PlayC2SHandlers::playerCommand)
            register(0x29, PlayerInputC2SPacket::class).handler(PlayC2SHandlers::input)
            register(0x2A, PlayerLoadedC2SPacket::class).handler(PlayC2SHandlers::loaded)
            register(0x33, SetCarriedItemC2SPacket::class).handler(PlayC2SHandlers::setCarriedItem)
            register(0x36, SetCreativeModeSlotC2SPacket::class).excludeFromLogging().handler(PlayC2SHandlers::setCreativeModeSlot)
            register(0x3A, SwingArmC2SPacket::class).handler(PlayC2SHandlers::swingArm)
            register(0x3C, UseItemOnC2SPacket::class).handler(PlayC2SHandlers::useItemOn)

            switchFlow()

            register(0x01, SpawnEntityS2CPacket::class)
            register(0x03, AnimateS2CPacket::class)
            register(0x09, BlockUpdateS2CPacket::class)
            register(0x13, SetContainerContentsS2CPacket::class)
            register(0x15, SetContainerSlotS2CPacket::class).excludeFromLogging()
            register(0x19, PluginMessagePacket::class)
            register(0x1D, DisconnectS2CPacket::class)
            register(0x20, SyncEntityPositionS2CPacket::class)
            register(0x22, UnloadChunkS2CPacket::class).excludeFromLogging()
            register(0x23, GameEventS2CPacket::class)
            register(0x28, ChunkS2CPacket::class).excludeFromLogging()
            register(0x2A, LevelParticlesS2CPacket::class)
            register(0x2C, JoinS2CPacket::class)
            register(0x2F, MoveEntityPacket.Pos::class).excludeFromLogging()
            register(0x30, MoveEntityPacket.PosRot::class).excludeFromLogging()
            register(0x35, OpenScreenS2CPacket::class)
            register(0x3A, PlayerAbilitiesS2CPacket::class)
            register(0x3F, PlayerInfoRemoveS2CPacket::class)
            register(0x40, PlayerInfoUpdateS2CPacket::class).excludeFromLogging()
            register(0x42, PlayerPosS2CPacket::class)
            register(0x43, PlayerRotationS2CPacket::class)
            register(0x47, RemoveEntitiesS2CPacket::class)
            register(0x4D, RotateHeadS2CPacket::class)
            register(0x58, SetCenterChunkS2CPacket::class)
            register(0x5A, SetCursorItemS2CPacket::class)
            register(0x5D, SetEntityDataS2CPacket::class)
            register(0x63, SetHeldSlotS2CPacket::class)
            register(0x73, SystemChatS2CPacket::class)
            register(0x74, TabListS2CPacket::class).excludeFromLogging()
        }

    }

    private val requiresImmediateHandling: MutableSet<Class<out Packet>> = mutableSetOf()

    private val id2TypeClientbound: ObjectList<Class<out Packet>> = ObjectArrayList()
    private val type2IdClientbound: Object2IntMap<Class<out Packet>> = Object2IntOpenHashMap()
    private val id2TypeServerbound: ObjectList<Class<out Packet>> = ObjectArrayList()
    private val type2IdServerbound: Object2IntMap<Class<out Packet>> = Object2IntOpenHashMap()

    private val handlers: MutableMap<Class<out Packet>, PacketHandler<out Packet>> = mutableMapOf()
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

        val id2Type = (if (flow == NetworkFlow.CLIENTBOUND) id2TypeClientbound else id2TypeServerbound)
        id2Type.size(max(id + 1, id2Type.size))
        id2Type[id] = clazz
        (if (flow == NetworkFlow.CLIENTBOUND) type2IdClientbound else type2IdServerbound)[clazz] = id
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

    fun <T : Packet> handler0(handler: PacketHandler<T>): ProtocolState {
        ensureRegisteredClassExists()
        handlers[lastRegisteredClass!!] = handler

        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> handler(handler: BiConsumer<MinecraftConnection, T>): ProtocolState =
        this.handler0(PacketHandler { c, p -> handler.accept(c, p as T) })

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> handler(handler: TriConsumer<MinecraftConnection, T, PacketHandlerContext>): ProtocolState =
        this.handler0(PacketHandler { c, p ->
            val ctx = PacketHandlerContext(c, p)
            handler.accept(c, p as T, ctx)
            ctx.afterHandled()
        })

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
        val type = (if (flow == NetworkFlow.CLIENTBOUND) id2TypeClientbound else id2TypeServerbound).getOrNull(id)

        type ?: run {
            val message = String.format("Packet ID 0x%02x not registered in state $this", id)

            if (strictErrorHandling) throw RuntimeException(message)

            LOGGER.error(message)
            return null
        }

        return type
    }

    fun <T : Packet> idForType(flow: NetworkFlow, clazz: Class<T>): Int {
        val v = (if (flow == NetworkFlow.CLIENTBOUND) type2IdClientbound else type2IdServerbound).getOrDefault(clazz, -1)
        if (v == -1) {
            LOGGER.error("Type ${clazz.simpleName} not registered in flow $flow")
        }

        return v
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> handlerFor(clazz: Class<T>): PacketHandler<Packet>? {
        return (handlers[clazz] ?: return null) as PacketHandler<Packet>
    }



    override fun toString(): String {
        return "ProtocolState(C2S=$id2TypeServerbound, S2C=$id2TypeClientbound)"
    }

}