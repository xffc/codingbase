package io.github.xffc.codingbase.editor.infinitecanvas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import io.github.xffc.codingbase.editor.util.OffsetSerializer.SerializableOffset
import kotlinx.serialization.Serializable

class ScreenNode(position: Offset) {
    var position by mutableStateOf(position)
}

@Serializable
sealed interface NodeData {
}