package com.example.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class NonggleColors(
    val black: Color,
    val white: Color,
    val m1: Color,
    val m2: Color,
    val m3: Color,
    val m4: Color,
    val m5: Color,
    val m6: Color,
    val m7: Color,
    val s1: Color,
    val error: Color,
    val g1: Color,
    val g2: Color,
    val g3: Color,
    val g4: Color,
    val g5: Color,
    val g_line: Color,
    val g_line_light: Color,
    val unactive: Color,
    val background: Color,
    val bg: Color,
    val shadow: Color
)

// 라이트 모드용 색상 팔레트
val LightNonggleColors = NonggleColors(
    black = Color(0xFF1E1E1E),
    white = Color(0xFFFFFFFF),
    m1 = Color(0xFF326B48), // Main Color 1 (진한 녹색)
    m2 = Color(0xFF488961), // Main Color 2
    m3 = Color(0xFF6FAB86), // Main Color 3
    m4 = Color(0xFFA1CAB1), // Main Color 4
    m5 = Color(0xFFE5EBDD), // Main Color 5 (가장 밝은 녹색)
    m6 = Color(0xFFEBF0EC), // Main Color 6
    m7 = Color(0xFFF4F8F5), // Main Color 7
    s1 = Color(0xFFFFA441), // Sub Color 1 (주황색 계열)
    error = Color(0xFFCB3C3C), // Error Color
    g1 = Color(0xFF4E4E4E), // Gray 1 (가장 진한 회색)
    g2 = Color(0xFF757575), // Gray 2
    g3 = Color(0xFFB0B0B0), // Gray 3
    g4 = Color(0xFFF3F3F3), // Gray 4
    g5 = Color(0xFFFAFAF9), // Gray 5 (가장 밝은 회색)
    g_line = Color(0xFFE0E0E0), // Line Color
    g_line_light = Color(0xFFECECEC), // Light Line Color
    unactive = Color(0xFF6FAB86), // Unactive Color
    background = Color(0xFFF6F4F1), // 앱 전체 배경
    bg = Color(0xFFFDFDFC), // 컴포넌트 등 보조 배경
    shadow = Color(0xFF1E1E1E) // 10% 투명도의 검은색 그림자
)

// 다크 모드용 색상 팔레트 (자동 생성)
val DarkNonggleColors = NonggleColors(
    black = Color(0xFFFFFFFF), // 텍스트 역할을 하므로 흰색으로 변경
    white = Color(0xFF121212), // 배경 역할을 하므로 어두운 색으로 변경
    m1 = Color(0xFF55C88A),
    m2 = Color(0xFF3FAE74),
    m3 = Color(0xFF2E8C5E),
    m4 = Color(0xFF246D49),
    m5 = Color(0xFF1E3A2B),
    m6 = Color(0xFF162A20),
    m7 = Color(0xFF0F1C15),
    s1 = Color(0xFFFFB866),
    error = Color(0xFFE85C5C),
    g1 = Color(0xFFEAEAEA), // primary text
    g2 = Color(0xFFBEBEBE), // secondary text
    g3 = Color(0xFF8C8C8C), // disabled / hint
    g4 = Color(0xFF2A2F2C), // surface/chip background
    g5 = Color(0xFF1B201D), // deeper surface
    g_line = Color(0xFF343A36),
    g_line_light = Color(0xFF2A2F2C),
    // States
    unactive = Color(0xFF3D6652),
    // Backgrounds
    background = Color(0xFF0F1110),
    bg = Color(0xFF161A17),
    // Shadow (10% black)
    shadow = Color(0x1A000000)
)

