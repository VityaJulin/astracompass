package com.vityajulin.astracompass.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vityajulin.astracompass.data.CompassSensorManager
import com.vityajulin.astracompass.data.LevelSensorManager
import com.vityajulin.astracompass.domain.CompassState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor (
    private val compassSensorManager: CompassSensorManager,
    private val levelSensorManager: LevelSensorManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompassState())
    val uiState: StateFlow<CompassState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            compassSensorManager.azimuthFlow.collect { newAzimuth ->
                _uiState.update { it.copy(azimuth = newAzimuth) }
            }
        }
        viewModelScope.launch {
            levelSensorManager.tiltFlow.collect { (x, y) ->
                _uiState.update { it.copy(tiltX = x, tiltY = y) }
            }
        }
    }

    fun toggleAstroMode() {
        _uiState.update { it.copy(isAstroMode = !it.isAstroMode) }
    }
}