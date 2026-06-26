package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.compiler.structure.Token

object Lexer {
    fun getTokens(text: CharSequence): List<Token> = buildList {
        val context = Context(text)
        context.readUntil {
            val token = nextToken(context)
            if (token != null) add(token)
            true
        }
    }

    private fun nextToken(context: Context): Token? {
        val tokenType = Token.charTable[context.current]

        val token =
            if (tokenType != null) Token(tokenType, context.current.toString())
            else if (context.current.isDigit() || context.current == '-') parseNumber(context, context.position)
            else if (context.current == '"') parseText(context, context.position)
            else if (context.current.isLetter()) parseKeyword(context)
            else if (context.current.isWhitespace()) return null
            else throw IllegalArgumentException("Invalid token at ${context.position}: ${context.current}")

        return token
    }

    private fun parseNumber(context: Context, startedAt: Int): Token {
        var hasDot = false
        return Token(Token.Type.NUMBER, context.buildStringUntil {
            if (!context.current.isDigit() && context.current != '-' && context.current != '.')
                return@buildStringUntil false

            if (context.current == '-' && startedAt != context.position)
                throw IllegalArgumentException("Invalid number chat at ${context.position}: ${context.current}")

            if (context.current == '.') {
                if (hasDot) throw IllegalArgumentException("Invalid number char at ${context.position}: ${context.current}")
                hasDot = true
            }

            true
        })
    }

    private fun parseText(context: Context, startedAt: Int): Token {
        var text = ""
        context.readUntil {
            if (context.current == '"') {
                return@readUntil startedAt == context.position
            }

            text += context.current
            true
        }

        return Token(Token.Type.TEXT, text)
    }

    private fun parseKeyword(context: Context) =
        Token(Token.Type.KEYWORD, context.buildStringUntil {
            context.current.isLetter() || context.current == '_'
        })

    private fun Context.buildStringUntil(consumer: () -> Boolean): String {
        var text = ""

        readUntil {
            if (!consumer()) return@readUntil false
            text += current
            true
        }

        previous()

        return text
    }

    data class Context(private val text: CharSequence) : AbstractConsumeContext<Char> {
        var position = 0

        override var current = text[position]

        override fun isExausted() = position >= text.length - 1

        override fun next() {
            position++
            current = text[position]
        }

        override fun previous() {
            position--
            current = text[position]
        }
    }
}