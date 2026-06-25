package io.github.xffc.codingbase.creative.code.methods.player

import io.github.xffc.codingbase.creative.code.CodeContext
import io.github.xffc.codingbase.creative.code.CodeMethod

@Suppress("unused")
object SendMessageMethod: CodeMethod.Action() {
    override fun execute(context: CodeContext) {
        println("sending message to player")
    }
}