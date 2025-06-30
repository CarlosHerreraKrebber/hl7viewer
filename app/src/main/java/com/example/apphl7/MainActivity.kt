package com.example.apphl7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apphl7.presentation.screen.AuffaelligebefundeScreen
import com.example.apphl7.presentation.screen.HL7Screen
import com.example.apphl7.presentation.viewmodel.HL7ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // NavGraph
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "HL7Screen") {
                composable(route = "HL7Screen") {
                    val viewModel: HL7ViewModel = hiltViewModel()
                    HL7Screen(navController, viewModel)
                }
                composable(route = "AuffaelligebefundeScreen") {
                    // Loads the Viewmodel from previous Screen
                    val viewModel: HL7ViewModel =
                        hiltViewModel(navController.previousBackStackEntry!!)
                    AuffaelligebefundeScreen(navController = navController, viewModel = viewModel)
                }

            }
        }
    }

}
