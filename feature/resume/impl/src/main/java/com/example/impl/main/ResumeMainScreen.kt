package com.example.impl.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.core.designsystem.component.NonggleTabRow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleTab
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.ResumeTab
import com.example.feature.resume.impl.ResumeTab.Companion.getByValue
import com.example.feature.resume.impl.ResumeWriteViewModel
import com.example.feature.resume.impl.step1.ResumeStep1Screen
import kotlinx.coroutines.launch
/// TODO: 완료시 토스트메시지 띄우기

@Composable
internal fun ResumeMainScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    viewModel: ResumeWriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { uiState.tabList.size })
    val coroutineScope = rememberCoroutineScope()

    ResumeMainScreen(
        modifier = modifier,
        tabList = uiState.tabList,
        pagerState = pagerState,
        onTabClick = { index ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }
        },
        onNextOrCompleteClick = {
            if (pagerState.currentPage == uiState.tabList.size - 1) {
                navigateToHome()
            } else {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    )
}

@Composable
internal fun ResumeMainScreen(
    modifier: Modifier = Modifier,
    tabList: List<Int>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
    onNextOrCompleteClick: () -> Unit,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
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
        ) {page ->
            when(getByValue(page)) {
                ResumeTab.INFO -> ResumeStep1Screen()
                ResumeTab.CAREER -> ResumeStep1Screen()
                ResumeTab.PORTFOLIO -> ResumeStep1Screen()
                ResumeTab.CONDITION -> ResumeStep1Screen()
                else -> {}
            }
        }
        FullButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onNextOrCompleteClick,
            title = if(pagerState.currentPage == tabList.size - 1) stringResource(R.string.resume_complete) else stringResource(R.string.resume_nextStep)
        )
    }

}