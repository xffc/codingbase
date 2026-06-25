package io.github.xffc.codingbase.creative.code

import net.kyori.adventure.text.Component

sealed interface CodeValue {
    data class Text(
        val value: Component
    ): CodeValue

    data class Number(
        val value: Double
    ): CodeValue
}