package io.github.xffc.codingbase.creative.menu

import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.extensions.runSync
import io.github.xffc.codingbase.creative.extensions.translatable
import io.github.xffc.codingbase.creative.items.CustomItem
import io.github.xffc.codingbase.creative.items.SwitchItem
import io.github.xffc.codingbase.creative.items.TextInputItem
import io.github.xffc.codingbase.creative.worlds.CreativeWorld
import io.github.xffc.codingbase.creative.worlds.generator.WorldGeneratorType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class ManageWorldMenu(
    val player: Player,
    val world: CreativeWorld
) : AbstractMenu("menu.manageworld.title".translatable()) {
    private val items = generateWorldManageItems(this, world.info)

    init {
        tick()
    }

    override fun tick() {
        items.forEachIndexed { index, item -> inv.setItem(10 + index, item.stack) }
        inv.setItem(16, world.info.toItemStack())
    }

    override fun onClose(event: InventoryCloseEvent) {
        if (event.reason == InventoryCloseEvent.Reason.PLUGIN) return
        items.forEach { it.destroy() }
    }

    companion object {
        fun generateWorldManageItems(
            menu: AbstractMenu,
            info: CreativeWorldInfo, creation: Boolean = false
        ) = buildList<CustomItem<*>> {
            add(
                TextInputItem(
                    "items.set_world_name",
                    menu,
                    ItemStack.of(Material.NAME_TAG),
                    info.name
                ) { info.name = it }
            )

            if (creation)
                add(
                    SwitchItem(
                        "items.set_world_type",
                        menu,
                        WorldGeneratorType.entries.map {
                            SwitchItem.Entry(it.name.lowercase(), it, it.material)
                        },
                        info.generator.ordinal
                    ) { info.generator = it }
                )

            add(
                SwitchItem(
                    "items.set_is_closed",
                    menu,
                    listOf(
                        SwitchItem.Entry("closed", true, Material.IRON_DOOR),
                        SwitchItem.Entry("open", false, Material.OAK_DOOR)
                    ),
                    if (info.isClosed) 0 else 1
                ) { info.isClosed = it }
            )
        }
    }
}