package com.example.apphl7.presentation.screen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun ValueGraph(value: Float, range: String) {
    // Custom Line with Value Indicator
    var lowerDev = 0f
    var upperDev = 75f
    val maxValue = 100f // Replace with actual max if known
    val percentage = (value / maxValue).coerceIn(0f, 1f)
    when { //TODO move this also to the data class range value ranging funk
        range.contains("-") -> {
            val parts = range.split("-")
            lowerDev = (parts.firstOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.25f
            upperDev = (parts.lastOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.75f
        }

        range.contains(">") -> {
            val parts = range.split(">")
            lowerDev = (parts.lastOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.25f
            upperDev = (parts.firstOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.75f
        }

        range.contains("<") -> {
            val parts = range.split("<")
            lowerDev = (parts.firstOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.25f
            upperDev = (parts.lastOrNull()?.toFloat()?.div(maxValue))?.coerceIn(0f, 1f) ?: 0.75f
        }
    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Enough space for line + text
    ) {
        val lineHeight = size.height / 2


        drawLine(
            color = Color.Green,
            start = Offset(0f, lineHeight),
            end = Offset(size.width * lowerDev, lineHeight),
            strokeWidth = 8f
        )

        drawLine(
            color = Color(0xFFFF781F),
            start = Offset(size.width * lowerDev, lineHeight),
            end = Offset(size.width * upperDev, lineHeight),
            strokeWidth = 8f
        )

        drawLine(
            color = Color.Red,
            start = Offset(size.width * upperDev, lineHeight),
            end = Offset(size.width, lineHeight),
            strokeWidth = 8f
        )

        val iconWidth = 60f
        val iconHeight = 60f
        val pointerHeight = 10f

        val iconX = (size.width * percentage).coerceIn(iconWidth / 2, size.width - iconWidth / 2)
        val iconY = lineHeight - iconHeight - pointerHeight - 4f
        // Draw speech bubble-like icon
        val path = Path()
        path.apply {
            // Rounded rectangle
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        left = iconX - iconWidth / 2,
                        top = iconY,
                        right = iconX + iconWidth / 2,
                        bottom = iconY + iconHeight
                    ),
                    cornerRadius = CornerRadius(12f, 12f)
                )
            )
            moveTo(iconX - 10f, iconY + iconHeight)
            lineTo(iconX, iconY + iconHeight + pointerHeight)
            lineTo(iconX + 10f, iconY + iconHeight)
            close()
        }

        drawPath(
            path = path,
            color = Color.Blue
        )

        val text = "%.2f".format(value)
        val paint = Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 28f
            color = android.graphics.Color.WHITE
            isAntiAlias = true
        }

        val fontMetrics = paint.fontMetrics
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        val textY = iconY + iconHeight / 2 + textHeight / 2 - fontMetrics.descent


        drawContext.canvas.nativeCanvas.drawText(
            text,
            textHeight,
            textY, // Raise text slightly above line
            paint
        )


//        // Background line
//        drawLine(
//            color = Color.LightGray,
//            start = Offset(0f, lineHeight),
//            end = Offset(size.width, lineHeight),
//            strokeWidth = 8f
//        )
//
//        // Foreground line up to percentage
//        drawLine(
//            color = Color.Green,
//            start = Offset(0f, lineHeight),
//            end = Offset(size.width * percentage, lineHeight),
//            strokeWidth = 8f
//        )

//        drawCircle(
//            color = Color.Green,
//            center = Offset(value,lineHeight)
//        )

        //  HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}