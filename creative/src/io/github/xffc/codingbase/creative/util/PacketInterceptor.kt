package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.extensions.nms
import io.github.xffc.codingbase.creative.extensions.translate
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import io.papermc.paper.datacomponent.DataComponentTypes
import net.minecraft.core.component.DataComponents
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemLore
import org.bukkit.entity.Player

class PacketInterceptor(private val player: Player) : ChannelDuplexHandler() {
    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        val packet = when (msg) {
            is ClientboundContainerSetContentPacket -> onSetContent(msg)
            is ClientboundContainerSetSlotPacket -> onSetSlot(msg)

            else -> null
        } ?: msg

        super.write(ctx, packet, promise)
    }

    private fun onSetContent(packet: ClientboundContainerSetContentPacket): ClientboundContainerSetContentPacket {
        val items = packet.items.map { translateItem(it) ?: it }
        val carriedItem = translateItem(packet.carriedItem) ?: packet.carriedItem

        return ClientboundContainerSetContentPacket(
            packet.containerId,
            packet.stateId,
            items,
            carriedItem
        )
    }

    private fun onSetSlot(packet: ClientboundContainerSetSlotPacket): ClientboundContainerSetSlotPacket? {
        return ClientboundContainerSetSlotPacket(
            packet.containerId,
            packet.stateId,
            packet.slot,
            translateItem(packet.item) ?: return null
        )
    }

    private fun translateItem(base: ItemStack): ItemStack? {
        val stack = base.bukkitStack

        val customName = stack.getData(DataComponentTypes.CUSTOM_NAME)
            ?.translate(player.locale())?.nms

        val lore = stack.getData(DataComponentTypes.LORE)?.lines()
            ?.map { it.translate(player.locale()).nms }

        if (customName == null && lore.isNullOrEmpty()) return null

        val copy = base.copy()

        customName?.also { copy.set(DataComponents.CUSTOM_NAME, it) }
        lore?.also { copy.set(DataComponents.LORE, ItemLore(it)) }

        return copy
    }
}