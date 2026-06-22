package io.github.xffc.codingbase.creative

import io.github.xffc.codingbase.creative.extensions.namespaced
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore
import net.kyori.adventure.translation.GlobalTranslator
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

object CreativePlugin: JavaPlugin() {
    val locales = listOf(Locale.US)

    override fun onEnable() {
        registerLocales()
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