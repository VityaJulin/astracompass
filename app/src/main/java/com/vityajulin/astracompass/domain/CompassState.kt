package com.vityajulin.astracompass.domain

data class CompassState(
    val azimuth: Float = 0f,
    val accuracy: Int = 0,
    val isAstroMode: Boolean = false,
    val tiltX: Float = 0f,
    val tiltY: Float = 0f
)
