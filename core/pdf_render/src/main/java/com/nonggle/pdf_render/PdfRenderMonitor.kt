package com.nonggle.pdf_render

import android.view.View
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume

data class PdfRenderLoadStatus(
    val totalRequested: Int,
    val finishedCount: Int,
    val inFlightCount: Int,
    val isIdle: Boolean
)

// 렌더링 로딩 상태 수집
class PdfRenderMonitor {
    private val inFlightCount = AtomicInteger(0)
    private val totalRequested = AtomicInteger(0)
    private val finishedCount = AtomicInteger(0)
    private val _status = MutableStateFlow(PdfRenderLoadStatus(0, 0, 0, true))
    val status: StateFlow<PdfRenderLoadStatus> = _status.asStateFlow()

    private val _progress = MutableStateFlow(
        PdfRenderProgress(
            stage = PdfRenderStage.INITIALIZING,
            progress = 0f,
            status = PdfRenderLoadStatus(0, 0, 0, true)
        )
    )
    val progress: StateFlow<PdfRenderProgress> = _progress.asStateFlow()

    private fun updateStatus() {
        _status.value = PdfRenderLoadStatus(
            totalRequested = totalRequested.get(),
            finishedCount = finishedCount.get(),
            inFlightCount = inFlightCount.get(),
            isIdle = inFlightCount.get() == 0
        )
    }

    fun reset() {
        inFlightCount.set(0)
        totalRequested.set(0)
        finishedCount.set(0)
        updateStatus()
        _progress.value = PdfRenderProgress(
            stage = PdfRenderStage.INITIALIZING,
            progress = 0f,
            status = status.value
        )
    }

    fun report(progress: PdfRenderProgress) {
        _progress.value = progress.copy(status = progress.status ?: status.value)
    }

    fun notifyLoadingStarted() {
        totalRequested.incrementAndGet()
        inFlightCount.incrementAndGet()
        updateStatus()
    }

    fun notifyLoadingFinished() {
        inFlightCount.updateAndGet { current -> (current - 1).coerceAtLeast(0) }
        finishedCount.incrementAndGet()
        updateStatus()
    }

    suspend fun waitForRender(
        view: View,
        onStatusChanged: ((PdfRenderLoadStatus) -> Unit)? = null
    ) {
        // Wait until all images have finished loading
        status
            .onEach { current -> onStatusChanged?.invoke(current) }
            .first { current -> current.isIdle }

        // Wait for the next frame to ensure images are rendered into the view
        waitForNextFrame(view)
    }

    private suspend fun waitForNextFrame(view: View) = suspendCancellableCoroutine { continuation ->
        view.post {
            continuation.resume(Unit)
        }
    }
}
