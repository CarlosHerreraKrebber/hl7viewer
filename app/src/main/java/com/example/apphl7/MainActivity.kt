package com.example.apphl7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apphl7.presentation.screen.BottomDetail
import com.example.apphl7.presentation.screen.HL7Screen
import com.example.apphl7.presentation.screen.NaviGraph
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // NavGraph
            val navController = rememberNavController()
            // val viewModel: HL7ViewModel = hiltViewModel()
            NavHost(navController = navController, startDestination = "HL7Screen")
            {
                composable(route = "HL7Screen")
                {
                    val viewModel: HL7ViewModel = hiltViewModel()
                    HL7Screen(navController, viewModel, applicationContext)
                }
//                composable(
//                    route = "BottomDetail/{index}",
//                    arguments = listOf(
//                        navArgument(name = "index") {
//                            type = NavType.IntType
//                        }
//                    )
//                ) { backStackEntry ->
//                    val viewModel: HL7ViewModel =
//                        hiltViewModel(navController.previousBackStackEntry!!)
//                    val index = backStackEntry.arguments?.getInt("index") ?: -1
//
//                    BottomDetail(
//                        navController = navController,
//                        viewModel,
//                        index = index
//                    )
//
//
//                }

            }
        }
    }

}
