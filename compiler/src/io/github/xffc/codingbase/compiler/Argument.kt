package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.TextSerializer
import io.github.xffc.codingbase.data.VariableScope

enum class ArgumenType(
    val tokenType: Token.Type,
    val parser: (Parser.Context, valueToken: Token) -> CodeArgument
) {
    TEXT(Token.Type.TEXT, { context, valueToken ->
        context.expectNextToken(Token.Type.COLON)
        context.expectNextToken(Token.Type.KEYWORD)
        val serializer = TextSerializer.valueOf(context.current.text)
        CodeArgument.Text(valueToken.text, serializer)
    }),

    VARIABLE(Token.Type.KEYWORD, { context, valueToken ->
        context.expectNextToken(Token.Type.COLON)
        context.expectNextToken(Token.Type.KEYWORD)
        val scope = VariableScope.valueOf(context.current.text)
        CodeArgument.Variable(valueToken.text, scope)
    }),

    NUMBER(Token.Type.NUMBER, { _, valueToken ->
        CodeArgument.Number(valueToken.text.toDouble())
    });

    companion object {
        val registry = ArgumenType.entries
            .associate { it.tokenType to it.parser }
    }
}