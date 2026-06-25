package io.github.xffc.codingbase.creative.code.methods.player

import io.github.xffc.codingbase.creative.CreativePlugin
import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.options.ValueOption
import io.github.xffc.codingbase.creative.code.options.ValueOption.Companion.getValue
import io.github.xffc.codingbase.data.CodeBlock
import net.kyori.adventure.text.Component

@Suppress("unused")
object SendMessageMethod: CodeMethod.Action() {
    val textOption = ValueOption("text", false, CodeValue.Text(Component.empty()))

    override val options = listOf(textOption)

    override fun execute(context: CodeContext, arguments: CodeBlock.Arguments) {
        val text = textOption.getValue(context, arguments)!!.value
        CreativePlugin.componentLogger.info(text)
    }
}