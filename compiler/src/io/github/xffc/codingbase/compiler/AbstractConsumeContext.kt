package io.github.xffc.codingbase.compiler

interface AbstractConsumeContext<T> {
    var current: T

    fun isExausted(): Boolean

    fun next()
    fun previous()

    fun readUntil(consumer: () -> Boolean) {
        while (true) {
            if (!consumer() || isExausted()) break
            next()
        }
    }
}