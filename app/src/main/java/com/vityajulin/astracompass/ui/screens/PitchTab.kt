package com.vityajulin.astracompass.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.math.abs

@Composable
fun PitchTab(viewModel: CompassViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

    val angle = abs(state.tiltX)
    val degrees = angle.toInt()
    val minutes = ((angle - degrees) * 60).toInt()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "$degrees°${minutes}'",
            style = MaterialTheme.typography.displayLarge,
            color = colorScheme.onSurface
        )
    }
}
