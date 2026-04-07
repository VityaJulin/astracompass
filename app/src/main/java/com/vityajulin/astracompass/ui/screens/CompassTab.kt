package com.vityajulin.astracompass.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassTab(viewModel: CompassViewModel) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Большой цифровой азимут сверху
        Text(
            text = "${state.azimuth.toInt()}°",
            fontSize = 48.sp,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 64.dp)
        )

        // Сам компас
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2 * 0.8f

            // Вращаем весь диск целиком относительно азимута телефона
            // Чтобы "Север" на диске всегда смотрел на реальный север
            rotate(degrees = -state.azimuth, pivot = center) {

                // Рисуем деления
                for (angle in 0 until 360 step 2) {
                    val angleRad = Math.toRadians(angle.toDouble() - 90).toFloat()
                    val lineLength = if (angle % 10 == 0) 40f else 20f
                    val start = Offset(
                        center.x + (radius - lineLength) * cos(angleRad),
                        center.y + (radius - lineLength) * sin(angleRad)
                    )
                    val end = Offset(
                        center.x + radius * cos(angleRad),
                        center.y + radius * sin(angleRad)
                    )

                    drawLine(
                        color = Color.Gray,
                        start = start,
                        end = end,
                        strokeWidth = 2f
                    )

                    // Рисуем цифры каждые 30 градусов
                    if (angle % 30 == 0) {
                        // Тут можно добавить drawText через nativeCanvas для цифр
                    }
                }

                // Метка Севера (Красная)
                val northRad = Math.toRadians(-90.0).toFloat()
                drawLine(
                    color = Color.Red,
                    start = center,
                    end = Offset(center.x + radius * cos(northRad), center.y + radius * sin(northRad)),
                    strokeWidth = 5f
                )
            }

            // Неподвижный индикатор "Курс" (верх телефона)
            drawLine(
                color = Color.White,
                start = Offset(center.x, center.y - radius - 20f),
                end = Offset(center.x, center.y - radius + 20f),
                strokeWidth = 8f
            )
        }
    }
}
