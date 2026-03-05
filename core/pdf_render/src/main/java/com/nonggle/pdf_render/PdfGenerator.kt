package com.nonggle.pdf_render

//noinspection SuspiciousImport
import android.R
import android.app.Activity
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.OutputStream
import kotlin.coroutines.resume
import kotlin.math.ceil
import androidx.compose.foundation.layout.width


class PdfGenerator(private val context: Context) {

    /**
     * Generates a PDF from the given Composables.
     */
    suspend fun generate(
        outputStream: OutputStream,
        pageSize: PdfPageSize = PdfPageSize.A4(72), // Default to A4
        margin: Dp = 160.dp, // 1 inch
        timeout: Long = 3000,
        pages: List<@Composable () -> Unit>
    ): Result<String> = withContext(Dispatchers.Main) {

        outputStream.use { out ->
            val pdfDocument = PdfDocument()
            val imageMonitor = PdfImageMonitor()

            // Dynamic density so the content appears the same regardless of the DPI
            val densityScale = pageSize.dpi / 72f
            val pdfDensity = object : Density {
                override val density: Float = densityScale
                override val fontScale: Float = 1f
            }

            // size minus margin/padding
            val contentWidth = pageSize.width - (margin.value * (pageSize.dpi / 160f) * 2).toInt()
            val contentHeight = pageSize.height - (margin.value * (pageSize.dpi / 160f) * 2).toInt()

            // margin in pixels
            val marginPx = (margin.value * (pageSize.dpi / 160f)).toInt()

            try {
                pages.forEachIndexed { index, pageContent ->

                    val contentReady = CompletableDeferred<Unit>()
                    val pageInfo = PdfDocument.PageInfo.Builder(
                        pageSize.width, pageSize.height, index + 1
                    ).create()
                    val page = pdfDocument.startPage(pageInfo)

                    // Create the View
                    var viewAttached = false
                    // keep the view invisible
                    val composeView = ComposeView(context = context)
                    val rootLayout =
                        (context as? Activity)?.window?.decorView?.findViewById<ViewGroup>(R.id.content)
                            ?: throw IllegalStateException("Context must be an Activity")

                    try {
                        composeView.apply {
                            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                            setContent {
                                CompositionLocalProvider(
                                    LocalPdfImageMonitor provides imageMonitor,
                                    LocalDensity provides pdfDensity
                                ) {
                                    val contentWidthDp = with(pdfDensity) { contentWidth.toDp() }
                                    val contentHeightDp = with(pdfDensity) { contentHeight.toDp() }
                                    Box(
                                        modifier = Modifier
                                            .size(
                                                contentWidthDp,
                                                contentHeightDp
                                            )
                                            .onGloballyPositioned {
                                                if (!contentReady.isCompleted) {
                                                    contentReady.complete(Unit)
                                                }
                                            }
                                    ) {
                                        pageContent()
                                    }
                                }
                            }
                        }

                        // Add view with 0 alpha
                        composeView.alpha = 0f
                        rootLayout.addView(
                            composeView,
                            LayoutParams(contentWidth, contentHeight)
                        )

                        // Safely wait for the content
                        withContext(Dispatchers.Main) {
                            kotlinx.coroutines.withTimeout(timeout) {
                                contentReady.await()
                            }
                        }
                        viewAttached = true

                        // Wait for the next frame
                        waitForNextFrame(composeView)

                        // Wait for the images to fully load
                        imageMonitor.waitForImages(composeView)

                        composeView.measure(
                            View.MeasureSpec.makeMeasureSpec(
                                contentWidth,
                                View.MeasureSpec.EXACTLY
                            ),
                            View.MeasureSpec.makeMeasureSpec(
                                contentHeight,
                                View.MeasureSpec.EXACTLY
                            )
                        )
                        composeView.layout(0, 0, pageSize.width, pageSize.height)

                        // Wait for the view to be ready to draw
                        waitForDrawReady(composeView)

                        page.canvas.save()
                        page.canvas.translate(marginPx.toFloat(), marginPx.toFloat())
                        composeView.draw(page.canvas)
                        page.canvas.restore()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        if (viewAttached) {
                            rootLayout.removeView(composeView)
                        }
                        return@withContext Result.failure(e)
                    } finally {
                        if (viewAttached) {
                            rootLayout.removeView(composeView)
                        }

                        pdfDocument.finishPage(page)
                    }
                }

                //Write to File
                withContext(Dispatchers.IO) {
                    pdfDocument.writeTo(out)
                    out.flush()
                }

                return@withContext Result.success("Successfully generated ${pages.size} pages!")
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.failure(e)
            } finally {
                pdfDocument.close()
            }
        }
    }

