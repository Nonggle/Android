package com.nonggle.feature.home.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.designsystem.component.FullButton
import com.nonggle.designsystem.component.NonggleCircularProgressBar
import com.nonggle.designsystem.component.NonggleMainTopAppBar
import com.nonggle.designsystem.theme.NonggleTheme

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToResumeWritingScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is HomeEffect.NavigateToResumeWritingScreen -> {
                    navigateToResumeWritingScreen()
                }
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.home_worker_background),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                NonggleMainTopAppBar(appBarTitle = stringResource(R.string.HomeScreen_Title))
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(R.string.HomeScreen_MainTitle, uiState.userName),
                    textAlign = TextAlign.Start,
                    style = NonggleTheme.typography.t2,
                )
                Spacer(modifier = Modifier.height(120.dp))
                DownloadProgressBox(
                    title = uiState.downloadResume.title,
                    updateTime = uiState.updateTime,
                    progress = uiState.progress,
                    isDownloadExist = uiState.downloadResumeExist
                )
            }
        }
        FullButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            title = stringResource(R.string.HomeScreen_Navigate_ResumeWriting),
            onClick = {
                onEvent(HomeEvent.NavigateToResumeWritingScreen)
            },
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun DownloadProgressBox(
    modifier: Modifier = Modifier,
    title: String,
    updateTime: String,
    progress: Float,
    isDownloadExist: Boolean,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = NonggleTheme.colorScheme.m5)
    ) {
        if(isDownloadExist) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = NonggleTheme.typography.b1_main,
                        color = NonggleTheme.colorScheme.black
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = updateTime,
                        style = NonggleTheme.typography.b2_sub,
                        color = NonggleTheme.colorScheme.g1
                    )
                }
                NonggleCircularProgressBar(percentage = progress)
            }
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 30.dp),
                text = stringResource(R.string.HomeScreen_Download_NotExist),
                style = NonggleTheme.typography.b1_main,
                color = NonggleTheme.colorScheme.black
            )
        }
    }
}

@Preview(name = "HomeScreen")
@Composable
fun HomePreviewScreen() {
    NonggleTheme {
        HomeScreen()
    }
}