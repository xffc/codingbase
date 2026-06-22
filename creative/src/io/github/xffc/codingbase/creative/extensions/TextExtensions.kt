package io.github.xffc.codingbase.creative.extensions

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TranslatableComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.translation.GlobalTranslator
import java.util.Locale

fun String.translatable(vararg args: Component): TranslatableComponent =
    Component.translatable(this, *args)

fun Component.translate(locale: Locale): Component =
    GlobalTranslator.render(this, locale)

fun String.translate(locale: Locale, vararg args: Component): Component =
    translatable(*args).translate(locale)

val Component.json: String
    get() = JSONComponentSerializer.json().serialize(this)

val Component.nms
    get() = PaperAdventure.asVanilla(this)

val String.plain: Component
    get() = PlainTextComponentSerializer.plainText().deserialize(this)

val String.json: Component
    get() = JSONComponentSerializer.json().deserialize(this)

val String.miniMessage: Component
    get() = MiniMessage.miniMessage().deserialize(this)

val Component.noStyle
    get() = Component.empty()
        .decoration(TextDecoration.ITALIC, false)
        .color(NamedTextColor.WHITE)
        .append(this)