    /**
     * Generates a PDF from a single long Composable.
     * If content height exceeds a page, it is split across multiple pages without bitmap capture.
     */
    suspend fun generateLongContent(
        outputStream: OutputStream,
        pageSize: PdfPageSize = PdfPageSize.A4(72),
        margin: Dp = 160.dp,
        timeout: Long = 3000,
        autoRebalanceBreaks: Boolean = true,
        minLastPageRatio: Float = 0.2f,
        content: @Composable () -> Unit
    ): Result<String> = withContext(Dispatchers.Main) {
        outputStream.use { out ->
            val pdfDocument = PdfDocument()
            val imageMonitor = PdfImageMonitor()

            val pdfDensity = object : Density {
                override val density: Float = pageSize.dpi / 160f
                override val fontScale: Float = 1f
            }

            val contentWidth = pageSize.width - (margin.value * (pageSize.dpi / 160f) * 2).toInt()
            val contentHeight = pageSize.height - (margin.value * (pageSize.dpi / 160f) * 2).toInt()
            val marginPx = (margin.value * (pageSize.dpi / 160f)).toInt()

            if (contentWidth <= 0 || contentHeight <= 0) {
                return@withContext Result.failure(
                    IllegalArgumentException("Page content area must be positive. Check pageSize and margin.")
                )
            }
            if (minLastPageRatio <= 0f || minLastPageRatio > 1f) {
                return@withContext Result.failure(
                    IllegalArgumentException("minLastPageRatio must be in the range (0f, 1f].")
                )
            }

            var viewAttached = false
            val contentReady = CompletableDeferred<Unit>()
            var laidOutContentHeight = 0
            val composeView = ComposeView(context = context)
            val rootLayout =
                (context as? Activity)?.window?.decorView?.findViewById<ViewGroup>(R.id.content)
                    ?: return@withContext Result.failure(
                        IllegalStateException("Context must be an Activity")
                    )

            try {
                composeView.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                    setContent {
                        CompositionLocalProvider(
                            LocalPdfImageMonitor provides imageMonitor,
                            LocalDensity provides pdfDensity
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(contentWidth.dp)
                                    .onGloballyPositioned {
                                        laidOutContentHeight = it.size.height
                                        if (!contentReady.isCompleted && it.size.height > 0) {
                                            contentReady.complete(Unit)
                                        }
                                    }
                            ) {
                                content()
                            }
                        }
                    }
                }

                composeView.alpha = 0f
                rootLayout.addView(
                    composeView,
                    LayoutParams(contentWidth, LayoutParams.WRAP_CONTENT)
                )
                viewAttached = true

                withContext(Dispatchers.Main) {
                    kotlinx.coroutines.withTimeout(timeout) {
                        contentReady.await()
                    }
                }

                waitForNextFrame(composeView)
                imageMonitor.waitForImages(composeView)

                composeView.measure(
                    View.MeasureSpec.makeMeasureSpec(contentWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                val totalContentHeight = maxOf(composeView.measuredHeight, laidOutContentHeight)
                if (totalContentHeight <= 0) {
                    return@withContext Result.failure(
                        IllegalStateException("Unable to measure composable content height.")
                    )
                }

                composeView.layout(0, 0, contentWidth, totalContentHeight)
                waitForDrawReady(composeView)

                val (pageStarts, pageHeights) = calculatePageStartsAndHeights(
                    totalContentHeight = totalContentHeight,
                    pageContentHeight = contentHeight,
                    autoRebalanceBreaks = autoRebalanceBreaks,
                    minLastPageRatio = minLastPageRatio
                )
                val pageCount = pageStarts.size

                for (pageIndex in 0 until pageCount) {
                    val pageInfo = PdfDocument.PageInfo.Builder(
                        pageSize.width,
                        pageSize.height,
                        pageIndex + 1
                    ).create()
                    val page = pdfDocument.startPage(pageInfo)
                    try {
                        page.canvas.save()
                        page.canvas.translate(marginPx.toFloat(), marginPx.toFloat())
                        page.canvas.clipRect(
                            0f,
                            0f,
                            contentWidth.toFloat(),
                            pageHeights[pageIndex].toFloat()
                        )
                        page.canvas.translate(0f, -pageStarts[pageIndex].toFloat())
                        composeView.draw(page.canvas)
                        page.canvas.restore()
                    } finally {
                        pdfDocument.finishPage(page)
                    }
                }

                withContext(Dispatchers.IO) {
                    pdfDocument.writeTo(out)
                    out.flush()
                }

                Result.success("Successfully generated $pageCount pages from long content.")
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            } finally {
                if (viewAttached) {
                    rootLayout.removeView(composeView)
                }
                pdfDocument.close()
            }
        }
    }

