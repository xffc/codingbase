package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.creative.code.events.CreativeEvent
import io.github.xffc.codingbase.creative.worlds.state.PlayState
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.CodeBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodeRuntime(
    val state: PlayState,
    code: List<CodeBlock.StartBlock>
) {
    private val events = code
        .filterIsInstance<CodeBlock.StartBlock.EventBlock>()
        .groupBy { CreativeEvent.registry[it.id]!! } // todo че нибудь придумать с экзепшнами

    private val functions = code
        .filterIsInstance<CodeBlock.StartBlock.FunctionBlock>()
        .associateBy { it.id }

    val scope = CoroutineScope(Dispatchers.IO)

    fun runEvent(event: CreativeEvent) = events[event]?.map {
        scope.launch {
            runCode(it.body)
        }
    }

    private fun runCode(body: CodeBody) {
        val context = createContext(body)

        while (!context.isStopped) {
            context.step()
        }
    }

    fun createContext(body: CodeBody): CodeContext =
        CodeContext(this, body.listIterator())
}