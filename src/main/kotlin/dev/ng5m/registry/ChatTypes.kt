package dev.ng5m.registry

object ChatTypes : RegistryInitializer<ChatType>(Registries.CHAT_TYPE) {
    val TEAM_MSG_COMMAND_INCOMING = add("team_msg_command_incoming")

    val SAY_COMMAND = add("say_command")

    val TEAM_MSG_COMMAND_OUTGOING = add("team_msg_command_outgoing")

    val MSG_COMMAND_OUTGOING = add("msg_command_outgoing")

    val EMOTE_COMMAND = add("emote_command")

    val CHAT = add("chat")

    val MSG_COMMAND_INCOMING = add("msg_command_incoming")
}
    