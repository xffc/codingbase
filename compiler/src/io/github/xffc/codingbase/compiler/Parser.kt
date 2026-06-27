package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.CodeBlock

class Parser(
    val methods: Map<String, MethodEntry>
) {
    fun getBlocks(tokens: List<Token>): List<CodeBlock.StartBlock> = buildList {
        val context = Context(tokens)

        context.readUntil {
            add(parseStartBlock(context))
            return@readUntil true
        }
    }

    private fun parseStartBlock(context: Context): CodeBlock.StartBlock {
        context.expectToken(Token.Type.KEYWORD)
        val type = context.current.text
        context.expectNextToken(Token.Type.COLON)
        context.expectNextToken(Token.Type.KEYWORD)
        val id = context.current.text

        return when (type) {
            "event" -> CodeBlock.StartBlock.EventBlock(id, parseBody(context))
            "function" -> CodeBlock.StartBlock.FunctionBlock(id, parseBody(context))
            else -> throw IllegalArgumentException("Invalid start block type ($type)")
        }
    }

    private fun parseBody(context: Context): CodeBlock.Body = buildList {
        context.expectNextToken(Token.Type.LBRACE)

        context.readUntil(true) {
            context.expectNextToken(Token.Type.KEYWORD, Token.Type.RBRACE)
            if (context.current.type == Token.Type.RBRACE) return@readUntil false
            addAll(parseMethodBlock(context))
            true
        }
    }

    private fun parseMethodBlock(context: Context): List<CodeBlock.MethodBlock> {
        context.expectToken(Token.Type.KEYWORD)
        return when (context.current.text) {
            "if" -> parseConditionBlock(context)
            else -> listOf(parseActionBlock(context))
        }
    }

    private fun parseConditionBlock(context: Context): List<CodeBlock.MethodBlock> = buildList {
        context.expectNextToken(Token.Type.KEYWORD, Token.Type.INVERT)

        val isInverted = if (context.current.type == Token.Type.INVERT) {
            context.expectNextToken(Token.Type.KEYWORD)
            true
        } else false

        val method = parseMethod(context)
        val body = parseBody(context)

        add(CodeBlock.MethodBlock.ConditionBlock(method.id, method.arguments, body, isInverted))
        context.peek { it.type == Token.Type.KEYWORD && it.text == "else" }?.also {
            val elseBody = parseBody(context)
            add(CodeBlock.MethodBlock.ElseBlock(elseBody))
        }
    }

    private fun parseActionBlock(context: Context): CodeBlock.MethodBlock.ActionBlock {
        val method = parseMethod(context)
        return CodeBlock.MethodBlock.ActionBlock(method.id, method.arguments)
    }

    private fun parseArgument(context: Context): CodeArgument =
        ArgumenType.registry.getValue(context.current.type).invoke(context, context.current)

    private fun parseMethod(context: Context): Method {
        val id = context.current.text
        val method = methods[id] ?: throw IllegalArgumentException("Method $id not found")

        context.expectNextToken(Token.Type.LPAREN)

        val arguments = buildList {
            context.readUntil(true) {
                context.expectNextToken(Token.Type.RPAREN, Token.Type.COMMA, *ArgumenType.registry.keys.toTypedArray())
                when (context.current.type) {
                    Token.Type.RPAREN -> return@readUntil false
                    Token.Type.COMMA -> return@readUntil true
                    else -> add(parseArgument(context))
                }
                true
            }
        }

        return Method(
            id,
            method,
            method.options.indices.associate { index ->
                method.options[index] to arguments[index]
            }
        )
    }

    data class Method(
        val id: String,
        val entry: MethodEntry,
        val arguments: CodeBlock.Arguments
    )

    data class Context(private val tokens: List<Token>) : AbstractConsumeContext<Token> {
        var position: Int = 0

        override var current: Token = tokens[position]

        override fun isExausted() =
            position >= tokens.size - 1

        fun expectToken(vararg types: Token.Type) {
            if (current.type !in types) throw IllegalArgumentException("Expected token $current to be one of ${types.joinToString { it.name }} at $position")
        }

        fun expectNextToken(vararg types: Token.Type) {
            next()
            expectToken(*types)
        }

        fun peek(filter: (Token) -> Boolean): Token? {
            next()
            if (!filter(current)) {
                previous()
                return null
            }
            return current
        }

        override fun next() {
            position++
            current = tokens[position]
        }

        override fun previous() {
            position--
            current = tokens[position]
        }
    }
}