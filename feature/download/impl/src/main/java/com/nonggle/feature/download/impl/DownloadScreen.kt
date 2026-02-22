package com.nonggle.feature.download.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.feature.download.impl.DownloadEvent
import com.nonggle.feature.download.impl.DownloadState
import com.nonggle.feature.download.impl.DownloadViewModel

@Composable
internal fun DownloadScreen(
    modifier: Modifier = Modifier,
    viewModel: DownloadViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}

@Composable
internal fun DownloadScreen(
    modifier: Modifier = Modifier,
    uiState: DownloadState,
    onEvent: (DownloadEvent) -> Unit = {}
) {

}