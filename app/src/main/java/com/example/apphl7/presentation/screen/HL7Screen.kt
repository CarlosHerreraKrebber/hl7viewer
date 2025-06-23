package com.example.apphl7.presentation.screen

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HL7Screen(viewModel: HL7ViewModel, context: Context) {
    val message = viewModel.parsedMessage

    LaunchedEffect(Unit) {
        viewModel.loadHL7(context)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    fun String?.costumSplit(): List<String> = this?.trim()?.split("^") ?: emptyList()

    Scaffold(
        topBar = {
           TopAppBar(
                title = {
                    Text("HL7 Report")
               },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Overflow */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    )
                    ,
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


            //Spacer(modifier = Modifier.height(8.dp))

            if (message == null) {
                Text("No HL7 content parsed yet.")
            } else {

                val hl7Type = message.msh?.messageType?.costumSplit()?.joinToString(" ")

                val personID = message.pid
                val validObservations =
                    message.observations.obrGroup.filter { it.obx.first().valueType == "CE" }

                val auffälligBefunde = validObservations.count()
                //.obrGroup.firstOrNull()?.abnormalFlags?.isNotBlank()

                LazyColumn{

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Blue)
                                .padding(16.dp,
                                    bottom = 8.dp))  {
                            Column(  ) {
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
                                    val Birthday = personID?.birthDate
                                    val test = message.msh?.dateTimeOfMessage.toString()

                                    Text(
                                        text = LocalDate.parse(
                                            Birthday.toString(),
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
                    }// Header with pat Infos


                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding( top = 8.dp,
                                    bottom = 8.dp,
                                    start = 16.dp,
                                        end = 16.dp
                                    ),
                            //verticalAlignment = Alignment.CenterVertically,
                            //horizontalArrangement = Arrangement.Center // Center text inside column
                        ) {
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
                                    imageVector = Icons.Default.Warning,
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
                                    text = "${
                                        validObservations.count { obs ->
                                            obs.obx.firstOrNull()?.abnormalFlags?.isNotBlank() == true
                                        }
                                    } auffällige Befunde ",
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = "Überprüfung Notwendig",
                                    textAlign = TextAlign.Center,
                                    color = Color.Red
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    } // Show the critical findings
                    items(validObservations) { observation ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp,
                                    horizontal = 16.dp),
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

                                Text(
                                    text = "current Value " + observation.obx.first().observationValue.toString(),
                                    style = MaterialTheme.typography.titleMedium
                                )

                                ValueGraph(message,observation)

                                // bottom sheet and navigation to show the notiz part.
                                // setting Naviagtion graph for it Nav controller and Nav host.
                                if (observation.nte.isNotEmpty()) {
                                    //   IconButton(onClick = { showInfo = !showInfo }) { }
                                    Text(
                                        text = "Info Notize: " + observation.nte.mapNotNull {
                                            it.comment?.replace(
                                                Regex("\\s+"), " "
                                            )
                                        }.filter { it.isNotBlank() }
                                            .joinToString(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    } // show all findings
                }
            }
        }
    }
}
@Composable
fun ValueGraph(message: HL7Message, observation: OBRGroup ){
    // Custom Line with Value Indicator
    val value =
        observation.obx.first().observationValue?.toFloatOrNull() ?: 0f
    val range = observation.obx.first().referenceRange.toString()
    var lowerDev = 0f
    var upperDev = 100f
    when {
        range.contains("-") ->
        {
            val parts = range.split("-")
            val lowerDev = parts.firstOrNull()?.toFloatOrNull()
            val upperDev = parts.lastOrNull()?.toFloatOrNull()
        }
        range.contains(">") ->
        {
            val parts = range.split(">")
            val lowerDev = parts.firstOrNull()?.toFloatOrNull()
            val upperDev = parts.lastOrNull()?.toFloatOrNull()
        }
        range.contains("<") ->
        {
            val parts = range.split("<")
            val lowerDev = parts.firstOrNull()?.toFloatOrNull()
            val upperDev = parts.lastOrNull()?.toFloatOrNull()
        }
        else ->{
            val lowerDev = null
            val upperDev = null
        }
    }

    val maxValue = 100f // Replace with actual max if known
    val percentage = (value / maxValue).coerceIn(0f, 1f)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Enough space for line + text
    ) {
        val lineHeight = size.height / 2

//        drawLine(color = Color.Green,
//            start = Offset(0f,lineHeight),
//            end =Offset(lowerDev,lineHeight)
//        )

        // Background line
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, lineHeight),
            end = Offset(size.width, lineHeight),
            strokeWidth = 8f
        )

        // Foreground line up to percentage
        drawLine(
            color = Color.Green,
            start = Offset(0f, lineHeight),
            end = Offset(size.width * percentage, lineHeight),
            strokeWidth = 8f
        )

//        drawCircle(
//            color = Color.Green,
//            center = Offset(value,lineHeight)
//        )

        //  HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
