package com.nonggle.feature.resume_view.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: ResumeViewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}

@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
) {

}