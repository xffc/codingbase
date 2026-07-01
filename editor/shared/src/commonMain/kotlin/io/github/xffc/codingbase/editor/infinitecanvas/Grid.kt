package io.github.xffc.codingbase.editor.infinitecanvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawGrid(offset: Offset, scale: Float) {
    val step = 50f * scale

    val startX = ((offset.x % step) + step) % step
    val startY = ((offset.y % step) + step) % step

    drawAxisLines(
        step,
        startX,
        size.width,
        { Offset(it, 0f) },
        { Offset(it, size.height) }
    )

    drawAxisLines(
        step,
        startY,
        size.height,
        { Offset(0f, it) },
        { Offset(size.width, it) }
    )
}

private fun DrawScope.drawAxisLines(
    step: Float,
    start: Float,
    axisLength: Float,
    startOffset: (Float) -> Offset,
    endOffset: (Float) -> Offset,
) {
    var n = start
    while (n <= axisLength) {
        drawLine(Color.Gray, startOffset(n), endOffset(n))
        n += step
    }
}