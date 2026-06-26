package io.github.xffc.codingbase.compiler

class Parser(
    val methods: Map<String, MethodEntry>
) {

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