package io.github.xffc.codingbase.creative.worlds

import io.github.xffc.codingbase.creative.CreativePlugin.WORLDS_PREFIX
import io.github.xffc.codingbase.creative.data.CreativeWorldInfo
import io.github.xffc.codingbase.creative.worlds.generator.SimpleBiomeProvider
import io.github.xffc.codingbase.creative.worlds.generator.WorldGenerator
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
        // todo: геймрулы, worldborder
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