package dev.ng5m.packet.play.s2c

import dev.ng5m.player.GameMode
import dev.ng5m.player.Identity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.Property
import dev.ng5m.util.getIf
import dev.ng5m.util.nullable
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.kyori.adventure.text.Component
import java.util.EnumSet
import java.util.Optional
import java.util.UUID

data class PlayerInfoUpdateS2CPacket(val actions: EnumSet<Action>, val entries: List<Entry>) : Packet {
    companion object {
        private val ACTION_SET_CODEC: Codec<EnumSet<Action>> = Codec.enumSet(Action::class.java)

        val CODEC: Codec<PlayerInfoUpdateS2CPacket> = Codec.of(
            { buf ->
                val actions = ACTION_SET_CODEC.read(buf)
                val entries = mutableListOf<Entry>()
                for (i in 0 until Codec.VARINT.read(buf)) {
                    entries.add(Entry.readEntry(buf, actions))
                }

                PlayerInfoUpdateS2CPacket(actions, entries)
            },
            { buf, packet ->
                ACTION_SET_CODEC.write(buf, packet.actions)

                Codec.VARINT.write(buf, packet.entries.size)
                for (entry in packet.entries) {
                    Entry.writeEntry(buf, entry)
                }
            }
        ).forType(PlayerInfoUpdateS2CPacket::class.java)


        fun addPlayer(uuid: UUID, identity: Identity): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.ADD_PLAYER), listOf(Entry(uuid, identity)))
        fun initializeChat(uuid: UUID, chatData: ChatData?): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.INITIALIZE_CHAT), listOf(Entry(uuid, chatData = Optional.ofNullable(chatData))))
        fun updateGameMode(uuid: UUID, gameMode: GameMode): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_GAME_MODE), listOf(Entry(uuid, gameMode = gameMode)))
        fun updateListed(uuid: UUID, listed: Boolean): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_LISTED), listOf(Entry(uuid, listed = listed)))
        fun updateLatency(uuid: UUID, pingMs: Int): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_LATENCY), listOf(Entry(uuid, pingMs = pingMs)))
        fun updateDisplayName(uuid: UUID, displayName: Component?): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_DISPLAY_NAME), listOf(Entry(uuid, displayName = Optional.ofNullable(displayName))))
        fun updateListPriority(uuid: UUID, listPriority: Int): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_LIST_PRIORITY), listOf(Entry(uuid, listPriority = listPriority)))
        fun updateOuterLayer(uuid: UUID, outerLayer: Boolean): PlayerInfoUpdateS2CPacket =
            PlayerInfoUpdateS2CPacket(EnumSet.of(Action.UPDATE_OUTER_LAYER), listOf(Entry(uuid, outerLayer = outerLayer)))
    }

    data class Entry(
        val uuid: UUID,
        val identity: Identity? = null,
        val chatData: Optional<ChatData>? = null,
        val gameMode: GameMode? = null,
        val listed: Boolean? = null,
        val pingMs: Int? = null,
        val displayName: Optional<Component>? = null,
        val listPriority: Int? = null,
        val outerLayer: Boolean? = null
    ) {
        companion object {
            fun readEntry(buf: ByteBuf, actions: EnumSet<Action>): Entry {
                val uuid = Codec.UUID.read(buf)
                return Entry(
                    uuid,
                    identity = getIf(Action.ADD_PLAYER in actions) {
                        Identity(
                            Codec.STRING.read(buf), uuid, Property.LIST_CODEC.read(buf)
                        )
                    },
                    chatData = getIf(Action.INITIALIZE_CHAT in actions) { ChatData.CODEC.prefixedOptional().read(buf) },
                    gameMode = getIf(Action.UPDATE_GAME_MODE in actions) { GameMode.entries[Codec.VARINT.read(buf)] },
                    listed = getIf(Action.UPDATE_LISTED in actions) { buf.readBoolean() },
                    pingMs = getIf(Action.UPDATE_LATENCY in actions) { Codec.VARINT.read(buf) },
                    displayName = getIf(Action.UPDATE_DISPLAY_NAME in actions) {
                        Codec.TEXT_COMPONENT.prefixedOptional().read(buf)
                    },
                    listPriority = getIf(Action.UPDATE_LIST_PRIORITY in actions) { Codec.VARINT.read(buf) },
                    outerLayer = getIf(Action.UPDATE_OUTER_LAYER in actions) { buf.readBoolean() }
                )
            }

            fun writeEntry(buf: ByteBuf, entry: Entry) {
                Codec.UUID.write(buf, entry.uuid)

                entry.identity?.let {
                    Codec.STRING.write(buf, it.username)
                    Property.LIST_CODEC.write(buf, it.properties)
                }
                entry.chatData?.let { ChatData.CODEC.prefixedOptional().write(buf, it) }
                entry.gameMode?.let { Codec.VARINT.write(buf, it.ordinal) }
                entry.listed?.let { buf.writeBoolean(it) }
                entry.pingMs?.let { Codec.VARINT.write(buf, it) }
                entry.displayName?.let { Codec.TEXT_COMPONENT.prefixedOptional().write(buf, it) }
                entry.listPriority?.let { Codec.VARINT.write(buf, it) }
                entry.outerLayer?.let { buf.writeBoolean(it) }
            }

        }
    }

    data class ChatData(
        val chatSessionId: UUID,
        val publicKeyExpiryTime: Long,
        val encodedPublicKey: ByteArray,
        val publicKeySignature: ByteArray
    ) {
        companion object {
            val CODEC: Codec<ChatData> = Codec.of(
                Codec.UUID, { it.chatSessionId },
                Codec.LONG, { it.publicKeyExpiryTime },
                Codec.BYTE_ARRAY, { it.encodedPublicKey },
                Codec.BYTE_ARRAY, { it.publicKeySignature },
                ::ChatData
            )
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ChatData

            if (publicKeyExpiryTime != other.publicKeyExpiryTime) return false
            if (chatSessionId != other.chatSessionId) return false
            if (!encodedPublicKey.contentEquals(other.encodedPublicKey)) return false
            if (!publicKeySignature.contentEquals(other.publicKeySignature)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = publicKeyExpiryTime.hashCode()
            result = 31 * result + chatSessionId.hashCode()
            result = 31 * result + encodedPublicKey.contentHashCode()
            result = 31 * result + publicKeySignature.contentHashCode()
            return result
        }

    }

    enum class Action {
        ADD_PLAYER,
        INITIALIZE_CHAT,
        UPDATE_GAME_MODE,
        UPDATE_LISTED,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        UPDATE_LIST_PRIORITY,
        UPDATE_OUTER_LAYER;
    }

}