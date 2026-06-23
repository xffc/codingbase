package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.extensions.customName
import io.github.xffc.codingbase.creative.extensions.noStyle
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.extensions.worldId
import io.github.xffc.codingbase.creative.util.DataInterface
import io.github.xffc.codingbase.creative.worlds.CreativeWorldFactory
import kotlinx.coroutines.launch
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class PlayerWorldsMenu(
    private val player: Player
) : AbstractMenu("menu.playerworlds.title".translatable()) {
    companion object {
        const val MAX_WORLDS = 4 // todo: временное решение пока что, потом перенести в мету игрока лакпермса

        private val createWorldItem = ItemStack.of(Material.WHITE_STAINED_GLASS_PANE)
            .customName("menu.playerworlds.create".translatable())
    }

    init {
        tick()
    }

    override fun tick() {
        DataInterface.scope.launch {
            val worlds = suspendTransaction {
                DataInterface.getPlayerWorlds(player.uniqueId)
            }

            (0..<MAX_WORLDS).forEach { index ->
                val item = worlds.getOrNull(index)?.toItemStack() ?: createWorldItem
                inv.setItem(index, item)
            }
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        val item = event.currentItem ?: return

        if (item == createWorldItem) {
            event.whoClicked.openInventory(CreateWorldMenu(player).inventory)
            return
        }

        item.worldId?.also {
            player.closeInventory()
            join(it)
        }
    }

    private fun join(worldId: UInt) {
        val world = CreativeWorldFactory.get(worldId)

        if (world == null) {
            DataInterface.scope.launch {
                val info = suspendTransaction {
                    DataInterface.getPlayerWorld(worldId)
                }

                val world = CreativeWorldFactory.load(info)
                world.join(player)
            }
        } else {
            world.join(player)
        }
    }
}