    // 페이지 분할 계산, 마지막 페이지 너무 작으면 앞 페이지 높이 조금씩 줄여 재분배
    private fun calculatePageStartsAndHeights(
        totalContentHeight: Int,
        pageContentHeight: Int,
        autoRebalanceBreaks: Boolean,
        minLastPageRatio: Float
    ): Pair<IntArray, IntArray> {
        val pageCount = ceil(totalContentHeight / pageContentHeight.toFloat()).toInt().coerceAtLeast(1)
        val pageHeights = IntArray(pageCount)

        if (pageCount == 1) {
            pageHeights[0] = totalContentHeight
        } else {
            val fullPages = pageCount - 1
            val remainder = totalContentHeight - (fullPages * pageContentHeight)
            val minLastPageHeight = ceil(pageContentHeight * minLastPageRatio).toInt().coerceAtLeast(1)
            val shouldRebalance = autoRebalanceBreaks &&
                remainder in 1 until minLastPageHeight &&
                fullPages > 0

            if (!shouldRebalance) {
                for (index in 0 until fullPages) {
                    pageHeights[index] = pageContentHeight
                }
                pageHeights[fullPages] = remainder
            } else {
                val deficit = minLastPageHeight - remainder
                val baseReduction = deficit / fullPages
                val extraReduction = deficit % fullPages

                for (index in 0 until fullPages) {
                    val reduction = baseReduction + if (index < extraReduction) 1 else 0
                    pageHeights[index] = pageContentHeight - reduction
                }
                pageHeights[fullPages] = remainder + deficit
            }
        }

        val pageStarts = IntArray(pageCount)
        var runningOffset = 0
        for (index in 0 until pageCount) {
            pageStarts[index] = runningOffset
            runningOffset += pageHeights[index]
        }
        return pageStarts to pageHeights
    }

    @Deprecated("Obsolete")
    private fun Int.toDp(dpi: Int): Dp {
        // 160 is the baseline dpi of Android
        val scaleFactor = dpi / 160f
        return (this / scaleFactor).dp
    }

    private suspend fun waitForNextFrame(view: View) = suspendCancellableCoroutine { continuation ->
        view.post {
            continuation.resume(Unit)
        }
    }

    private suspend fun waitForDrawReady(view: View) = suspendCancellableCoroutine { continuation ->
        var resumed = false
        val observer = view.viewTreeObserver
        val listener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (!resumed) {
                    resumed = true
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    continuation.resume(Unit)
                }
                return true
            }
        }
        observer.addOnPreDrawListener(listener)

        // post to check if view is already ready
        view.post {
            if (!resumed && view.width > 0 && view.height > 0 && view.isAttachedToWindow) {
                resumed = true
                observer.removeOnPreDrawListener(listener)
                continuation.resume(Unit)
            }
        }
    }
}
