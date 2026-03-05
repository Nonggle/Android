
package com.nonggle.pdf_render

import android.view.View
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume

class PdfImageMonitor {
    private val loadingCount = AtomicInteger(0)
    val isIdle = MutableStateFlow(true)

    fun notifyLoadingStarted() {
        loadingCount.incrementAndGet()
        isIdle.value = false
    }

    fun notifyLoadingFinished() {
        val current = loadingCount.decrementAndGet()
        if (current == 0) {
            isIdle.value = true
        }
    }

    suspend fun waitForImages(view: View) {
        // Wait until all images have finished loading
        isIdle.first { it }

        // Wait for the next frame to ensure images are rendered into the view
        waitForNextFrame(view)
    }

    private suspend fun waitForNextFrame(view: View) = suspendCancellableCoroutine { continuation ->
        view.post {
            continuation.resume(Unit)
        }
    }
}