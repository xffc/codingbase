package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.data.CodeBlock

class CodeContext(
    val runtime: CodeRuntime,
    private val body: ListIterator<CodeBlock.MethodBlock>
) {
    var isStopped: Boolean = false
        private set

    fun step() {
        if (body.hasNext()) return stop()

        val block = body.next()
        // todo: сделать.
    }

    fun stop() {
        isStopped = true
    }
}