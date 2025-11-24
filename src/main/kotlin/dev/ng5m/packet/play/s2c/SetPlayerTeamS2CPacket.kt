package dev.ng5m.packet.play.s2c

import dev.ng5m.Team
import dev.ng5m.entity.Entity
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.DecoderException
import dev.ng5m.serialization.Packet
import dev.ng5m.serialization.annotation.BitMask
import net.kyori.adventure.text.Component

data class SetPlayerTeamS2CPacket(
    val teamName: String,
    val method: Method
) : Packet {
    companion object {
        val CODEC: Codec<SetPlayerTeamS2CPacket> = Codec.of(
            Codec.STRING, { it.teamName },
            Method.CODEC, { it.method },
            ::SetPlayerTeamS2CPacket
        ).forType(SetPlayerTeamS2CPacket::class.java)

        fun createTeam(team: Team): SetPlayerTeamS2CPacket =
            SetPlayerTeamS2CPacket(
                team.name, CreateTeam(
                    team.displayName,
                    FriendlyFlags(team.friendlyFire, team.canSeeFriendlyInvisible),
                    team.nameTagVisibility, team.collisionRule, team.color,
                    team.prefix, team.suffix, team.entities.map {
                        it.toString()
                    }
                )
            )

        fun removeTeam(team: Team): SetPlayerTeamS2CPacket =
            SetPlayerTeamS2CPacket(team.name, RemoveTeam)

        fun updateTeamInfo(team: Team): SetPlayerTeamS2CPacket =
            SetPlayerTeamS2CPacket(
                team.name, UpdateTeamInfo(
                    team.displayName,
                    FriendlyFlags(team.friendlyFire, team.canSeeFriendlyInvisible),
                    team.nameTagVisibility, team.collisionRule, team.color,
                    team.prefix, team.suffix
                )
            )

        fun addEntities(team: Team, vararg entities: Entity): SetPlayerTeamS2CPacket =
            SetPlayerTeamS2CPacket(team.name, AddEntities(entities.map { it.uuid.toString() }))

        fun removeEntities(team: Team, vararg entities: Entity): SetPlayerTeamS2CPacket =
            SetPlayerTeamS2CPacket(team.name, RemoveEntities(entities.map { it.uuid.toString() }))

    }

    data class FriendlyFlags(
        @field:BitMask(0x01) val friendlyFire: Boolean = false,
        @field:BitMask(0x02) val canSeeFriendlyInvisible: Boolean = false
    )

    sealed interface Method {
        companion object {
            @Suppress("UNCHECKED_CAST")
            val CODEC: Codec<Method> = Codec.of(
                { buf ->
                    when (buf.readByte().toInt()) {
                        0 -> CreateTeam.CODEC
                        1 -> RemoveTeam.CODEC
                        2 -> UpdateTeamInfo.CODEC
                        3 -> AddEntities.CODEC
                        4 -> RemoveTeam.CODEC

                        else -> throw DecoderException("Invalid method")
                    }.read(buf)
                },
                { buf, m ->
                    buf.writeByte((when (m) {
                        is CreateTeam -> 0
                        is RemoveTeam -> 1
                        is UpdateTeamInfo -> 2
                        is AddEntities -> 3
                        is RemoveEntities -> 4
                    }))
                    (when (m) {
                        is CreateTeam -> CreateTeam.CODEC
                        is RemoveTeam -> RemoveTeam.CODEC
                        is UpdateTeamInfo -> UpdateTeamInfo.CODEC
                        is AddEntities -> AddEntities.CODEC
                        is RemoveEntities -> RemoveEntities.CODEC
                    } as Codec<Method>).write(buf, m)
                }
            )
        }
    }

    data class CreateTeam(
        val teamDisplayName: Component,
        val friendlyFlags: FriendlyFlags,
        val nameTagVisibility: String,
        val collisionRule: String,
        val teamColor: TeamDecoration,
        val teamPrefix: Component,
        val teamSuffix: Component,
        val entities: List<String>
    ) : Method {
        companion object {
            val CODEC: Codec<CreateTeam> = Codec.of(
                Codec.TEXT_COMPONENT, { it.teamDisplayName },
                Codec.bitField(FriendlyFlags::class.java) { FriendlyFlags() }, { it.friendlyFlags },
                Codec.STRING, { it.nameTagVisibility },
                Codec.STRING, { it.collisionRule },
                Codec.ofEnum(TeamDecoration::class.java), { it.teamColor },
                Codec.TEXT_COMPONENT, { it.teamPrefix },
                Codec.TEXT_COMPONENT, { it.teamSuffix },
                Codec.STRING.list(), { it.entities },
                ::CreateTeam
            )
        }
    }

    data object RemoveTeam : Method {
        val CODEC: Codec<RemoveTeam> = Codec.empty { RemoveTeam }
    }

    data class UpdateTeamInfo(
        val teamDisplayName: Component,
        val friendlyFlags: FriendlyFlags,
        val nameTagVisibility: String,
        val collisionRule: String,
        val teamColor: TeamDecoration,
        val teamPrefix: Component,
        val teamSuffix: Component
    ) : Method {
        companion object {
            val CODEC: Codec<UpdateTeamInfo> = Codec.of(
                Codec.TEXT_COMPONENT, { it.teamDisplayName },
                Codec.bitField(FriendlyFlags::class.java) { FriendlyFlags() }, { it.friendlyFlags },
                Codec.STRING, { it.nameTagVisibility },
                Codec.STRING, { it.collisionRule },
                Codec.ofEnum(TeamDecoration::class.java), { it.teamColor },
                Codec.TEXT_COMPONENT, { it.teamPrefix },
                Codec.TEXT_COMPONENT, { it.teamSuffix },
                ::UpdateTeamInfo
            )
        }
    }

    data class AddEntities(val entities: List<String>) : Method {
        companion object {
            val CODEC: Codec<AddEntities> = Codec.of(
                Codec.STRING.list(), { it.entities }, ::AddEntities
            )
        }
    }

    data class RemoveEntities(val entities: List<String>) : Method {
        companion object {
            val CODEC: Codec<RemoveEntities> = Codec.of(
                Codec.STRING.list(), { it.entities }, ::RemoveEntities
            )
        }
    }

    enum class TeamDecoration {
        BLACK,
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW,
        WHITE,
        OBFUSCATED,
        BOLD,
        STRIKETHROUGH,
        UNDERLINED,
        ITALIC,
        RESET
    }

}