package com.example.impl.complete_resume

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.designsystem.component.ContainedButton
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R

@Composable
internal fun CompleteResumeScreen(
    modifier: Modifier = Modifier,
    viewModel: CompleteResumeViewModel = hiltViewModel(),
    navigateToUserResume: () -> Unit,
    navigateToBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val successComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.complete_resume))
    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

    val successProgress by animateLottieCompositionAsState(
        composition = successComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    val errorProgress by animateLottieCompositionAsState(
        composition = errorComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_resume))

    val loadingProgress by animateLottieCompositionAsState(
        composition = loadingComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    if (uiState.uploadSuccess == true) {
        SuccessUpLoadResumeScreen(
            modifier = modifier,
            isLoading = uiState.isLoading,
            navigateToUserResume = navigateToUserResume,
            successComposition = successComposition,
            successProgress = { successProgress },
            loadingComposition = loadingComposition,
            loadingProgress = { loadingProgress }
        )
    } else if (uiState.uploadSuccess == false) {
        FailUpLoadResumeScreen(
            modifier = modifier,
            isLoading = uiState.isLoading,
            navigateTogoBack = navigateToBack,
            errorComposition = errorComposition,
            errorProgress = { errorProgress },
            loadingComposition = loadingComposition,
            loadingProgress = { loadingProgress }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun SuccessUpLoadResumeScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    navigateToUserResume: () -> Unit = {},
    successComposition: LottieComposition?,
    successProgress: () -> Float,
    loadingComposition: LottieComposition?,
    loadingProgress: () -> Float,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Spacer(modifier = Modifier.weight(1f))
            LottieAnimation(
                composition = loadingComposition,
                progress = loadingProgress
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.resumeCompleteScreen_Title_Loading),
                style = NonggleTheme.typography.t1.copy(NonggleTheme.colorScheme.black)
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LottieAnimation(
                    composition = successComposition,
                    progress = successProgress
                )
               Column(
                   modifier = Modifier.fillMaxSize(),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally) {
                   Spacer(modifier = Modifier.weight(1f))
                   Text(
                       modifier = Modifier.height(40.dp),
                       text = stringResource(R.string.resumeCompleteScreen_SubTitle_Success),
                       style = NonggleTheme.typography.t1.copy(NonggleTheme.colorScheme.m1)
                   )
                   Spacer(modifier = Modifier.weight(1f))
                   ContainedButton(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 20.dp),
                       titleText = stringResource(R.string.resumeCompleteScreen_label_SuccessButton),
                       onClick = navigateToUserResume
                   )
                   Spacer(modifier = Modifier.height(40.dp))
               }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun FailUpLoadResumeScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    navigateTogoBack: () -> Unit = {},
    errorComposition: LottieComposition?,
    errorProgress: () -> Float,
    loadingComposition: LottieComposition?,
    loadingProgress: () -> Float,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Spacer(modifier = Modifier.weight(1f))
            LottieAnimation(
                composition = loadingComposition,
                progress = loadingProgress
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.resumeCompleteScreen_Title_Loading),
                style = NonggleTheme.typography.t1.copy(NonggleTheme.colorScheme.black)
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.weight(1f))
            LottieAnimation(
                composition = errorComposition,
                progress = errorProgress
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.resumeCompleteScreen_Title_Error),
                style = NonggleTheme.typography.t1.copy(NonggleTheme.colorScheme.black)
            )
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                titleText = stringResource(R.string.resumeCompleteScreen_label_ErrorButton),
                onClick = navigateTogoBack
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompletePreviewScreen() {
    val successComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.complete_resume))
    val successProgress by animateLottieCompositionAsState(
        composition = successComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_resume))

    val loadingProgress by animateLottieCompositionAsState(
        composition = loadingComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    NonggleTheme {
        SuccessUpLoadResumeScreen(
            successComposition = successComposition,
            successProgress = { successProgress },
            loadingComposition =  loadingComposition,
            loadingProgress = { loadingProgress }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FailPreviewScreen() {
    val errorComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

    val errorProgress by animateLottieCompositionAsState(
        composition = errorComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_resume))

    val loadingProgress by animateLottieCompositionAsState(
        composition = loadingComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f
    )

    NonggleTheme {
        FailUpLoadResumeScreen(
            errorComposition = errorComposition,
            errorProgress = { errorProgress },
            loadingComposition =  loadingComposition,
            loadingProgress = { loadingProgress }
        )
    }
}