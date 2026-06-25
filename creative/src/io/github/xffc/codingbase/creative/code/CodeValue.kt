package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.creative.extensions.plain
import io.github.xffc.codingbase.creative.util.ComponentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component

@Serializable
sealed interface CodeValue {
    typealias Variables = HashMap<String, CodeValue>

    fun toComponent(): Component

    @Serializable
    @SerialName("text")
    data class Text(
        val value: @Serializable(with = ComponentSerializer::class) Component
    ): CodeValue {
        override fun toComponent() = value
    }

    @Serializable
    @SerialName("number")
    data class Number(
        val value: Double
    ): CodeValue {
        override fun toComponent() = value.toString().plain
    }
}