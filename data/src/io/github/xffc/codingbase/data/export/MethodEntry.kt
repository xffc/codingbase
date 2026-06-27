package io.github.xffc.codingbase.data.export

import kotlinx.serialization.Serializable

@Serializable
data class MethodEntry(
    val type: Type,
    val options: List<Option>
) {
    @Serializable
    data class Option(
        val name: String,
        val type: OptionType
    )

    enum class Type { ACTION, CONDITION }
}