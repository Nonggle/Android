package com.nonggle.feature.resume_view.impl

import android.Manifest
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.download.DownloadFileSaver
import com.example.common.download.DownloadFileSaver.saveFileToDownloads
import com.example.common.utils.getWorkPeriodFormatter
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleDialog
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTopAppBar
import com.example.core.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume_view.impl.navigation.Gender.Companion.getByName
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEffect
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import com.nonggle.feature.resume_view.impl.navigation.ScreenMode
import com.nonggle.model.ResumeContents
import com.nonggle.pdf_render.Orientation
import com.nonggle.pdf_render.PdfGenerator
import com.nonggle.pdf_render.PdfPageSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: ResumeViewViewModel,
    navigateToBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Android 9 이하에서 외부 경로 쓰기 권한 요청 런처
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updatePermissionGranted(isGranted)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ResumeViewEffect.NavigateToBack -> {
                    navigateToBack()
                }

                // 버전에 따라 권한을 요청후 작업을 진행해야함
                is ResumeViewEffect.DownLoadPDF -> {
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !uiState.writeExternalPermissionGranted) {
                        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !uiState.writeExternalPermissionGranted) {
                        // 권한허용이 필요한데 하지 않았으므로 종료
                        Toast.makeText(
                            context,
                            R.string.ResumeExport_PermissionFail_ToastMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        return@collect
                    }
                    withContext(Dispatchers.IO) {
                        val pdfGenerator = PdfGenerator(context = context)
                        // 파일 임시 저장 위치
                        val path = context.getExternalFilesDir("PDF")

                        path?.let {
                            val file = File(it, "Resume.pdf")
                            val outputStream = FileOutputStream(file)
                            val result = pdfGenerator.generateLongContent(
                                outputStream = outputStream,
                                pageSize = PdfPageSize.A4(72)
                                    .orientation(Orientation.PORTRAIT),
                                margin = 160.dp,
                                content = {
                                    ResumeViewScreen(
                                        context = context,
                                        modifier = modifier,
                                        uiState = uiState,
                                        resumeDetail = uiState.resumeDetail,
                                        scrollState = scrollState,
                                        onEvent = viewModel::setEvent,
                                    )
                                }
                            )
                            if (result.isFailure) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        R.string.ResumeExport_Failure_ToastMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            // pdf 렌더링 성공, 실패 여부 관계없이 열람용 화면으로 모드 다시 돌려놓기
                            viewModel.updateScreenMode(ScreenMode.SCREEN)
                            val downloadResult = saveFileToDownloads(
                                context = context,
                                file = file,
                                displayName = "${uiState.resumeDetail?.userName ?: "농글농글"} 이력서.pdf",
                            )
                            when(downloadResult) {
                                is DownloadFileSaver.SaveToDownloadsResult.Success -> {
                                    viewModel.generateDownloadSuccessToastMessage()
                                }
                                is DownloadFileSaver.SaveToDownloadsResult.Error -> {
                                    viewModel.generateDownloadFailToastMessage(downloadResult.message)
                                }
                            }
                        }
                    }
                }

                is ResumeViewEffect.DownLoadSuccess -> {
                    Toast.makeText(
                        context,
                        R.string.ResumeExport_Succcess_ToastMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResumeViewEffect.DownLoadFailure -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    ResumeViewScreen(
        context = context,
        modifier = modifier,
        uiState = uiState,
        resumeDetail = uiState.resumeDetail,
        scrollState = scrollState,
        onEvent = viewModel::setEvent,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeViewScreen(
    context: Context,
    modifier: Modifier = Modifier,
    uiState: ResumeViewState,
    resumeDetail: ResumeContents?,
    scrollState: ScrollState,
    onEvent: (ResumeViewEvent) -> Unit = {},
) {
    var showExportDialog by remember { mutableStateOf(false) }

    if (showExportDialog) {
        exportDialog(
            onDismiss = { showExportDialog = false },
            onConfirm = {
                showExportDialog = false

                //1) pdf 추출
                //2) 다운로드 로직 시행
                onEvent(ResumeViewEvent.DownloadResumeToLocal)
            }
        )
    }

    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) { CircularProgressIndicator() }
    } else if (uiState.resumeRetry) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FullButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onEvent(ResumeViewEvent.RetryGetResumeDetail) },
                title = stringResource(R.string.resumeViewScreen_Title_Retry)
            )
            Text(
                text = stringResource(R.string.resumeViewScreen_SubTitle_Retry),
                style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1)
            )
        }
    } else if (resumeDetail != null) {
        val resumeContent: @Composable ColumnScope.() -> Unit = {
            Column {
                // ---------- 헤더(배경 + 프로필 카드 겹침) ----------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(155.dp)
                            .background(color = NonggleTheme.colorScheme.m1)
                    )
                    if (uiState.screenMode == ScreenMode.SCREEN) {
                        // 상단바
                        NonggleTopAppBar(
                            navigationIcon = R.drawable.arrow_left,
                            onNavigationClick = { onEvent(ResumeViewEvent.ClickBackIconButton) },
                            colors = TopAppBarColors(
                                containerColor = Color.Transparent,
                                navigationIconContentColor = Color.Transparent,
                                titleContentColor = Color.Transparent,
                                actionIconContentColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent,
                            ),
                            actions = {
                                Row {
                                    NonggleIconButton(
                                        onClick = { showExportDialog = true },
                                        image = painterResource(R.drawable.export)
                                    )
                                }
                            }
                        )
                    }

                    // 겹치는 카드
                    Box(
                        modifier = Modifier
                            .padding(top = 100.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = NonggleTheme.colorScheme.g_line,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = NonggleTheme.colorScheme.white,
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                modifier = Modifier.padding(top = 52.dp, bottom = 10.dp),
                                text = resumeDetail.userName,
                                style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black),
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier.padding(start = 4.dp),
                                    text = "${getByName(resumeDetail.gender)}, ${resumeDetail.userAge}",
                                    style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1),
                                )
                            }

                            Text(
                                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                                text = resumeDetail.summary,
                                style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1),
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        userProfile(
                            context = context,
                            modifier = Modifier.padding(top = 66.dp),
                            profileImageUrl = resumeDetail.userProfileImageUrl,
                            screenMode = uiState.screenMode,
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(color = NonggleTheme.colorScheme.g_line)
                )

                // ---------- 경력 타이틀 ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.resumeViewScreen_Title_CareerPeriodTotal),
                        style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = resumeDetail.careerPeriod,
                        style = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.m1),
                    )
                }
                resumeDetail.careerList.forEach {
                    careerCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        careerTitle = it.title,
                        careerPeriod = it.careerPeriod,
                        careerPeriodTotal = getWorkPeriodFormatter(
                            it.careerStareDate,
                            it.careerEndDate
                        ),
                        careerExplanation = it.careerExplanation
                    )
                    Spacer(Modifier.height(20.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = NonggleTheme.colorScheme.g_line)
                )
                Spacer(Modifier.height(26.dp))

                // ---------- 자격증 ----------
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(R.string.resumeViewScreen_Title_Certificate),
                    style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.resumeViewScreen_SubTitle_Certificate),
                        style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colorScheme.g2)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = resumeDetail.certificateList.joinToString { it.certificateTitle },
                        style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colorScheme.g1)
                    )
                }

                Spacer(Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = NonggleTheme.colorScheme.g_line)
                )
                Spacer(Modifier.height(36.dp))

                // ---------- 자기소개 ----------
                Text(
                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp),
                    text = stringResource(R.string.resumeViewScreen_Title_UserDetail),
                    style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    resumeDetail.userDetailKeyword.forEach {
                        typeCard(title = "#${it.type}")
                    }
                }

                Text(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 20.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    text = resumeDetail.userDetailSummary,
                    style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g2)
                )
                Spacer(Modifier.height(40.dp))
            }
        }

        when (uiState.screenMode) {
            ScreenMode.SCREEN -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    content = resumeContent
                )
            }

            ScreenMode.PDF -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = resumeContent
                )
            }
        }
    }

}

@Composable
fun exportDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    NonggleDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        dialogTitle = stringResource(R.string.ResumeExport_Dialog_Title),
        dialogContent = {
            Text(
                text = stringResource(R.string.ResumeExport_Dialog_Content),
                style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
            )
        }
    )
}