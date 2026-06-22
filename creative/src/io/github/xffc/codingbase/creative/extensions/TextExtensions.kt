package io.github.xffc.codingbase.creative.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TranslatableComponent
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer

fun String.translatable(vararg args: Component): TranslatableComponent =
    Component.translatable(this, *args)

val String.json: Component
    get() = JSONComponentSerializer.json().deserialize(this)