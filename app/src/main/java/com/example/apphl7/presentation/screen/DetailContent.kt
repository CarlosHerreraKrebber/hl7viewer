package com.example.apphl7.presentation.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apphl7.R
import com.example.apphl7.domain.model.OBRGroup


@Composable
fun DetailContent(
    observation: OBRGroup,
    lokalcontex: Context,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        // val setId = observation.obr?.setId?.toIntOrNull() ?: return@clickable
        //navController.navigate("BottomDetail/$setId") })  ,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(

                text = observation.obx.first().observationId.costumSplit()
                    .drop(1).joinToString(" ").replace(
                        Regex("\\s+"), " "
                    ),
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.material3.Text(
                text = "in " + observation.obx.first().units.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.padding(8.dp))

            var currentVal = observation.obx.first().observationValue.toString()
            val range = observation.obx.first().rangeSplit()
            var valueDanger =
                false // TODO Impl a real marker to find high values if abnormal flag is empty
            // TODO do the range parsing in the Domain layer / properply giving the range
            // TODO load range in int list 2x1 1. lower, 2. upper bound
            Text(
                text = "current Value " + currentVal,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(4.dp)
            ) { ValueGraph(currentVal.toFloat(), range.get(0), range.get(1)) }

            if (observation.nte.isNotEmpty()) {
                NotizBox(detailSheet = true)
                Text(
                    text = "Info Notiz: " + observation.nte.mapNotNull {
                        it.comment?.replace(
                            Regex("\\s+"), " "
                        )
                    }.filter { it.isNotBlank() }
                        .joinToString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        start = 8.dp,
                        end = 8.dp
                    )
                    .wrapContentSize(Alignment.Center),

                )
            {
                HyperLinkButton(observation, lokalcontex)
            }
        }
    }
}

@Composable
private fun HyperLinkButton(
    observation: OBRGroup,
    lokalcontex: Context
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        TextButton(onClick = {
            val query = Uri.encode(
                observation.obx.first().observationId.costumSplit()
                    .drop(1).joinToString(" ").replace(
                        Regex("\\s+"), " "
                    )
            )
            val url = "https://www.google.com/search?q=$query"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            lokalcontex.startActivity(intent)
        }) {
            Icon(painterResource(id = R.drawable.iconhyperlink), contentDescription = "Search")
            Text(
                "Weitere Informationen",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}