package io.github.xffc.codingbase.compiler

import kotlinx.serialization.Serializable

@Serializable
data class MethodEntry(
    val type: String,
    val options: List<String>
)