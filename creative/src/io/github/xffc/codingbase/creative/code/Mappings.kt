package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.TextSerializer
import io.github.xffc.codingbase.data.VariableScope
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun CodeArgument.toValue(context: CodeContext): CodeValue = when (this) {
    is CodeArgument.Text -> CodeValue.Text(
        serializer.instance.deserialize(Placeholders.replace(text, context))
    )

    is CodeArgument.Number -> CodeValue.Number(value)

    is CodeArgument.Variable -> scope.getContainer(context)[name]
        ?: throw IllegalArgumentException("Variable $name not found")
}

fun VariableScope.getContainer(context: CodeContext): CodeValue.Variables = when (this) {
    VariableScope.LOCAL -> context.localVariables
    VariableScope.GLOBAL -> context.runtime.globalVariables
    VariableScope.SAVED -> context.runtime.savedVariables
}

// todo: ограничить через билдеры (потому что в minimessage можно вставить команды и все такое)
val TextSerializer.instance
    get() = when (this) {
        TextSerializer.MINI_MESSAGE -> MiniMessage.miniMessage()
        TextSerializer.PLAIN -> PlainTextComponentSerializer.plainText()
    }