package io.github.xffc.codingbase.webeditor.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import kotlin.uuid.Uuid

sealed class Node(position: Offset) {
    abstract val id: Uuid

    var position: Offset by mutableStateOf(position)

    val height: Float
        get() {
            val rows = 0 // todo
            return HEADER_HEIGHT + PIN_ROW_HEIGHT * rows + 12f
        }

    fun render(scope: DrawScope, textMeasurer: TextMeasurer, isSelected: Boolean) =
        scope.render(textMeasurer, isSelected)

    protected abstract fun DrawScope.render(
        textMeasurer: TextMeasurer,
        isSelected: Boolean
    )

    companion object {
        const val WIDTH = 220f
        const val HEADER_HEIGHT = 48f
        const val PIN_ROW_HEIGHT = 28f
        const val PIN_RADIUS = 7f
        const val PIN_PADDING = 16f
        const val CORNER_RADIUS = 8f
        const val BORDER_WIDTH = 2f
    }
}