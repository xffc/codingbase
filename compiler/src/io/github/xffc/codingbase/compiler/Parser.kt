package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.compiler.structure.Token
import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.TextSerializer
import io.github.xffc.codingbase.data.VariableScope

object Parser {
    fun getBlocks(tokens: List<Token>): List<CodeBlock.StartBlock> = buildList {
        val context = Context(tokens.listIterator())
        context.readUntil {
            add(parseStartBlock(context))
            true
        }
    }

    private fun parseStartBlock(context: Context): CodeBlock.StartBlock {
        context.expectToken(Token.Type.KEYWORD)
        val type = context.current.text

        context.expectNextToken(Token.Type.COLON)

        val id = context.expectNextToken(Token.Type.KEYWORD).text

        val body = parseBody(context)

        return when (type) {
            "event" -> CodeBlock.StartBlock.EventBlock(id, body)
            "function" -> CodeBlock.StartBlock.FunctionBlock(id, body)
            else -> throw IllegalArgumentException("Invalid start block type $type")
        }
    }

    private fun parseBody(context: Context): CodeBlock.Body = buildList {
        context.expectNextToken(Token.Type.LBRACE)

        context.readUntil {
            context.expectNextToken(Token.Type.KEYWORD, Token.Type.RBRACE)

            when (context.current.type) {
                Token.Type.KEYWORD -> add(parseActionBlock(context))
                Token.Type.RBRACE -> return@readUntil false
                else -> throw IllegalArgumentException()
            }

            true
        }
    }

    fun parseActionBlock(context: Context): CodeBlock.MethodBlock.ActionBlock {
        val id = context.current.text

        context.expectNextToken(Token.Type.LPAREN)

        val args = buildList {
            context.readUntil {
                val valueToken = context.expectNextToken(
                    Token.Type.KEYWORD,
                    Token.Type.TEXT,
                    Token.Type.NUMBER
                )

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

                    Token.Type.RPAREN -> return@readUntil false

                    Token.Type.COMMA -> return@readUntil true

                    else -> throw IllegalArgumentException()
                }

                add(value)

                context.peek {
                    when (it.type) {
                        Token.Type.RPAREN -> false
                        Token.Type.COMMA -> true
                        else -> throw IllegalArgumentException()
                    }
                }
            }
        }

        // todo: associate arguments to ids
        return CodeBlock.MethodBlock.ActionBlock(id, mapOf())
    }

    private fun <T> Context.peek(consumer: (Token) -> T): T {
        next()
        val result = consumer(current)
        previous()
        return result
    }

    private fun Context.expectNextToken(vararg types: Token.Type): Token {
        next()
        expectToken(*types)
        return current
    }

    private fun Context.expectToken(vararg types: Token.Type) {
        if (current.type !in types) throw IllegalArgumentException("Expected token type to be any of ${types.joinToString { it.name }}, but got ${current.type.name}")
    }

    data class Context(private val tokens: ListIterator<Token>) : AbstractConsumeContext<Token> {
        override var current = tokens.next()

        override fun isExausted() = !tokens.hasNext()

        override fun next() {
            current = tokens.next()
        }

        override fun previous() {
            current = tokens.previous()
        }
    }
}