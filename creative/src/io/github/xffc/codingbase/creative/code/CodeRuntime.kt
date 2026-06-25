package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.creative.code.events.CreativeEvent
import io.github.xffc.codingbase.creative.worlds.state.PlayState
import io.github.xffc.codingbase.data.CodeBlock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.bukkit.entity.Entity

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

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun runEvent(event: CreativeEvent, source: Entity? = null, target: Entity? = null) = events[event]?.map {
        scope.launch {
            runCode(createContext(it.body, TargetSelector(source, target)))
        }
    }

    fun runCode(context: CodeContext) {
        while (!context.isStopped) {
            context.step()
        }
    }

    fun createContext(body: CodeBlock.Body, selector: TargetSelector): CodeContext =
        CodeContext(this, body.listIterator(), selector)
}