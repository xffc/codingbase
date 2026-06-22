package io.github.xffc.codingbase.creative.worlds.generator

import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo

class SimpleBiomeProvider(val biome: Biome): BiomeProvider() {
    override fun getBiome(worldInfo: WorldInfo, x: Int, y: Int, z: Int) = biome
    override fun getBiomes(worldInfo: WorldInfo) = listOf(biome)
}