package com.nonggle.resume.impl.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nonggle.designsystem.component.NonggleTabRow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.designsystem.component.FullButton
import com.nonggle.designsystem.component.NonggleTab
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.designsystem.component.NonggleTopAppBar
import com.nonggle.feature.resume.impl.R
import com.nonggle.resume.impl.main.ResumeTab.Companion.getByValue
import com.nonggle.resume.impl.step1.ResumeStep1Screen
import com.nonggle.resume.impl.step2.ResumeStep2Screen
import com.nonggle.resume.impl.step3.ResumeStep3Screen
import kotlinx.coroutines.launch


@Composable
internal fun ResumeMainScreen(
    modifier: Modifier = Modifier,
    navigateToComplete: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: ResumeMainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { uiState.tabList.size })
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    ResumeMainScreen(
        modifier = modifier,
        tabList = uiState.tabList,
        pagerState = pagerState,
        onTabClick = { index ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }
        },
        navigateToComplete = {
            if (pagerState.currentPage == uiState.tabList.size - 1) {
                /// FIXME: 데이터 검증 거친 후 이동 만약 검증 실패시 오류 생긴 화면으로 이동
                navigateToComplete()
            } else {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        },
        navigateGoBack = navigateToHome,
        context = context
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeMainScreen(
    modifier: Modifier = Modifier,
    tabList: List<Int>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
    navigateToComplete: () -> Unit,
    navigateGoBack: () -> Unit,
    context: Context,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NonggleTopAppBar(
            titleRes = R.string.resume_Title,
            navigationIcon = R.drawable.arrow_left,
            onNavigationClick = navigateGoBack
        )
        NonggleTabRow(selectedTabIndex = pagerState.currentPage) {
            tabList.forEachIndexed { index, title ->
                NonggleTab(
                    text = {
                        Text(
                            text = stringResource(title),
                            style = NonggleTheme.typography.b4_btn
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = { onTabClick(index) },
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
        ) { page ->
            when (getByValue(page)) {
                ResumeTab.INFO -> ResumeStep1Screen()
                ResumeTab.CAREER -> ResumeStep2Screen()
                ResumeTab.PORTFOLIO -> ResumeStep3Screen()
                else -> {}
            }
        }
        FullButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = navigateToComplete,
            title = if (pagerState.currentPage == tabList.size - 1) stringResource(R.string.resume_complete) else stringResource(
                R.string.resume_nextStep
            )
        )
    }

}