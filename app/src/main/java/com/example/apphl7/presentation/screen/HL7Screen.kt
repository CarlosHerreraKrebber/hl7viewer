package com.example.apphl7.presentation.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apphl7.presentation.viewmodel.HL7ViewModel

@Composable
fun HL7Screen(viewModel: HL7ViewModel,context: Context) {
    val message = viewModel.parsedMessage

    LaunchedEffect(Unit) {
        viewModel.loadHL7(context)
    }

// do some basic kotlin and compose tuts
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Befunde", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (message == null) {
            Text("No HL7 content parsed yet.")
        } else {
            val personID = message.pid
            LazyColumn {
                item {
                    Text(text = personID.patientName, style = MaterialTheme.typography.titleMedium)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }


    }

}
