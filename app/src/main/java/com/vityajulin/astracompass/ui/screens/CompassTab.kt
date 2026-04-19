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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassTab(viewModel: CompassViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    // Получаем текущие цвета темы
    val colorScheme = MaterialTheme.colorScheme

    // Плавная анимация угла
    val animatedAzimuth by animateFloatAsState(
        targetValue = state.azimuth,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "AzimuthAnimation"
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Текст азимута (теперь цвет берется из темы: onSurface)
        Text(
            text = "${state.azimuth.toInt()}°",
            style = MaterialTheme.typography.displayLarge,
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
            val radius = size.width / 2

            rotate(degrees = -animatedAzimuth, pivot = center) {
                for (angle in 0 until 360 step 2) {
                    val isMajor = angle % 10 == 0
                    val isText = angle % 30 == 0
                    val lineLength = if (isMajor) 60f else 30f

                    val angleRad = Math.toRadians(angle.toDouble() - 90).toFloat()

                    // Рисуем деления цветом onSurface (в обычном режиме - черный/белый, в Astro - красный)
                    drawLine(
                        color = colorScheme.onSurface.copy(alpha = if (isMajor) 1f else 0.5f),
                        start = Offset(center.x + (radius - lineLength) * cos(angleRad), center.y + (radius - lineLength) * sin(angleRad)),
                        end = Offset(center.x + radius * cos(angleRad), center.y + radius * sin(angleRad)),
                        strokeWidth = if (isMajor) 4f else 2f
                    )

                    if (isText) {
                        val label = when (angle) {
                            0 -> "N"
                            90 -> "E"
                            180 -> "S"
                            270 -> "W"
                            else -> angle.toString()
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                label,
                                center.x + (radius - 100f) * cos(angleRad),
                                center.y + (radius - 100f) * sin(angleRad) + 15f,
                                android.graphics.Paint().apply {
                                    // Важно: конвертируем Compose Color в Native Color
                                    color = if (angle == 0) android.graphics.Color.RED else colorScheme.onSurface.toArgb()
                                    textSize = 45f
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                }
            }

            // Неподвижный указатель «Курс»
            drawPath(
                path = Path().apply {
                    moveTo(center.x, center.y - radius - 20f)
                    lineTo(center.x - 20f, center.y - radius + 20f)
                    lineTo(center.x + 20f, center.y - radius + 20f)
                    close()
                },
                color = colorScheme.primary // В обычном режиме будет акцентный цвет Android, в Astro - Красный
            )
        }
    }
}
