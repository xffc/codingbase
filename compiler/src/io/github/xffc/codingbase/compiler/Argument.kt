package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.TextSerializer
import io.github.xffc.codingbase.data.VariableScope

class Argument(context: Parser.Context, valueToken: Token) {
    val value = when (valueToken.type) {
        Token.Type.KEYWORD -> {
            context.expectNextToken(Token.Type.COLON)
            val scope = VariableScope.valueOf(context.expectNextToken(Token.Type.KEYWORD).text)
            CodeArgument.Variable(valueToken.text, scope)
        }

        Token.Type.TEXT -> {
            context.expectNextToken(Token.Type.COLON)
            val serializer = TextSerializer.valueOf(context.expectNextToken(Token.Type.KEYWORD).text)
            CodeArgument.Text(valueToken.text, serializer)
        }

        Token.Type.NUMBER -> CodeArgument.Number(valueToken.text.toDouble())

        else -> throw IllegalArgumentException()
    }
}