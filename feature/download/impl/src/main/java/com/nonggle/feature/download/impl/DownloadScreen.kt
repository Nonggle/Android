package com.nonggle.feature.download.impl.navigation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleDialog
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.theme.NonggleTheme
import com.nonggle.feature.download.impl.R
import com.nonggle.feature.download.impl.DownloadEvent
import com.nonggle.feature.download.impl.DownloadState
import com.nonggle.feature.download.impl.DownloadViewModel
import com.nonggle.model.SingleResume

@Composable
internal fun DownloadScreen(
    modifier: Modifier = Modifier,
    navigateToViewResume: (Long) -> Unit,
    viewModel: DownloadViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DownloadScreen(
        modifier = modifier,
        uiState = uiState,
        context = context,
        onEvent = viewModel::setEvent,
        navigateToViewResume = navigateToViewResume
    )
}

@Composable
internal fun DownloadScreen(
    modifier: Modifier = Modifier,
    uiState: DownloadState,
    context: Context,
    onEvent: (DownloadEvent) -> Unit = {},
    navigateToViewResume: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading == true) {
            CircularProgressIndicator()
        } else if (uiState.isError == true) {
            FullButton(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(R.string.Download_Title_RetryButton),
                onClick = { onEvent(DownloadEvent.RetryGetResumeList) }
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = uiState.resumeList.size,
                    key = { index -> uiState.resumeList[index].id },
                    itemContent = { index ->
                        resumeItem(
                            resumeContent = uiState.resumeList[index],
                            context = context,
                            navigateToViewResume = navigateToViewResume,
                            deleteItem = { onEvent(DownloadEvent.DeleteResumeItem(resumeId = uiState.resumeList[index].id)) }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun resumeItem(
    modifier: Modifier = Modifier,
    context: Context,
    deleteItem: () -> Unit,
    resumeContent: SingleResume,
    navigateToViewResume: (Long) -> Unit = {},

) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if(showDeleteDialog) {
        deleteDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                deleteItem()
                showDeleteDialog = false
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = NonggleTheme.colorScheme.white)
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, NonggleTheme.colorScheme.g_line_light, RoundedCornerShape(4.dp))
            .clickable { navigateToViewResume(resumeContent.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 사진 영역
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                if (resumeContent.profileImageUrl.isEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(99.dp)),
                        painter = painterResource(R.drawable.user),
                        contentDescription = null
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .size(58.dp)
                            .border(
                                width = 1.dp,
                                color = NonggleTheme.colorScheme.g_line_light,
                                shape = RoundedCornerShape(99.dp)
                            ),
                        model = ImageRequest.Builder(context)
                            .data(resumeContent.profileImageUrl)
                            .crossfade(true)
                            .transformations(CircleCropTransformation())
                            .build(),
                        contentDescription = null,
                        error = painterResource(R.drawable.user),
                        placeholder = painterResource(R.drawable.user),
                    )
                }
            }
            // 인적사항 영역
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = resumeContent.userName,
                    style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.black)
                )
                Text(
                    text = resumeContent.introduce,
                    style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g2),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = NonggleTheme.colorScheme.m5)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        text = resumeContent.totalCareer,
                        style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.m1)
                    )
                }
            }
        }
        NonggleIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 10.dp),
            onClick = { showDeleteDialog = true },
            image = painterResource(R.drawable.xcircle)
        )
    }
}

@Composable
fun deleteDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    NonggleDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        dialogTitle = stringResource(R.string.ResumeDelete_Dialog_Title),
        dialogContent = {
            Text(
                text = stringResource(R.string.ResumeDelete_Dialog_Content),
                style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
            )
        }
    )
}