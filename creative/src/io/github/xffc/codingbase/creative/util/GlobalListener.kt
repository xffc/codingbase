package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.CreativePlugin.spawnWorld
import io.github.xffc.codingbase.creative.extensions.uuid
import io.github.xffc.codingbase.creative.items.CustomItem
import io.github.xffc.codingbase.creative.items.CustomItem.Companion.customItemKey
import io.github.xffc.codingbase.creative.items.TextInputItem
import io.github.xffc.codingbase.creative.menu.AbstractMenu
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.persistence.PersistentDataType

object GlobalListener: Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val pipeline = (event.player as CraftPlayer).handle.connection.connection.channel.pipeline()
        pipeline.addBefore("packet_handler", "packet_interceptor", PacketInterceptor(event.player))

        // todo: reset player
        event.player.teleport(spawnWorld.spawnLocation)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val pipeline = (event.player as CraftPlayer).handle.connection.connection.channel.pipeline()
        if (pipeline.get("packet_interceptor") != null ) pipeline.remove("packet_interceptor")
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val menu = event.inventory.holder as? AbstractMenu ?: return
        event.isCancelled = true

        event.currentItem?.persistentDataContainer
            ?.get(customItemKey, PersistentDataType.STRING)
            ?.uuid
            ?.let { customId ->
                CustomItem.activeItems[customId]?.onClick(event) ?: return@let
                return
            }

        menu.onClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val menu = event.inventory.holder as? AbstractMenu ?: return
        menu.onClose(event)
    }

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        val id = event.player.persistentDataContainer.get(customItemKey, PersistentDataType.STRING).also{println(it)}?.uuid ?: return
        val textInput = CustomItem.activeItems[id].also{println(it)} as? TextInputItem ?: return
        textInput.complete(event.message())
        event.isCancelled = true
    }
}