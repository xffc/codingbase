package io.github.xffc.codingbase.creative.code.events

sealed class CreativeEvent {
    val id = this::class.simpleName!!
        .removeSuffix("Event")
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .lowercase()

    sealed interface Player
    sealed interface World

    companion object {
        val registry = CreativeEvent::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .associateBy { it.id }
    }
}