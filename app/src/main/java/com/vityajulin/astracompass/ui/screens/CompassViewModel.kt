package com.vityajulin.astracompass.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vityajulin.astracompass.data.CompassSensorManager
import com.vityajulin.astracompass.domain.CompassState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor (private val sensorManager: CompassSensorManager) : ViewModel() {

    private val _uiState = MutableStateFlow(CompassState())
    val uiState: StateFlow<CompassState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sensorManager.azimuthFlow.collect { newAzimuth ->
                _uiState.update { it.copy(azimuth = newAzimuth) }
            }
        }
    }

    fun toggleAstroMode() {
        _uiState.update { it.copy(isAstroMode = !it.isAstroMode) }
    }
}