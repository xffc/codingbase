package io.github.xffc.codingbase.webeditor.model

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.uuid.Uuid

class EventNode(
    override val id: Uuid,
    position: Offset
): Node(position) {
    override fun DrawScope.render(
        textMeasurer: TextMeasurer,
        isSelected: Boolean
    ) {
        drawRoundRect(
            color = Color(0xFF1A1A1A),
            topLeft = position,
            size = Size(WIDTH, height),
            cornerRadius = CornerRadius(CORNER_RADIUS)
        )

        if (isSelected) {
            drawRoundRect(
                color = Color(0xFFFFC107),
                topLeft = position,
                size = Size(WIDTH, height),
                cornerRadius = CornerRadius(CORNER_RADIUS),
                style = Stroke(width = 2.5f)
            )
        }

        drawRoundRect(
            color = Color.DarkGray, // todo: header color
            topLeft = position,
            size = Size(WIDTH, HEADER_HEIGHT),
            cornerRadius = CornerRadius(CORNER_RADIUS)
        )

        drawRect(
            color = Color.DarkGray, // todo: header color
            topLeft = Offset(position.x, position.y + HEADER_HEIGHT / 2),
            size = Size(WIDTH, HEADER_HEIGHT / 2)
        )

        val titleLayout = textMeasurer.measure(
            text = "node", // todo: title
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        )

        drawText(
            textLayoutResult = titleLayout,
            topLeft = Offset(
                x = position.x + 12f,
                y = position.y + (HEADER_HEIGHT - titleLayout.size.height) / 2f
            )
        )

        drawLine(
            color = Color.Black.copy(alpha = 0.4f),
            start = Offset(position.x, position.y + HEADER_HEIGHT),
            end = Offset(position.x + WIDTH, position.y + HEADER_HEIGHT),
            strokeWidth = 1f
        )

        /* todo: pins
        node.inputs.forEachIndexed { i, pin ->
            drawPin(node, pin, i, textMeasurer)
        }
        node.outputs.forEachIndexed { i, pin ->
            drawPin(node, pin, i, textMeasurer)
        }*/
    }
}