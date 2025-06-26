package com.example.apphl7.presentation.screen


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apphl7.R
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.presentation.viewmodel.HL7ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDetail(navController: NavController, viewModel: HL7ViewModel, index: Int) {
//    val lokalcontex = LocalContext.current
//    val observation = viewModel.parsedMessage?.getObrByIndex(index)
////    NewScreenOpen(observation, navController, lokalcontex)
//
//    observation?.let {
//        androidx.compose.material.Surface {
//            ModalBottomSheet(
//                sheetState = sheetState,
//                onDismissRequest = { navController.navigate(("HL7Screen"))}
//            ){
//                DetailContent(it,lokalcontex)
//            }
//        }
//    }

}

//
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//private fun NewScreenOpen(
//    observation: OBRGroup,
//    navController: NavController,
//    lokalcontex: Context
//) {
//    Scaffold(
//        topBar = {
//            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//            TopAppBar(
//                title = {
//                    androidx.compose.material3.Text(
//                        observation.obx.firstOrNull()?.observationId.costumSplit()
//                            .drop(1).joinToString(" ").replace(
//                                Regex("\\s+"), " "
//                            )
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigate("HL7Screen") { popUpToId } }) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /* TODO: Overflow */ }) {
//                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Blue,
//                    titleContentColor = Color.White,
//                    navigationIconContentColor = Color.White
//                ),
//                scrollBehavior = scrollBehavior,
//                modifier = Modifier
//                    .nestedScroll(scrollBehavior.nestedScrollConnection)
//                    .background(Color.Blue)
//            )
//
//        }
//    ) { innerPadding ->
//
//        DetailContent(observation, lokalcontex)
//    }
//}
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


