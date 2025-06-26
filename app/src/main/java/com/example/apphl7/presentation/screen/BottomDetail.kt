package com.example.apphl7.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll


import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apphl7.domain.model.OBRGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDetail(navController: NavController, observation: OBRGroup) {
    Scaffold(
        topBar = {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            TopAppBar(
                title = {
                    androidx.compose.material3.Text(
                        observation.obx.first().observationId.costumSplit()
                            .drop(1).joinToString(" ").replace(
                                Regex("\\s+"), " "
                            )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("HL7Screen") }) {
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
                    navigationIconContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .background(Color.Blue)
            )

        }
    ) { innerPadding ->
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    innerPadding
                )
            // val setId = observation.obr?.setId?.toIntOrNull() ?: return@clickable
            //navController.navigate("BottomDetail/$setId") })  ,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = "in " + observation.obx.first().units.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.padding(16.dp))

                var currentVal =
                    observation.obx.first().observationValue.toString()
                var range = observation.obx.first().referenceRange.toString()
                    .replace(
                        observation.obx.first().referenceRange.toString(),
                        "25 - 75",
                        observation.obx.first().referenceRange.toString().isNullOrBlank()
                    )
                var valueDanger =
                    false // TODO Impl a real marker to find high values if abnormal flag is empty
                // TODO do the range parsing in the Domain layer / properply giving the range
                // TODO load range in int list 2x1 1. lower, 2. upper bound
                Text(
                    text = "current Value " + currentVal,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Box(modifier = Modifier
                    .background(color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                    )){ ValueGraph(currentVal.toFloat(), range) }
                if (observation.nte.isNotEmpty()) {
                Row() {

                        NotizBox(detailSheet=true)
                    }

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

//    var skipHalfExpanded by remember { mutableStateOf(false) }
//    val state =
//        rememberModalBottomSheetState(
//            initialValue = ModalBottomSheetValue.Hidden,
//            skipHalfExpanded = skipHalfExpanded,
//        )
//    val scope = rememberCoroutineScope()
//    ModalBottomSheetLayout(
//        sheetState = state,
//        sheetContent = {
//            Text(text = " this is nummer on Sheet ocnten")
//        },
//    ) {
//        Text(text = " this is rest screen")
//        ElevatedCard(
//            elevation = CardDefaults.cardElevation(
//                defaultElevation = 6.dp
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    vertical = 8.dp,
//                    horizontal = 16.dp
//                )
//                .clickable(
//                    onClick = {
//                        navController.navigate("HL7Screen")
//                        }
//                    )
//        ) {
//            Text(text = observation.obx.firstOrNull()?.observationId.toString())
//
//
//            if (observation.nte.isNotEmpty())
//                Text(
//                    text = "Info Notize: " + observation.nte.mapNotNull {
//                        it.comment?.replace(
//                            Regex("\\s+"), " "
//                        )
//                    }.filter { it.isNotBlank() }
//                        .joinToString(),
//                    style = MaterialTheme.typography.titleMedium
//                )
//        }
//
//    }
        }
    }
}