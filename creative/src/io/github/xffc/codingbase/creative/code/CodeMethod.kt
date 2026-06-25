package io.github.xffc.codingbase.creative.code

import io.github.classgraph.ClassGraph

sealed class CodeMethod<T> {
    val id = this::class.simpleName!!
        .removeSuffix("Method")
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .lowercase()

    abstract fun execute(context: CodeContext): T

    abstract class Action : CodeMethod<Unit>()
    abstract class Condition : CodeMethod<Boolean>()

    companion object {
        val registry = ClassGraph()
            .enableClassInfo()
            .acceptPackages(CodeMethod::class.java.packageName + ".methods")
            .scan()
            .use { result ->
                result.getSubclasses(CodeMethod::class.java)
                    .mapNotNull { classInfo ->
                        if (classInfo.isAbstract || classInfo.isInterface) return@mapNotNull null
                        classInfo.loadClass().kotlin.objectInstance as? CodeMethod<*>
                    }
                    .associateBy { it.id }
            }
    }
}