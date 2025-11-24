package dev.ng5m

import dev.ng5m.entity.Entity
import dev.ng5m.packet.play.s2c.SetPlayerTeamS2CPacket
import net.kyori.adventure.text.Component
import java.util.UUID


object NameTagVisibility {
    const val ALWAYS = "always"
    const val HIDE_FOR_OTHER_TEAMS = "hideForOtherTeams"
    const val HIDE_FOR_OWN_TEAM = "hideForOwnTeam"
    const val NEVER = "never"
}

object CollisionRule {
    const val ALWAYS = "always"
    const val PUSH_OTHER_TEAMS = "pushOtherTeams"
    const val PUSH_OWN_TEAM = "pushOwnTeam"
    const val NEVER = "never"
}

object Teams {
    val teams = mutableSetOf<Team>()

    fun registerTeam(
        name: String,
        displayName: Component = Component.text(name),
        nameTagVisibility: String = NameTagVisibility.ALWAYS,
        collisionRule: String = CollisionRule.ALWAYS,
        color: SetPlayerTeamS2CPacket.TeamDecoration = SetPlayerTeamS2CPacket.TeamDecoration.RESET,
        prefix: Component = Component.empty(),
        suffix: Component = Component.empty(),
        entities: Collection<String> = listOf(),
        friendlyFire: Boolean = false,
        canSeeFriendlyInvisible: Boolean = false
    ): Team {
        val team = Team(name)
        team.entities.addAll(entities)
        team.displayName = displayName
        team.nameTagVisibility = nameTagVisibility
        team.collisionRule = collisionRule
        team.color = color
        team.prefix = prefix
        team.suffix = suffix
        team.friendlyFire = friendlyFire
        team.canSeeFriendlyInvisible = canSeeFriendlyInvisible

        teams.add(team)
        MinecraftServer.getInstance().broadcastPlayPacket(team.createPacket())

        return team
    }

}

class Team(val name: String) {
    internal val entities = mutableSetOf<String>()
    internal var displayName: Component = Component.text("name")
    internal var nameTagVisibility: String = NameTagVisibility.ALWAYS
    internal var collisionRule: String = CollisionRule.ALWAYS
    internal var color: SetPlayerTeamS2CPacket.TeamDecoration = SetPlayerTeamS2CPacket.TeamDecoration.RESET
    internal var prefix: Component = Component.empty()
    internal var suffix: Component = Component.empty()
    internal var friendlyFire: Boolean = false
    internal var canSeeFriendlyInvisible: Boolean = false

    fun addEntities(vararg entities: Entity) {
        addEntities(*entities.map { it.uuid.toString() }.toTypedArray())
    }

    fun addEntities(vararg entities: String) {
        this.entities.addAll(entities)
        MinecraftServer.getInstance().broadcastPlayPacket(
            SetPlayerTeamS2CPacket(name, SetPlayerTeamS2CPacket.AddEntities(entities.toList()))
        )
    }

    fun removeEntities(vararg entities: Entity) {
        this.entities.removeAll(entities.map { it.uuid.toString() }.toSet())
        MinecraftServer.getInstance().broadcastPlayPacket(
            SetPlayerTeamS2CPacket.removeEntities(this, *entities)
        )
    }

    fun createPacket(): SetPlayerTeamS2CPacket =
        SetPlayerTeamS2CPacket.createTeam(this)

}