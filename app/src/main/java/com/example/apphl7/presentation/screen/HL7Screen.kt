package com.example.apphl7.presentation.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Outline.Rectangle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphl7.domain.model.NTE
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import org.w3c.dom.Text

@Composable
fun HL7Screen(viewModel: HL7ViewModel,context: Context) {
    val message = viewModel.parsedMessage

    LaunchedEffect(Unit) {
        viewModel.loadHL7(context)
    }


    Column(modifier = Modifier
        .padding(16.dp),
        ) {
        if (message != null) {
            Text(
                "Report " + message.msh?.messageType.toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (message == null) {
            Text("No HL7 content parsed yet.")
        } else {
            val personID = message.pid
            LazyColumn {
                item {
                    personID?.let {
                        Text(
                            text = it.patientName.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            val validObservations =
                message.observations.obrGroup.filter { it.obx.first().valueType == "CE" }
            LazyColumn {
                item {
                    Text(
                        text = "Anzahl der Befunde auffälliger: " + validObservations.size.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            LazyColumn(Modifier.padding(16.dp)
            ) {
                items(validObservations) {
                    it.let {
                        Text(
                            text = it.obx.first().observationId.toString(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    it.let { Text(
                        text = "current Value " + it.obx.first().observationValue.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    if (it.nte.isNotEmpty()) {
                        it.let {
                            Text(
                                text = "Info Notize: " + it.nte.mapNotNull { it.comment}.filter{it.isNotBlank()}
                                    .joinToString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                }

            }
        }


    }
}
