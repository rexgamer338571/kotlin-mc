package dev.ng5m.packet.play.s2c

import dev.ng5m.packet.play.s2c.PlayerInfoUpdateS2CPacket.PlayerAction.AddPlayer.Companion
import dev.ng5m.player.GameMode
import dev.ng5m.player.Player
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.Property
import dev.ng5m.util.U4
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import java.util.BitSet
import java.util.EnumSet
import java.util.Optional
import java.util.UUID

data class PlayerInfoUpdateS2CPacket(val actions: EnumSet<PlayerAction.Type>, val entries: Map<UUID, Set<PlayerAction>>) : Packet {
    companion object {
        val CODEC: Codec<PlayerInfoUpdateS2CPacket> = Codec.of(
            { buf ->
                val enumSet = BitSet.valueOf(ByteArray(1) { buf.readByte() })
                val length = Codec.VARINT.read(buf)

                val ret = mutableMapOf<UUID, Set<PlayerAction>>()

                for (i in 0 until length) {
                    val actions = mutableSetOf<PlayerAction>()
                    val uuid = Codec.UUID.read(buf)

                    for (type in PlayerAction.Type.entries) {
                        if (!enumSet.get(type.ordinal)) continue

                        actions.add(type.codec.read(buf))
                    }

                    ret[uuid] = actions
                }

                return@of PlayerInfoUpdateS2CPacket(EnumSet.noneOf(PlayerAction.Type::class.java), ret)
            },
            { buf, packet ->
                Codec.enumSet(PlayerAction.Type::class.java).write(buf, packet.actions)

                Codec.VARINT.write(buf, packet.entries.size)
                for (entry in packet.entries) {
                    val value = entry.value.toList()
                    Codec.UUID.write(buf, entry.key)

                    var index = 0
                    for (action in packet.actions) {
                        @Suppress("UNCHECKED_CAST")
                        (action.codec as Codec<PlayerAction>).write(buf, value[index++])
                    }
                }
            }
        ).forType(PlayerInfoUpdateS2CPacket::class.java)
    }

    sealed interface PlayerAction {
        fun write(buf: ByteBuf)
        fun type(): Type

        enum class Type(val codec: Codec<out PlayerAction>) {
            ADD_PLAYER(AddPlayer.CODEC),
            INITIALIZE_CHAT(InitializeChat.CODEC),
            UPDATE_GAME_MODE(UpdateGameMode.CODEC),
            UPDATE_LISTED(UpdateListed.CODEC),
            UPDATE_LATENCY(UpdateLatency.CODEC),
            UPDATE_DISPLAY_NAME(UpdateDisplayName.CODEC),
            UPDATE_LIST_PRIORITY(UpdateListPriority.CODEC),
            UPDATE_OUTER_LAYER(UpdateOuterLayer.CODEC);

            companion object {
                val ENUM_SET_CODEC: Codec<EnumSet<Type>> = Codec.enumSet(Type::class.java)
            }
        }

        data class AddPlayer(val name: String, val properties: List<Property>) : PlayerAction {
            companion object {
                val CODEC: Codec<AddPlayer> = Codec.of(
                    Codec.STRING, { it.name },
                    Property.CODEC.list(), { it.properties },
                    ::AddPlayer
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.ADD_PLAYER
        }

        data class InitializeChat(val union: Optional<U4<UUID, Long, ByteArray, ByteArray>>) : PlayerAction {
            companion object {
                val CODEC: Codec<InitializeChat> = Codec.of(
                    U4.codec(
                        Codec.UUID,
                        Codec.LONG,
                        Codec.BYTE_ARRAY,
                        Codec.BYTE_ARRAY
                    ).prefixedOptional(), { it.union },
                    ::InitializeChat
                )
            }

            constructor(
                chatSessionID: UUID, pubKeyExpiryTime: Long,
                pubKey: ByteArray, pubKeySignature: ByteArray
            ) : this(Optional.of(U4(chatSessionID, pubKeyExpiryTime, pubKey, pubKeySignature)))

            constructor() : this(Optional.empty())

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.INITIALIZE_CHAT

        }

        data class UpdateGameMode(val gameMode: GameMode) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateGameMode> = Codec.of(
                    Codec.ofEnum(GameMode::class.java), { it.gameMode }, ::UpdateGameMode
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_GAME_MODE
        }

        data class UpdateListed(val listed: Boolean) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateListed> = Codec.of(
                    Codec.BOOLEAN, { it.listed }, ::UpdateListed
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_LISTED
        }

        data class UpdateLatency(val latencyMS: Int) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateLatency> = Codec.of(
                    Codec.VARINT, { it.latencyMS }, ::UpdateLatency
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_LATENCY
        }

        data class UpdateDisplayName(val displayName: Optional<Component>) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateDisplayName> = Codec.of(
                    Codec.TEXT_COMPONENT.prefixedOptional(), { it.displayName }, ::UpdateDisplayName
                )
            }

            constructor() : this(Optional.empty())
            constructor(displayName: Component) : this(Optional.of(displayName))

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_DISPLAY_NAME
        }

        data class UpdateListPriority(val priority: Int) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateListPriority> = Codec.of(
                    Codec.VARINT, { it.priority }, ::UpdateListPriority
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_LIST_PRIORITY
        }

        data class UpdateOuterLayer(val shown: Boolean) : PlayerAction {
            companion object {
                val CODEC: Codec<UpdateOuterLayer> = Codec.of(
                    Codec.BOOLEAN, { it.shown }, ::UpdateOuterLayer
                )
            }

            override fun write(buf: ByteBuf) = CODEC.write(buf, this)
            override fun type(): Type = Type.UPDATE_OUTER_LAYER
        }
    }

}