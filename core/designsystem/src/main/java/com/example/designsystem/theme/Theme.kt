package com.example.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
internal val LocalNonggleColors = staticCompositionLocalOf { LightNonggleColors }
internal val LocalNonggleTypography = staticCompositionLocalOf { NonggleTypography() }
/**
 * 앱 전역에서 테마 속성에 접근하기 위한 진입점(Entry Point) 객체입니다.
 */
object NonggleTheme {

    val colorScheme: NonggleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalNonggleColors.current

    val typography: NonggleTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalNonggleTypography.current
}

/**
 * Nonggle 앱의 전체 테마를 적용하는 최상위 Composable 함수입니다.
 * 이 함수가 모든 화면의 루트(root)를 감싸야 합니다.
 *
 * @param darkTheme 시스템이 다크 모드일 때 true.
 * @param content 앱의 콘텐츠 Composable.
 */
@Composable
fun NonggleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: NonggleTypography = NonggleTypography(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkNonggleColors else LightNonggleColors

    CompositionLocalProvider(
        LocalNonggleColors provides colors,
        LocalNonggleTypography provides typography
    ) {
        content()
    }
}