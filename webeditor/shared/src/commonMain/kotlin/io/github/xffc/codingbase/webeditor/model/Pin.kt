package io.github.xffc.codingbase.webeditor.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Pin(
    var text: String,
    var isInput: Boolean,
    var connectedTo: Uuid? = null
) {
    companion object {
        const val SIZE = 8f

        fun nextNode(connectedTo: Uuid? = null): Pin =
            Pin("Next", false, connectedTo)
    }
}