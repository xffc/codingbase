package io.github.xffc.codingbase.creative.worlds.generator

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.Random

class WorldGenerator(size: UShort, private val generator: Generator): ChunkGenerator() {
    val bounds = (0..<size.toInt())
    val center = bounds.last / 2

    override fun generateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        generator.generate(this, chunkX, chunkZ, chunkData)
    }

    fun interface Generator {
        fun generate(generator: WorldGenerator, chunkX: Int, chunkZ: Int, chunkData: ChunkData)
    }

    companion object {
        const val SPAWN_HEIGHT = 128

        fun ChunkData.fillY(from: Int, height: Int, material: Material) {
            setRegion(0, from, 0, 16, from + height, 16, material)
        }
    }
}