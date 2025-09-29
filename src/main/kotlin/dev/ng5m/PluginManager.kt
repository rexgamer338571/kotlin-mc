package dev.ng5m

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException

class PluginManager {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PluginManager::class.java)
    }

    private val plugins = mutableMapOf<String, Plugin>()

    fun registerPlugin(id: String, plugin: Plugin) {
        plugins[id] = plugin
    }

    fun getPlugin(id: String): Plugin = plugins[id] ?: throw IllegalArgumentException("no such plugin: $id")

    private fun disablePlugin(id: String) {
        val plugin = getPlugin(id)
        try {
            plugin.onDisable()
        } catch (x: Exception) {
            LOGGER.error("Error disabling plugin $id: $x")
        }
    }

    private fun enablePlugin(id: String) {
        val plugin = getPlugin(id)
        try {
            plugin.onEnable()
        } catch (x: Exception) {
            LOGGER.error("Error enabling plugin $id: $x")
            disablePlugin(id)
        }
    }

    fun enablePlugins() {
        plugins.keys.forEach(this::enablePlugin)
    }

    private fun disablePlugins() {
        plugins.keys.forEach(this::disablePlugin)
    }

}