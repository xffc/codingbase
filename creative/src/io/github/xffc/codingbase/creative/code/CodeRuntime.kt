package io.github.xffc.codingbase.creative.code

import io.github.xffc.codingbase.creative.code.events.CreativeEvent
import io.github.xffc.codingbase.creative.worlds.state.PlayState
import io.github.xffc.codingbase.data.CodeBlock
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.bukkit.entity.Entity
import java.io.File
import java.util.Collections

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

    private val contexts = Collections.synchronizedList(mutableListOf<CodeContext>())

    val globalVariables: CodeValue.Variables = hashMapOf()

    val savedVariables: CodeValue.Variables = File(state.world.instance.worldFolder, "variables.json").let { file ->
        if (file.createNewFile()) {
            val default = hashMapOf<String, CodeValue>()
            file.writeText(Json.encodeToString(default))
            return@let default
        }

        Json.decodeFromString(file.readText())
    }

    fun runEvent(event: CreativeEvent, source: Entity? = null, target: Entity? = null) = events[event]?.map {
        state.scope.launch {
            runCode(createContext(it.body, TargetSelector(source, target)))
        }
    }

    fun runCode(context: CodeContext) {
        while (!context.isStopped) {
            context.step()
        }
    }

    fun createContext(
        body: CodeBlock.Body,
        selector: TargetSelector,
        localVariables: CodeValue.Variables = hashMapOf()
    ): CodeContext {
        val context = CodeContext(
            this,
            body.listIterator(),
            selector,
            localVariables
        )

        contexts.add(context)
        return context
    }

    fun destroy() {
        File(state.world.instance.worldFolder, "variables.json").writeText(
            Json.encodeToString(savedVariables)
        )

        contexts.forEach { it.stop() }

        state.scope.cancel()
    }
}