package io.github.xffc.codingbase.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CodeBody = List<CodeBlock.MethodBlock>

@Serializable
sealed interface CodeBlock {
    val id: String

    interface HasBody {
        val body: CodeBody
    }

    @Serializable
    sealed interface MethodBlock : CodeBlock {
        // todo: аргументы

        @Serializable
        @SerialName("action")
        data class ActionBlock(
            override val id: String
        ) : MethodBlock

        @Serializable
        @SerialName("condition")
        data class ConditionBlock(
            override val id: String,
            override val body: CodeBody
        ) : MethodBlock, HasBody
    }

    @Serializable
    sealed interface StartBlock : CodeBlock, HasBody {
        @Serializable
        @SerialName("event")
        data class EventBlock(
            @SerialName("event")
            override val id: String,
            override val body: CodeBody
        ) : StartBlock

        @Serializable
        @SerialName("function")
        data class FunctionBlock(
            @SerialName("name")
            override val id: String,
            override val body: CodeBody
        ) : StartBlock
    }
}