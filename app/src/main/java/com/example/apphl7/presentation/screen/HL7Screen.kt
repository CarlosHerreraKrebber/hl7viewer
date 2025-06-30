package com.example.apphl7.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apphl7.R
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.domain.model.PID
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

fun String?.costumSplit(): List<String> = this?.trim()?.split("^") ?: emptyList()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HL7Screen(navController: NavController, viewModel: HL7ViewModel) {

    /*
    Main Screen Of the Application
     */
    val message = viewModel.parsedMessage
    val localcontex = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadHL7(localcontex)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var selectedObservation by remember { mutableStateOf<OBRGroup?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("HL7 Report")
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back navigation */ }) {
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

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if (message == null) {
                Text("No HL7 content parsed yet.")
            } else {
                val hl7Type = message.msh?.messageType?.costumSplit()?.joinToString(" ")

                val personID = message.pid
                val validObservations =
                    message.observations.obrGroup.filter { it.obx.first().valueType == "CE" }

                LazyColumn {

                    item {
                        Header(hl7Type, personID, message)
                    }// Header with pat Infos

                    if (validObservations.isNotEmpty()) {
                        item {
                            CountAuffaelligeBefunde(validObservations, navController)
                        } // Show the critical findings
                        items(
                            validObservations,
                            key = { observation ->
                                observation.obr?.setId?.toIntOrNull() ?: observation.hashCode()
                            }
                        ) { observation ->
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
                                        }),

                                ) { // TODO Refeactor card Content
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
                        } // show all findings
                    }
                }
            }
        }
    }
}

@Composable
private fun CountAuffaelligeBefunde(
    validObservations: List<OBRGroup>,
    navController: NavController
) {

    val abnormalCount = validObservations.count() { obs ->
        obs.obx.firstOrNull()?.abnormalFlags?.isNotBlank() == true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
        //verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.Center // Center text inside column
    ) {

        TextButton(onClick = {
            navController.navigate("AuffaelligebefundeScreen")
        }) {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .background(
                        color = Color(0xFFFFCDD2), // Light red background
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painterResource(id = R.drawable.iconhighprio),
                    contentDescription = "Warning",
                    tint = Color(0xFFD32F2F), // Dark red tint
                    modifier = Modifier
                        .size(24.dp),

                    )
            }


            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    // TODO add to count when the given Value is Higher than the Range
                    text = "${abnormalCount} auffällige Befunde",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = "Überprüfung Notwendig",
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

    }
}

@Composable
private fun Header(
    hl7Type: String?,
    personID: PID?,
    message: HL7Message
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp
            )
    ) {
        Column() {
            Text(
                "Report $hl7Type",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            personID?.let {
                Text(
                    text = it.patientName.costumSplit().joinToString(" "),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White

                )
                Text(
                    text = "\u2022 Test Subject",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFFF781F)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                // .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(8.dp))
                //.padding(12.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Geburtstag",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White

                )
                Text(
                    text = "Tagesbuchungsnummer",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White

                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                // .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(8.dp))
                //.padding(12.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val birthday = personID?.birthDate
                val test = message.msh?.dateTimeOfMessage.toString()

                Text(
                    text = LocalDate.parse(
                        birthday.toString(),
                        DateTimeFormatter.ofPattern("yyyyddMM")
                    )
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    color = Color.White
                )
                message.msh?.messageControlId?.let {
                    Text(
                        text = it.replace(test, ""),
                        color = Color.White
                    )
                }
            }
        }
        //Spacer(modifier = Modifier.height(16.dp))
    }
}

