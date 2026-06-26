package io.github.xffc.codingbase.compiler

import kotlinx.serialization.json.Json
import kotlin.jvm.java

object CompilerApp {
    @JvmStatic
    fun main(vararg args: String) {
        val json = Json {
            prettyPrint = true
            encodeDefaults = true
            ignoreUnknownKeys = true
        }

        val tokens = Lexer.getTokens(
            this::class.java
                .getResource("/debug_code.cb")!!
                .readText()
        )

        println(tokens.joinToString(" ") { "${it.type.name}(${it.text})" })

        val code = Parser.getBlocks(tokens)

        println(json.encodeToString(code))
    }
}