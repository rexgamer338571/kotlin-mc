package dev.ng5m.packet.play.s2c

import dev.ng5m.entity.inventory.Inventory
import dev.ng5m.entity.inventory.InventoryType
import dev.ng5m.registry.Registries
import dev.ng5m.registry.ResourceKey
import dev.ng5m.serialization.Codec
import dev.ng5m.serialization.Packet
import dev.ng5m.util.ofEnum
import net.kyori.adventure.text.Component

data class OpenScreenS2CPacket(
    val windowId: Int,
    val windowType: ResourceKey<InventoryType>,
    val windowTitle: Component
) : Packet {
    companion object {
        val CODEC: Codec<OpenScreenS2CPacket> = Codec.of(
            Codec.VARINT, { it.windowId },
            Registries.MENU.idCodec, { it.windowType },
            Codec.TEXT_COMPONENT, { it.windowTitle },
            ::OpenScreenS2CPacket
        ).forType(OpenScreenS2CPacket::class.java)
    }

    constructor(windowType: ResourceKey<InventoryType>, windowTitle: Component) :
            this(Inventory.ID_TRACKER.next(), windowType, windowTitle)

}