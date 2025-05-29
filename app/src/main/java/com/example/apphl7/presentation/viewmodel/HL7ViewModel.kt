package com.example.apphl7.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.*

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.usecase.LoadAndParseHL7FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val loadAndParseHL7File: LoadAndParseHL7FileUseCase
) : ViewModel() {

    var parsedMessage by mutableStateOf<HL7Message?>(null)
        private set

    fun loadHL7(context: android.content.Context) {
        viewModelScope.launch(Dispatchers.IO) {
            parsedMessage = loadAndParseHL7File(context, "hl7_sample.hl7")
        }
    }
}
