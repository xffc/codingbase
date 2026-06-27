package io.github.xffc.codingbase.creative.code.methods.variable

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption.ValueOption.Companion.getValue
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.export.OptionType

@Suppress("unused")
object VariableEqualsMethod: CodeMethod.Condition() {
    val firstValueOption = CodeMethodOption.ValueOption<CodeValue>("first", OptionType.ANY)
    val secondValueOption = CodeMethodOption.ValueOption<CodeValue>("second", OptionType.ANY)

    override val options = listOf(firstValueOption, secondValueOption)

    override fun execute(context: CodeContext, arguments: CodeBlock.Arguments): Boolean {
        val first = firstValueOption.getValue(context, arguments)
        val second = secondValueOption.getValue(context, arguments)
        return first == second
    }
}