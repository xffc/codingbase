package io.github.xffc.codingbase.editor.infinitecanvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput

class CanvasInfo {
    var offset by mutableStateOf(Offset.Zero)
    var scale by mutableStateOf(1f)
}

@Composable
fun InfiniteCanvas(modifier: Modifier = Modifier) {
    val canvasInfo by derivedStateOf { CanvasInfo() }

    var isInitialized by remember { mutableStateOf(true) }

    // todo: moving, scaling and dragging
    Canvas(
        modifier.clipToBounds()
            .pointerInput(Unit) { handleMovement(canvasInfo) }
    ) {
        if (isInitialized) {
            isInitialized = false
            canvasInfo.offset = Offset(-size.width, -size.height)
        }

        drawGrid(canvasInfo.offset, canvasInfo.scale)

        withTransform({
            translate(canvasInfo.offset.x, canvasInfo.offset.y)
            scale(canvasInfo.scale, canvasInfo.scale, Offset.Zero)
        }) {
            // TODO: draw nodes
        }
    }
}

private suspend fun PointerInputScope.handleMovement(canvasInfo: CanvasInfo) {
    detectTransformGestures { _, pan, _, _ -> canvasInfo.offset += pan }
}