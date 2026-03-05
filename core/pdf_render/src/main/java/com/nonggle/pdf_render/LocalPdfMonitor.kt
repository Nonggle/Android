package com.nonggle.pdf_render

import androidx.compose.runtime.staticCompositionLocalOf

// compose 트리 어디에서든 모니터링 상태 관찰하기 위한 정의
val LocalPdfMonitor = staticCompositionLocalOf<PdfRenderMonitor?> { null }