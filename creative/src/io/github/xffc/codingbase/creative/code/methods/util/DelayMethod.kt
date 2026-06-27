package io.github.xffc.codingbase.creative.code.methods.util

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption.ValueOption.Companion.getValue
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.export.OptionType
import kotlin.math.roundToLong

@Suppress("unused")
object DelayMethod: CodeMethod.Action() {
    val delayOption = CodeMethodOption.ValueOption<CodeValue.Number>("delay", OptionType.NUMBER)

    override val options = listOf(delayOption)

    override fun execute(context: CodeContext, arguments: CodeBlock.Arguments) {
        val delay = delayOption.getValue(context, arguments)!!
        Thread.sleep(delay.value.roundToLong())
    }
}