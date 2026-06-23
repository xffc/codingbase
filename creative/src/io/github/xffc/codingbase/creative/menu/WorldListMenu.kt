package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.extensions.worldId
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import io.github.xffc.codingbase.creative.worlds.CreativeWorldFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

// todo
class WorldListMenu(
    private val player: Player
) : AbstractMenu.Paged<CreativeWorld>(
    "menu.worldlist.title".translatable(),
    54
) {
    init {
        tick()
    }

    override fun getEntries(): Collection<CreativeWorld> = CreativeWorldFactory.activeWorlds.values

    override fun itemStack(value: CreativeWorld): ItemStack = value.info.toItemStack(player)

    override fun onClick(event: InventoryClickEvent) {
        super.onClick(event)
        val id = event.currentItem?.worldId ?: return
        CreativeWorldFactory.activeWorlds[id]?.join(player)
    }
}