package io.github.xffc.codingbase.webeditor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform