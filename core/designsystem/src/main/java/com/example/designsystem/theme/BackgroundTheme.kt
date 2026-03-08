package com.example.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Immutable
data class BackgroundTheme(
    val color: Color = Color.White,
    val tonalElevation: Dp = Dp.Unspecified,
)

// 컴포저블 어디서나 접근 가능한 변수.
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }
