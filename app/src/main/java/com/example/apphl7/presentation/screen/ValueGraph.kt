package com.example.apphl7.presentation.screen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ValueGraph(value: Float, lowerDev: Float, upperDev: Float) {
    // Custom Line with Value Indicator

    val maxValue = 100f // Replace with actual max if known
    val scaled = (value/ maxValue).coerceIn(0f, 1f)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Enough space for line + text
            .padding(start = 8.dp,
            end =8.dp)
    ) {
        val lineHeight = size.height / 2

        val rangeMin = 0f
        val rangeMax = 100f

        val scale = size.width / (rangeMax - rangeMin)

        // Normalize positions
        val lowX = (lowerDev - rangeMin) * scale
        val upX = (upperDev - rangeMin) * scale
        val valueX = (value - rangeMin) * scale

        // Background bars
        drawLine(
            color = Color.Green,
            start = Offset(0f, lineHeight),
            end = Offset(lowX, lineHeight),
            strokeWidth = 8f
        )

        drawLine(
            color = Color(0xFFFF781F),
            start = Offset(lowX, lineHeight),
            end = Offset(upX, lineHeight),
            strokeWidth = 8f
        )

        drawLine(
            color = Color.Red,
            start = Offset(upX, lineHeight),
            end = Offset(size.width, lineHeight),
            strokeWidth = 8f
        )

        // Draw value indicator
        val valueColor = if (value in lowerDev..upperDev) Color.Green else Color.Red

        drawCircle(
            color = valueColor,
            radius = 10f,
            center = Offset(valueX, lineHeight)
        )




//        val scale = size.width/(upperDev - lowerDev)
//
//        drawLine(
//            color = Color.Green,
//            start = Offset(0f, lineHeight),
//            end = Offset(size.width * (lowerDev *scale), lineHeight),
//            strokeWidth = 8f
//        )
//
//        drawLine(
//            color = Color(0xFFFF781F),
//            start = Offset(size.width * (lowerDev*scale), lineHeight),
//            end = Offset(size.width * (upperDev*scale), lineHeight),
//            strokeWidth = 8f
//        )
//
//        drawLine(
//            color = Color.Red,
//            start = Offset(size.width * (upperDev*scale), lineHeight),
//            end = Offset(size.width, lineHeight),
//            strokeWidth = 8f
//        )

//        val iconWidth = 60f
//        val iconHeight = 60f
//        val pointerHeight = 10f
//
//        val iconX = ((size.width / (upperDev - lowerDev) * (value - lowerDev)).coerceIn(iconWidth / 2, size.width - iconWidth / 2))
//        val iconY = lineHeight - iconHeight - pointerHeight - 4f
//        // Draw speech bubble-like icon
//        val path = Path()
//        path.apply {
//            // Rounded rectangle
//            addRoundRect(
//                RoundRect(
//                    rect = Rect(
//                        left = iconX - iconWidth / 2,
//                        top = iconY,
//                        right = iconX + iconWidth / 2,
//                        bottom = iconY + iconHeight
//                    ),
//                    cornerRadius = CornerRadius(12f, 12f)
//                )
//            )
//            moveTo(iconX - 10f, iconY + iconHeight)
//            lineTo(iconX, iconY + iconHeight + pointerHeight)
//            lineTo(iconX + 10f, iconY + iconHeight)
//            close()
//        }
//
//        drawPath(
//            path = path,
//            color = Color.Blue
//        )
//
//        val text = "%.2f".format(value)
//        val paint = Paint().apply {
//            textAlign = Paint.Align.CENTER
//            textSize = 28f
//            color = android.graphics.Color.WHITE
//            isAntiAlias = true
//        }
//
//        val fontMetrics = paint.fontMetrics
//        val textHeight = fontMetrics.descent - fontMetrics.ascent
//        val textY = iconY + iconHeight / 2 + textHeight / 2 - fontMetrics.descent
//
//
//        drawContext.canvas.nativeCanvas.drawText(
//            text,
//            textHeight,
//            textY, // Raise text slightly above line
//            paint
//        )
//
//
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
//            end = Offset((size.width / (upperDev - lowerDev) * (value - lowerDev)).coerceIn(0f,size.width), lineHeight),
//            strokeWidth = 8f
//        )


    }
}