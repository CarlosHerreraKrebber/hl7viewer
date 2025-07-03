package com.example.apphl7.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.apphl7.R
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuffaelligebefundeScreen(navController: NavController, viewModel: HL7ViewModel) {

    val auffaeObservs = viewModel.parsedMessage?.getAufaelligeBefunde()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val localcontex = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var selectedObservation by remember { mutableStateOf<OBRGroup?>(null) }
// TODO fixing the navBack to Main screen
//  Problem is pretty sure to be on the Bottom Sheet selected Observation -> Nullpionter
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Auffällige Befunde")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconarrowback),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Overflow */ }) {
                        Icon(
                            painterResource(id = R.drawable.iconmorevet),
                            contentDescription = "More options"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .background(Color.Blue)
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn {
                if (auffaeObservs!!.isNotEmpty()) {
                    items(  items = auffaeObservs) { observation ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 16.dp
                                )
                                .clickable(
                                    onClick = {
                                        val key = observation.obr?.setId?.toIntOrNull()
                                            ?: observation.hashCode()
                                        if (key != -1) {
                                            coroutineScope.launch {
                                                selectedObservation = observation
                                                sheetState.show()
                                            }

                                        }
                                    }
                                ),

                            ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(

                                    text = observation.obx.first().observationId.costumSplit()
                                        .drop(1).joinToString(" ").replace(
                                            Regex("\\s+"), " "
                                        ),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "in " + observation.obx.first().units.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                var currentVal =
                                    observation.obx.first().observationValue.toString()
                                val range = observation.obx.first().rangeSplit()
                                var valueDanger = false
                                // TODO Impl a real marker to find high values if abnormal flag is empty
                                // TODO do the range parsing in the Domain layer / properply giving the range
                                // TODO load range in int list 2x1 1. lower, 2. upper bound
                                Text(
                                    text = "Current Value " + currentVal,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                                ValueGraph(currentVal.toFloat(), range.get(0), range.get(1))

                                Row() {
                                    if (observation.nte.isNotEmpty()) {
                                        NotizBox()
                                    }
                                    if ((observation.obx.firstOrNull()?.abnormalFlags?.isNotBlank() == true)
                                        || valueDanger
                                    ) {
                                        AttentioniBox()
                                    }
                                }
                            }

                            if (selectedObservation != null) {
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        coroutineScope.launch {
                                            sheetState.hide()
                                        }.invokeOnCompletion {
                                            selectedObservation = null
                                        }
                                    },
                                    sheetState = sheetState
                                ) {
                                    DetailContent(selectedObservation!!, localcontex)
                                }
                            }
                        }
                    }
                }
            } // show all findings

        }
    }
}






