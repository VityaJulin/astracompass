package com.vityajulin.astracompass.domain

data class CompassState(
    val azimuth: Float = 0f,
    val accuracy: Int = 0, // 0 - ненадежно, 3 - отлично
    val isAstroMode: Boolean = false
)
