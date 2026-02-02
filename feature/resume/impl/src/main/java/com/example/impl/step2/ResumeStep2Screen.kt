package com.example.feature.resume.impl.step2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import com.example.core.designsystem.component.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.component.NonggleBottomSheet
import com.example.core.designsystem.component.OutlinedIconButton
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.step1.ResumeStep1Event
import com.example.feature.resume.impl.step1.ResumeStep1State
import com.example.feature.resume.impl.step1.ResumeStep1ViewModel
import com.example.impl.component.SubTitleText
import com.example.impl.component.TitleText

@Composable
internal fun ResumeSte2Screen(
    modifier: Modifier = Modifier,
    viewModel: ResumeStep2ViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isShowBottomSheet by remember { mutableStateOf(false) }

}

@Composable
internal fun ResumeStep2Screen(
    modifier: Modifier = Modifier,
    uiState: ResumeStep2State,
    onEvent: (ResumeStep2Event) -> Unit = {},
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        item {
            TitleText(
                modifier = Modifier.padding(bottom = 8.dp),
                titleStringResId = R.string.resume2Screen_Title_careerTitle,
            )
            SubTitleText(
                modifier = Modifier.padding(bottom = 16.dp),
                subTitleStringResId = R.string.resume2Screen_Title_careerSubTitle
            )
            OutlinedButton(
                modifier = Modifier.padding(bottom = 32.dp),
                isSelect = true,
                enabled = false,
                onClick = {}, // do nothing
                titleText = totalPeriod
            )
        }
//            this.items {
//                // 경력 리스트 위치
//            }
        item {
            OutlinedIconButton(
                modifier = Modifier.padding(bottom = 32.dp),
                contentColor = NonggleTheme.colorScheme.g3,
                disableContentColor = NonggleTheme.colorScheme.g3,
                borderColor = NonggleTheme.colorScheme.g_line,
                titleText = stringResource(R.string.resume2Screen_Title_careerAddTitle),
                titleTextStyle = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.g3),
                onClick = { isShowBottomSheet = true }
            )
        }
    }
}