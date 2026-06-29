package io.github.xffc.codingbase.webeditor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import io.github.xffc.codingbase.webeditor.model.Node
import io.github.xffc.codingbase.webeditor.model.Pin
import kotlinx.serialization.Serializable

private val ZOOM_BOUNDS = 0.2f..5f

@Composable
fun InfiniteCanvas(modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()

    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }

    val nodes: MutableList<Node> = listOf(
        Node("Player join", Offset.Zero, listOf(Pin.nextNode())),
        Node("World start", Offset(300f, 150f), listOf(Pin.nextNode()))
    ).toMutableStateList()

    Canvas(
        modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    offset += pan
                }
            }

            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val new = handleScroll(offset, scale) ?: continue
                        offset = new.first
                        scale = new.second
                    }
                }
            }
    ) {
        drawGrid(offset, scale)

        withTransform({
            translate(offset.x, offset.y)
            scale(scale, scale, Offset.Zero)
        }) {
            nodes.forEach { drawNode(it, textMeasurer) }
        }
    }
}

private fun DrawScope.drawNode(node: Node, textMeasurer: TextMeasurer) {
    drawRoundRect(
        color = Color.DarkGray.copy(0.7f),
        topLeft = node.position,
        size = Size(Node.WIDTH, Node.TITLE_HEIGHT)
    )

    val titleText = textMeasurer.measure(
        text = node.name,
        style = TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    )

    drawText(
        titleText,
        topLeft = Offset(
            node.position.x + Node.WIDTH / 2 - titleText.size.width / 2,
            node.position.y + Node.TITLE_HEIGHT / 2 - titleText.size.height / 2,
        )
    )

    drawPins(node, textMeasurer)
}

private fun DrawScope.drawPins(node: Node, textMeasurer: TextMeasurer) {
    node.pins.forEachIndexed { index, pin ->
    }
}

private fun DrawScope.drawGrid(offset: Offset, scale: Float) {
    val step = 50f * scale

    fun drawSide(start: Float, size: Float, startOffset: (Float) -> Offset, endOffset: (Float) -> Offset) {
        var n = start
        while (n <= size) {
            drawLine(
                Color.Gray,
                startOffset(n),
                endOffset(n),
                strokeWidth = 1f
            )

            n += step
        }
    }

    drawSide(
        ((offset.x % step) + step) % step,
        size.width,
        { Offset(it, 0f) },
        { Offset(it, size.height) }
    )

    drawSide(
        ((offset.y % step) + step) % step,
        size.height,
        { Offset(0f, it) },
        { Offset(size.width, it) }
    )
}

private suspend fun AwaitPointerEventScope.handleScroll(offset: Offset, scale: Float): Pair<Offset, Float>? {
    val event = awaitPointerEvent()
    if (event.type != PointerEventType.Scroll) return null

    val change = event.changes.first()
    val delta = change.scrollDelta
    val cursor = change.position

    val zoomFactor =
        if (delta.y > 0) .9f
        else 1.1f

    val newScale = (scale * zoomFactor).coerceIn(ZOOM_BOUNDS)
    val newOffset = cursor - (cursor - offset) * (newScale / scale)

    return newOffset to newScale
}