package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.data.CodeBlock

class CodeContext(
    val runtime: CodeRuntime,
    private val bodyIterator: ListIterator<CodeBlock.MethodBlock>
) {
    var isStopped: Boolean = false
        private set

    fun step() {
        if (!bodyIterator.hasNext()) return stop()

        when (val block = bodyIterator.next()) {
            is CodeBlock.MethodBlock.ActionBlock -> {
                execute<CodeMethod.Action, Unit>(block)
            }

            is CodeBlock.MethodBlock.ConditionBlock -> {
                val result = execute<CodeMethod.Condition, Boolean>(block)

                val elseBlock = peek { it is CodeBlock.MethodBlock.ElseBlock } as? CodeBlock.MethodBlock.ElseBlock

                val nextBody: CodeBlock.Body =
                    if (result) block.body
                    else elseBlock?.body ?: return

                runtime.runCode(runtime.createContext(nextBody))
            }

            else -> throw IllegalArgumentException("Invalid block ${block.id}")
        }
    }

    private fun peek(consumer: (CodeBlock.MethodBlock) -> Boolean): CodeBlock.MethodBlock? {
        if (!bodyIterator.hasNext()) return null
        val next = bodyIterator.next()

        if (!consumer(next)) {
            bodyIterator.previous()
            return null
        }

        return next
    }

    private inline fun <reified M: CodeMethod<T>, T> execute(block: CodeBlock.MethodBlock): T {
        val method = CodeMethod.registry[block.id] ?: throw IllegalArgumentException("Method ${block.id} not found")
        if (method !is M) throw IllegalArgumentException("Method ${block.id} is not ${M::class.simpleName}")
        return method.execute(this, block.arguments)
    }

    fun stop() {
        isStopped = true
    }
}