package io.github.xffc.codingbase.creative.code.methods.variable

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.getContainer
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption.ArgumentOption.Companion.get
import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.CodeBlock
import io.github.xffc.codingbase.data.export.OptionType

@Suppress("unused")
object VariableExistsMethod: CodeMethod.Condition() {
    val variableOption = CodeMethodOption.ArgumentOption("variable", OptionType.VARIABLE)

    override val options = listOf(variableOption)

    override fun execute(context: CodeContext, arguments: CodeBlock.Arguments): Boolean {
        val variable = variableOption.get<CodeArgument.Variable>(arguments)
        return variable.name in variable.scope.getContainer(context)
    }
}