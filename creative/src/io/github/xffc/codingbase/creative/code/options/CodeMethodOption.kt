package io.github.xffc.codingbase.creative.code.options

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeValue
import io.github.xffc.codingbase.creative.code.toValue
import io.github.xffc.codingbase.data.CodeArgument
import io.github.xffc.codingbase.data.CodeBlock

sealed interface CodeMethodOption {
    val name: String
    val required: Boolean

    fun getArgument(arguments: CodeBlock.Arguments): CodeArgument? {
        val argument = arguments[name]

        if (argument == null) {
            if (required) throw IllegalArgumentException("Argument $name is required, but not present")
            return null
        }

        return argument
    }

    data class ArgumentOption(
        override val name: String
    ) : CodeMethodOption {
        override val required = true

        companion object {
            inline fun <reified T: CodeArgument> ArgumentOption.get(
                arguments: CodeBlock.Arguments
            ): T {
                return getArgument(arguments) as? T
                    ?: throw IllegalArgumentException("Argument $name is not an instance of ${T::class.simpleName}")
            }
        }
    }

    data class ValueOption<T : CodeValue>(
        override val name: String,
        override val required: Boolean = true,
        val defaultValue: T? = null
    ) : CodeMethodOption {
        companion object {
            inline fun <reified T : CodeValue> ValueOption<T>.getValue(
                context: CodeContext,
                arguments: CodeBlock.Arguments
            ): T? {
                val argument = getArgument(arguments) ?: return defaultValue

                return argument.toValue(context) as? T
                    ?: throw IllegalArgumentException("Argument $name is not an instance of ${T::class.simpleName}")
            }
        }
    }
}