package io.github.xffc.codingbase.creative

import io.github.xffc.codingbase.creative.commands.AbstractCommand
import io.github.xffc.codingbase.creative.data.Worlds
import io.github.xffc.codingbase.creative.extensions.namespaced
import io.github.xffc.codingbase.creative.util.DataInterface
import io.github.xffc.codingbase.creative.util.GlobalListener
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore
import net.kyori.adventure.translation.GlobalTranslator
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File
import java.util.Locale

object CreativePlugin: JavaPlugin() {
    val locales = listOf(Locale.US)

    val IS_DEBUG_ENV = System.getProperty("IS_DEBUG", "true").toBoolean()

    const val WORLDS_PREFIX = "worlds/"

    override fun onEnable() {
        registerLocales()

        if (IS_DEBUG_ENV) File(server.worldContainer, WORLDS_PREFIX).deleteRecursively()

        DataInterface.connect(
            config.getString("url", "jdbc:h2:./database")!!,
            config.getString("user", "sa")!!,
            config.getString("password", "")!!
        )

        transaction {
            if (IS_DEBUG_ENV) SchemaUtils.drop(Worlds)
            SchemaUtils.create(Worlds)
        }

        server.pluginManager.registerEvents(GlobalListener, this)

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            AbstractCommand.registry.forEach { command ->
                it.registrar().register(command.instance)
            }
        }
    }

    // todo: копирование переводов в dataFolder
    @Suppress("UNCHECKED_CAST")
    private fun registerLocales() {
        val source = MiniMessageTranslationStore.create("translations".namespaced)

        locales.forEach { locale ->
            val path = "locales/${locale.toLanguageTag()}.yml"

            val registry = getTextResource(path)?.use { reader ->
                YamlConfiguration.loadConfiguration(reader)
                    .getValues(true)
                    .filterValues { it is String } as Map<String, String>
            } ?: return@forEach

            source.registerAll(locale, registry)
        }

        GlobalTranslator.translator().addSource(source)
    }
}