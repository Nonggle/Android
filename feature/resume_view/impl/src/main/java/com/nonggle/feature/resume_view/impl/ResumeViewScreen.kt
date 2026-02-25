package com.nonggle.feature.resume_view.impl

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Uri
import com.example.common.utils.getWorkPeriodFormatter
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewEvent
import com.nonggle.feature.resume_view.impl.navigation.ResumeViewState
import com.nonggle.model.ResumeContents

@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    viewModel: ResumeViewViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    ResumeViewScreen(
        modifier = modifier,
        uiState = uiState,
        resumeDetail = uiState.resumeDetail,
        onEvent = viewModel::setEvent,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeViewScreen(
    modifier: Modifier = Modifier,
    uiState: ResumeViewState,
    resumeDetail: ResumeContents?,
    onEvent: (ResumeViewEvent) -> Unit = {},
    listState: LazyListState = rememberLazyListState(), /// FIXME: 수정 예정
) {
    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) { CircularProgressIndicator() }
    } else if(uiState.resumeRetry) {
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
    } else if(resumeDetail != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = listState
        ) {
            // ---------- 헤더(배경 + 프로필 카드 겹침) ----------
            item(key = "header") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    // 상단 배경(기존 height 150)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(155.dp)
                            .background(color = NonggleTheme.colorScheme.m1)
                    )

                    // 겹치는 카드
                    Box(
                        modifier = Modifier
                            .padding(top = 100.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .background(
                                color = NonggleTheme.colorScheme.g1,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            userProfile(Uri(resumeDetail.userProfileImageUrl))

                            Text(
                                modifier = Modifier.padding(vertical = 10.dp),
                                text = resumeDetail.userName,
                                style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black),
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = null
                                )
                                Text(
                                    text = "${resumeDetail.gender} * ${resumeDetail.userAge}",
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
                }

                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(color = NonggleTheme.colorScheme.g3)
                )
            }

            // ---------- 경력 타이틀 ----------
            item(key = "career_title") {
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
            }

            items(
                items = resumeDetail.careerList,
                key = { career -> career.id }
            ) { career ->
                careerCard(
                    careerTitle = career.title,
                    careerPeriod = career.careerPeriod,
                    careerPeriodTotal = getWorkPeriodFormatter(career.careerStareDate, career.careerEndDate),
                    careerExplanation = career.careerExplanation
                )
                Spacer(Modifier.height(20.dp))
            }

            item(key = "divider1") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = NonggleTheme.colorScheme.g_line)
                )
                Spacer(Modifier.height(36.dp))
            }

            // ---------- 자격증 ----------
            item(key = "cert_title") {
                Text(
                    text = stringResource(R.string.resumeViewScreen_Title_Certificate),
                    style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
            }

            item(key = "divider2") {
                Spacer(Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = NonggleTheme.colorScheme.g_line)
                )
                Spacer(Modifier.height(36.dp))
            }

            // ---------- 자기소개 ----------
            item(key = "detail_title") {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.resumeViewScreen_Title_UserDetail),
                    style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black)
                )
            }

            item(key = "keywords") {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = resumeDetail.userDetailKeyword.size,
                        key = { index -> resumeDetail.userDetailKeyword[index].id }
                    ) { index ->
                        typeCard(title = resumeDetail.userDetailKeyword[index].type)
                    }
                }
            }

            item(key = "detail_summary") {
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                    text = resumeDetail.userDetailSummary,
                    style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g2)
                )
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}