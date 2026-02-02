package com.example.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class NonggleColors(
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
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    m1 = Color(0xFF558B2F), // Main Color 1 (진한 녹색)
    m2 = Color(0xFF8BC34A), // Main Color 2
    m3 = Color(0xFFAED581), // Main Color 3
    m4 = Color(0xFFDCEDC8), // Main Color 4
    m5 = Color(0xFFF1F8E9), // Main Color 5 (가장 밝은 녹색)
    m6 = Color(0xFFECF3E6), // Main Color 6
    m7 = Color(0xFFE5EBE0), // Main Color 7
    s1 = Color(0xFFFF8A65), // Sub Color 1 (주황색 계열)
    error = Color(0xFFD32F2F), // Error Color
    g1 = Color(0xFF212121), // Gray 1 (가장 진한 회색)
    g2 = Color(0xFF424242), // Gray 2
    g3 = Color(0xFF616161), // Gray 3
    g4 = Color(0xFFF3F3F3), // Gray 4
    g5 = Color(0xFF9E9E9E), // Gray 5 (가장 밝은 회색)
    g_line = Color(0xFFE0E0E0), // Line Color
    g_line_light = Color(0xFFEEEEEE), // Light Line Color
    unactive = Color(0xFFBDBDBD), // Unactive Color
    background = Color(0xFFFFFFFF), // 앱 전체 배경
    bg = Color(0xFFF5F5F5), // 컴포넌트 등 보조 배경
    shadow = Color(0x1A000000) // 10% 투명도의 검은색 그림자
)

// 다크 모드용 색상 팔레트 (자동 생성)
val DarkNonggleColors = NonggleColors(
    black = Color(0xFFFFFFFF), // 텍스트 역할을 하므로 흰색으로 변경
    white = Color(0xFF121212), // 배경 역할을 하므로 어두운 색으로 변경
    m1 = Color(0xFF689F38), // 채도를 약간 낮추고 명도를 높여 눈의 피로 감소
    m2 = Color(0xFF9CCC65),
    m3 = Color(0xFFAED581),
    m4 = Color(0xFF558B2F), // 밝은 배경색은 어두운 톤으로 변경
    m5 = Color(0xFF33691E), // 가장 밝은 배경은 가장 어두운 톤으로 변경
    m6 = Color(0xFF2E4B2E),
    m7 = Color(0xFF2C3E2C),
    s1 = Color(0xFFFFAB91), // 채도를 낮추고 명도 조정
    error = Color(0xFFE57373), // 너무 강하지 않은 빨간색으로 조정
    g1 = Color(0xFFFAFAFA), // 회색 계열은 명도 반전
    g2 = Color(0xFFF5F5F5),
    g3 = Color(0xFFEEEEEE),
    g4 = Color(0xFFE0E0E0),
    g5 = Color(0xFFBDBDBD),
    g_line = Color(0xFF424242), // 라인 색상은 어두운 배경에서 보이도록 밝은 회색/어두운 회색으로 변경
    g_line_light = Color(0xFF303030),
    unactive = Color(0xFF616161),
    background = Color(0xFF121212), // 앱 전체 배경은 매우 어둡게
    bg = Color(0xFF1E1E1E), // 컴포넌트 배경은 약간 더 밝게
    shadow = Color(0x1AFFFFFF) // 그림자는 밝은 색으로 (어두운 배경 위에서)
)