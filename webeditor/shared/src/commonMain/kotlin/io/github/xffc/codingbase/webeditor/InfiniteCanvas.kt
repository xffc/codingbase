package io.github.xffc.codingbase.webeditor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import io.github.xffc.codingbase.webeditor.model.EventNode
import io.github.xffc.codingbase.webeditor.model.Node
import kotlin.uuid.Uuid

@Composable
fun InfiniteCanvas() {
    val textMeasurer = rememberTextMeasurer()

    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }

    var draggingNodeId by remember { mutableStateOf<Uuid?>(null) }
    var dragStartOffset by remember { mutableStateOf(Offset.Zero) }

    var nodes = remember {
        listOf(
            EventNode(Uuid.random(), Offset.Zero),
            EventNode(Uuid.random(), Offset(300f, 300f))
        ).map { it.id to it }.toMutableStateMap()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()

                        if (event.type != PointerEventType.Scroll) continue

                        val change = event.changes.first()
                        val zoomFactor = if (change.scrollDelta.y > 0) 0.9f else 1.1f
                        val newScale = (scale * zoomFactor).coerceIn(0.2f, 5f)

                        offset = change.position - (change.position - offset) * (newScale / scale)
                        scale = newScale
                    }
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    if (draggingNodeId == null) {
                        offset += pan
                    }

                    scale = (scale * zoom).coerceIn(0.1f, 10f)
                }
            }
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)

                    val worldPos = (down.position - offset) / scale

                    val hitNode = nodes.values.lastOrNull { node ->
                        val xBounds = node.position.x..(node.position.x + Node.WIDTH)
                        val yBounds = node.position.y..(node.position.y + node.height)
                        worldPos.x in xBounds && worldPos.y in yBounds
                    } ?: return@awaitEachGesture

                    draggingNodeId = hitNode.id
                    var prevPosition = down.position

                    drag(down.id) { change ->
                        change.consume()
                        val delta = change.position - prevPosition
                        prevPosition = change.position
                        hitNode.position += delta / scale
                    }

                    draggingNodeId = null
                }
            }
    ) {
        drawGrid(offset, scale)

        withTransform({
            translate(offset.x, offset.y)
            scale(scale, scale, pivot = Offset.Zero)
        }) {
            nodes.forEach { entry ->
                entry.value.render(this, textMeasurer, draggingNodeId == entry.key)
            }
        }
    }
}

fun DrawScope.drawGrid(offset: Offset, scale: Float, step: Float = 50f) {
    val stepPx = step * scale

    val startX = ((offset.x % stepPx) + stepPx) % stepPx
    val startY = ((offset.y % stepPx) + stepPx) % stepPx

    var x = startX
    while (x <= size.width) {
        drawLine(
            color = Color.DarkGray,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = 1f
        )

        x += stepPx
    }

    var y = startY
    while (y <= size.height) {
        drawLine(
            color = Color.DarkGray,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1f
        )

        y += stepPx
    }
}