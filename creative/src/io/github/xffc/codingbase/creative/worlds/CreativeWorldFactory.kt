package io.github.xffc.codingbase.creative.worlds

import io.github.xffc.codingbase.creative.CreativePlugin.WORLDS_PREFIX
import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.worlds.generator.SimpleBiomeProvider
import io.github.xffc.codingbase.creative.worlds.generator.WorldGenerator
import org.bukkit.GameRules
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.block.Biome

object CreativeWorldFactory {
    private val activeWorlds = mutableMapOf<UInt, CreativeWorld>()

    fun get(id: UInt): CreativeWorld? =
        activeWorlds[id]

    fun load(info: CreativeWorldInfo): CreativeWorld =
        register(createInstance(info), info)

    fun create(info: CreativeWorldInfo): CreativeWorld {
        val instance = createInstance(info)

        val center = info.size.toDouble() * 16 / 2

        instance.spawnLocation = Location(instance, center, WorldGenerator.SPAWN_HEIGHT.toDouble(), center)

        instance.worldBorder.setCenter(center, center)
        instance.worldBorder.size = info.size.toDouble() * 16

        instance.setGameRule(GameRules.RAIDS, false)
        instance.setGameRule(GameRules.SPAWN_MOBS, false)
        instance.setGameRule(GameRules.LOCATOR_BAR, false)
        instance.setGameRule(GameRules.ADVANCE_TIME, false)
        instance.setGameRule(GameRules.SPAWN_WARDENS, false)
        instance.setGameRule(GameRules.SPAWN_PATROLS, false)
        instance.setGameRule(GameRules.SPAWN_PHANTOMS, false)
        instance.setGameRule(GameRules.SPAWN_MONSTERS, false)
        instance.setGameRule(GameRules.ADVANCE_WEATHER, false)
        instance.setGameRule(GameRules.IMMEDIATE_RESPAWN, true)
        instance.setGameRule(GameRules.COMMAND_BLOCKS_WORK, false)
        instance.setGameRule(GameRules.SHOW_DEATH_MESSAGES, false)
        instance.setGameRule(GameRules.GLOBAL_SOUND_EVENTS, false)
        instance.setGameRule(GameRules.SPAWN_WANDERING_TRADERS, false)
        instance.setGameRule(GameRules.SHOW_ADVANCEMENT_MESSAGES, false)
        instance.setGameRule(GameRules.SPECTATORS_GENERATE_CHUNKS, false)
        instance.setGameRule(GameRules.ALLOW_ENTERING_NETHER_USING_PORTALS, false)
        instance.setGameRule(GameRules.RESPAWN_RADIUS, 0)

        return register(instance, info)
    }

    private fun register(instance: World, info: CreativeWorldInfo): CreativeWorld {
        val world = CreativeWorld(instance, info)
        activeWorlds[world.info.id] = world
        return world
    }

    private fun createInstance(info: CreativeWorldInfo): World =
        WorldCreator(WORLDS_PREFIX + info.id)
            .generator(WorldGenerator(info.size, info.generator))
            .biomeProvider(SimpleBiomeProvider(Biome.PLAINS))
            .createWorld()!!
}