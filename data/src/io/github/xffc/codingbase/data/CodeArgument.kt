package io.github.xffc.codingbase.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface CodeArgument {
    @Serializable
    @SerialName("text")
    data class Text(
        val text: String,
        val serializer: TextSerializer
    ) : CodeArgument

    @Serializable
    @SerialName("number")
    data class Number(
        val value: Double
    ) : CodeArgument

    @Serializable
    @SerialName("variable")
    data class Variable(
        val name: String,
        val scope: VariableScope
    ) : CodeArgument
}