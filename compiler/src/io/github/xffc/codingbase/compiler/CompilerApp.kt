package io.github.xffc.codingbase.compiler

import io.github.xffc.codingbase.data.export.MethodEntry
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.jvm.java

// todo: сделать нормальные exceptions
object CompilerApp {
    private lateinit var dataFolder: File

    @JvmStatic
    fun main(vararg args: String) {
        dataFolder = File(args.getOrNull(0) ?: "./")

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

        println(tokens.mapIndexed { index, token -> "${token.type.name}:${index}(${token.text})" })

        val methods = unpackData<Map<String, MethodEntry>>("methods.json")
        val code = Parser(methods).getBlocks(tokens)

        println(json.encodeToString(code))
    }

    private inline fun <reified T> unpackData(fileName: String): T {
        val file = File(dataFolder, fileName)
        if (!file.exists()) throw IllegalArgumentException("Data file ${file.path} does not exist! Run server at least once to generate it")
        return Json.decodeFromString<T>(file.readText())
    }
}