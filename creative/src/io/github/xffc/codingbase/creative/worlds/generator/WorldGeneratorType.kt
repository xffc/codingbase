package io.github.xffc.codingbase.creative.worlds.generator

import io.github.xffc.codingbase.creative.worlds.generator.WorldGenerator.Companion.SPAWN_HEIGHT
import io.github.xffc.codingbase.creative.worlds.generator.WorldGenerator.Companion.fillY
import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator

enum class WorldGeneratorType: WorldGenerator.Generator {
    VOID {
        override fun generate(generator: WorldGenerator, chunkX: Int, chunkZ: Int, chunkData: ChunkGenerator.ChunkData) {
            if (chunkX != generator.center || chunkZ != generator.center) return
            chunkData.fillY(SPAWN_HEIGHT - 1, 1, Material.STONE)
        }
    },
    PLAIN {
        override fun generate(generator: WorldGenerator, chunkX: Int, chunkZ: Int, chunkData: ChunkGenerator.ChunkData) {
            chunkData.fillY(SPAWN_HEIGHT - 1, 1, Material.GRASS_BLOCK)
            chunkData.fillY(SPAWN_HEIGHT - 4, 3, Material.DIRT)
            chunkData.fillY(SPAWN_HEIGHT - 5, 1, Material.BEDROCK)
        }
    }
}