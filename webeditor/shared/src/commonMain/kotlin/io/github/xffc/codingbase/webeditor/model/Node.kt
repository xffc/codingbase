package io.github.xffc.codingbase.webeditor.model

import androidx.compose.ui.geometry.Offset
import io.github.xffc.codingbase.webeditor.util.SerializableOffset
import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val name: String,
    var position: SerializableOffset = Offset.Zero,
    var pins: List<Pin>
) {
    companion object {
        const val WIDTH = 150f
        const val TITLE_HEIGHT = 50f
    }
}