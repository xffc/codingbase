package io.github.xffc.codingbase.creative.code.methods.variable

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.getContainer
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption.ArgumentOption.Companion.get
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption.ValueOption.Companion.getValue
import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.export.OptionType

@Suppress("unused")
object SetVariableMethod: CodeMethod.Action() {
    val variableOption = CodeMethodOption.ArgumentOption("variable", OptionType.VARIABLE)
    val valueOption = CodeMethodOption.ValueOption<CodeValue>("value", OptionType.ANY)

    override val options = listOf(variableOption, valueOption)

    override fun execute(context: CodeContext, arguments: CodeBlock.Arguments) {
        val variable = variableOption.get<CodeArgument.Variable>(arguments)
        val value = valueOption.getValue(context, arguments)!!
        variable.scope.getContainer(context)[variable.name] = value
    }
}