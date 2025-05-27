package com.example.apphl7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.naviagtion.compose.hiltViewModel
import com.example.apphl7.presentation.screen.HL7Screen
import com.example.apphl7.presentation.viewmodel.HL7ViewModel

@AndroidEnteryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HL7ViewModel = hiltViewModel()
            HL7Screen(viewModel)
                }
            }
        }
    }
}

