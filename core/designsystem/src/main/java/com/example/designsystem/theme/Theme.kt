package com.example.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = NonggleTokens.m1,
    onPrimary = NonggleTokens.white,
    primaryContainer = NonggleTokens.m5,
    onPrimaryContainer = NonggleTokens.m1,

    secondary = NonggleTokens.m2,
    onSecondary = NonggleTokens.white,
    secondaryContainer = NonggleTokens.m6,
    onSecondaryContainer = NonggleTokens.m3,

    tertiary = NonggleTokens.s1,
    onTertiary = NonggleTokens.black,
    tertiaryContainer = Color(0xFFFFE2C2),      // light orange container (custom)
    onTertiaryContainer = Color(0xFF3A2200),    // deep brown for contrast (custom)

    error = NonggleTokens.error,
    onError = NonggleTokens.white,
    errorContainer = Color(0xFFFFDAD6),         // M3-style soft error container (custom)
    onErrorContainer = Color(0xFF410002),       // deep error text (custom)

    background = NonggleTokens.bg,
    onBackground = NonggleTokens.black,

    surface = NonggleTokens.bg,
    onSurface = NonggleTokens.black,

    surfaceVariant = NonggleTokens.g4,
    onSurfaceVariant = NonggleTokens.g1,
    surfaceTint = NonggleTokens.g2,

    inverseSurface = NonggleTokens.black,
    inverseOnSurface = NonggleTokens.g5,

    outline = NonggleTokens.gLine,
)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = Color.White)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

@VisibleForTesting
val DarkDefaultColorScheme = darkColorScheme(
    // In dark theme, primary should be lighter than background for contrast
    primary = NonggleTokens.m4,
    onPrimary = Color(0xFF0C1F14),              // near-black green (custom)
    primaryContainer = Color(0xFF254C35),       // deep brand green container (custom)
    onPrimaryContainer = NonggleTokens.m7,

    secondary = NonggleTokens.m3,
    onSecondary = Color(0xFF0C1F14),            // same near-black green (custom)
    secondaryContainer = Color(0xFF2C5C43),     // deep supporting green (custom)
    onSecondaryContainer = NonggleTokens.m7,

    tertiary = Color(0xFFFFC98A),               // lighter orange for dark mode (custom)
    onTertiary = Color(0xFF2A1800),
    tertiaryContainer = Color(0xFF5A3A10),      // dark orange container (custom)
    onTertiaryContainer = Color(0xFFFFEAD3),

    // Error: use M3-like light error for dark backgrounds (better readability)
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = Color(0xFF0F1411),             // greenish-black (custom)
    onBackground = NonggleTokens.m7,

    surface = Color(0xFF121815),                // slightly lifted from background (custom)
    onSurface = NonggleTokens.m7,

    surfaceVariant = Color(0xFF2A332E),         // dark gray-green (custom)
    onSurfaceVariant = Color(0xFFD6E2DA),       // soft light for variant text (custom)

    inverseSurface = NonggleTokens.m7,
    inverseOnSurface = Color(0xFF1A1F1C),

    outline = Color(0xFF4B5D55),                // muted outline on dark (custom)
)

@Composable
fun NonggleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.background,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = when {
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }
    // Composition locals
    CompositionLocalProvider(
        LocalBackgroundTheme provides backgroundTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = NonggleTextStyle.asMaterialTypography(),
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S