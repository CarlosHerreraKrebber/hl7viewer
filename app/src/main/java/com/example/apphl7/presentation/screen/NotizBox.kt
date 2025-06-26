package com.example.apphl7.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotizBox(detailSheet: Boolean = false) {
    Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .wrapContentSize(Alignment.Center)
            .background(
                color = if (detailSheet) { Color(0x00ADE8F4) } else Color(0xFFADE8F4),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),// inner padding
        // contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info icon",
                tint = Color(0xFF0077B6)
            )
            Text(
                text = "Notiz",
                color = Color(0xFF0077B6),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}