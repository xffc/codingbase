package io.github.xffc.codingbase.creative.code.options

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.toValue
import io.github.xffc.codingbase.data.CodeBlock

data class ValueOption<T: CodeValue>(
    val name: String,
    val required: Boolean = true,
    val defaultValue: T? = null
): CodeMethodOption {
    companion object {
        inline fun <reified T: CodeValue> ValueOption<T>.getValue(
            context: CodeContext,
            arguments: CodeBlock.Arguments
        ): T? {
            val argument = arguments[name]

            if (argument == null) {
                if (required) throw IllegalArgumentException("Argument $name is required, but not present")
                return defaultValue
            }

            return argument.toValue(context) as? T
                ?: throw IllegalArgumentException("Argument $name is not an instance of ${T::class.simpleName}")
        }
    }
}