package io.github.xffc.codingbase.compiler

interface AbstractConsumeContext<T> {
    var current: T

    fun isExausted(): Boolean

    fun next()
    fun previous()

    fun readUntil(manualRead: Boolean = false, consumer: () -> Boolean) {
        while (true) {
            if (!consumer() || isExausted()) break
            if (!manualRead) next()
        }
    }
}