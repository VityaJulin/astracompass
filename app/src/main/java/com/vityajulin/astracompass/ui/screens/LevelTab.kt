package com.vityajulin.astracompass.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LevelTab(viewModel: CompassViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

    val animatedTiltX by animateFloatAsState(
        targetValue = state.tiltX,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "TiltXAnimation"
    )

    val animatedTiltY by animateFloatAsState(
        targetValue = state.tiltY,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "TiltYAnimation"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "X: ${animatedTiltX.toInt()}°  Y: ${animatedTiltY.toInt()}°",
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val bubbleRadius = 30f
            val maxOffset = 150f

            val offsetX = (animatedTiltX / 45f).coerceIn(-1f, 1f) * maxOffset
            val offsetY = (animatedTiltY / 45f).coerceIn(-1f, 1f) * maxOffset

            drawCircle(
                color = colorScheme.onSurface.copy(alpha = 0.3f),
                radius = maxOffset + bubbleRadius,
                center = center,
                style = Stroke(width = 2f)
            )

            drawCircle(
                color = colorScheme.onSurface.copy(alpha = 0.15f),
                radius = (maxOffset + bubbleRadius) / 2,
                center = center,
                style = Stroke(width = 1f)
            )

            drawCircle(
                color = colorScheme.primary,
                radius = bubbleRadius,
                center = Offset(center.x + offsetX, center.y + offsetY)
            )

            if (kotlin.math.abs(animatedTiltX) < 3f && kotlin.math.abs(animatedTiltY) < 3f) {
                drawCircle(
                    color = colorScheme.primary,
                    radius = bubbleRadius + 10f,
                    center = center,
                    style = Stroke(width = 4f)
                )
            }
        }
    }
}