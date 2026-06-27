package io.github.xffc.codingbase.compiler

data class Token(
    val type: Type,
    val text: String
) {
    enum class Type(val char: Char? = null) {
        KEYWORD, TEXT, NUMBER,
        INVERT('!'),
        COMMA(','), COLON(':'),
        LBRACE('{'), RBRACE('}'),
        LPAREN('('), RPAREN(')'),
    }

    companion object {
        val charTable = HashMap(
            Type.entries
            .filter { it.char != null }
            .associateBy { it.char }
        )
    }
}