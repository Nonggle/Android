package com.nonggle.pdf_render
// 외부로 진행률 내보내는 타입
enum class PdfRenderStage {
    INITIALIZING,
    COMPOSING,
    RENDERING_PAGES,
    WRITING_FILE,
    COMPLETED
}

data class PdfRenderProgress(
    val stage: PdfRenderStage,
    val progress: Float,
    val renderedPages: Int = 0,
    val totalPages: Int = 0,
    val status: PdfRenderLoadStatus? = null
)

