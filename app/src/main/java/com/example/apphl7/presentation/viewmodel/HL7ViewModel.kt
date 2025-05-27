package com.example.apphl7.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.*

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.usecase.LoadAndParseHL7FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HL7ViewModel @Inject constructor(
    application: Application,
    private val loadAndParseHL7File: LoadAndParseHL7FileUseCase
) : AndroidViewModel(application) {

    var parsedMessage by mutableStateOf<HL7Message?>(null)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            parsedMessage = loadAndParseHL7File(getApplication(), "Beispiel_HL7.hl7")

        }
    }
}
