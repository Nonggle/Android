package com.nonggle.feature.resume_view.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState

@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: ResumeViewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    uiState: ResumeViewState
) {
    if(uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingIndicator()
        }
    } else {
        Column() { }
    }
}