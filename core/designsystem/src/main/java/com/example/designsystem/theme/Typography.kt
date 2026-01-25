package com.example.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class NonggleTextTokens(
    val HintTextAppearance: TextStyle,
    val TextInputEditTextStyle: TextStyle,
    val t1: TextStyle,
    val t2: TextStyle,
    val t3: TextStyle,
    val b1_main: TextStyle,
    val b2_sub: TextStyle,
    val b3_small: TextStyle,
    val b4_btn: TextStyle,
)

internal fun NonggleTextTokens.asMaterialTypography(): Typography = Typography(
    headlineLarge = t1,
    headlineMedium = t2,
    headlineSmall = t3,
    titleLarge = t2,
    titleMedium = t3,
    titleSmall = b2_sub,
    bodyLarge = b1_main,
    bodyMedium = b2_sub,
    bodySmall = b3_small,
    labelLarge = b4_btn,
    labelMedium = HintTextAppearance,
    labelSmall = b3_small,
)

internal val NonggleTextStyle = NonggleTextTokens(
    HintTextAppearance = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    TextInputEditTextStyle = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    t1 = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    t2 = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    t3 = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    b1_main = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    b2_sub = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    b3_small = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    b4_btn = TextStyle(
        fontFamily = spoqahanSansneo,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    )
)
