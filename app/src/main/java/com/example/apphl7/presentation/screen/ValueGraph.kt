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
    /* Draws the Value in a Graph in X which is 3 way colored after
    value referance range and value is Markt in the graph
      */

    val maxValue = 100f

    val scaled = (value / maxValue).coerceIn(0f, 1f)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Enough space for line + text
            .padding(
                start = 8.dp,
                end = 8.dp
            )
    ) {
        // TODO Correct the scaling for proper drawing
        // TODO Maybe evenrefactor the scaling into the data class

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

        // TODO Add a propper Value marker for the Graph
        val valueColor = if (value in lowerDev..upperDev) Color.Green else Color.Red

        drawCircle(
            color = valueColor,
            radius = 10f,
            center = Offset(valueX, lineHeight)
        )

    }
}