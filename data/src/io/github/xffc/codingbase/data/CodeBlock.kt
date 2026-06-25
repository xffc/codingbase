package io.github.xffc.codingbase.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface CodeBlock {
    val id: String

    typealias Body = List<MethodBlock>
    typealias Arguments = Map<String, CodeArgument>

    interface HasBody {
        val body: Body
    }

    @Serializable
    sealed interface MethodBlock : CodeBlock {
        val arguments: Arguments

        @Serializable
        @SerialName("action")
        data class ActionBlock(
            override val id: String,
            override val arguments: Arguments = mapOf()
        ) : MethodBlock

        @Serializable
        @SerialName("condition")
        data class ConditionBlock(
            override val id: String,
            override val arguments: Arguments = mapOf(),
            override val body: Body = listOf(),
            val isInverted: Boolean = false
        ) : MethodBlock, HasBody

        @Serializable
        @SerialName("else")
        data class ElseBlock(
            override val body: Body
        ) : MethodBlock, HasBody {
            override val id: String = "else"
            override val arguments: Arguments = mapOf()
        }
    }

    @Serializable
    sealed interface StartBlock : CodeBlock, HasBody {
        @Serializable
        @SerialName("event")
        data class EventBlock(
            @SerialName("event")
            override val id: String,
            override val body: Body = listOf()
        ) : StartBlock

        @Serializable
        @SerialName("function")
        data class FunctionBlock(
            @SerialName("name")
            override val id: String,
            override val body: Body = listOf()
        ) : StartBlock
    }
}