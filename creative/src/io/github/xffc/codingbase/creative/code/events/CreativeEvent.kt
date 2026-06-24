package io.github.xffc.codingbase.creative.code.events

sealed class CreativeEvent {
    val id = this::class.simpleName!!
        .removeSuffix("Event")
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .lowercase()

    abstract class Player: CreativeEvent()
}