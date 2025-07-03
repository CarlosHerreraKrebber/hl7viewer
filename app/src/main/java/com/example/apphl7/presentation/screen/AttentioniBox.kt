package com.example.apphl7.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apphl7.R

/**
 * Creates a box with Attention Crisis icon and Erhöter Wert
 */
@Composable
fun AttentioniBox() {
    Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .wrapContentSize(Alignment.Center)
            .background(
                color = Color(0xFFFFCDD2),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),// inner padding
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.iconcrisesallert),
                contentDescription = "Warning Icon",
                tint = Color(0xFFD32F2F),
            )
            Text(
                text = "Erhöter Wert",
                color = Color(0xFFD32F2F),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}