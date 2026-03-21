package com.nonggle.resume.impl.step2

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import com.nonggle.designsystem.component.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.common.utils.getPeriodFormatter
import com.nonggle.designsystem.component.OutlinedIconButton
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume.impl.R
import com.nonggle.impl.component.CareerBottomSheet
import com.nonggle.impl.component.CareerItem
import com.nonggle.impl.component.SubTitleText
import com.nonggle.impl.component.TitleText
import java.time.Period

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep2Screen(
    modifier: Modifier = Modifier,
    viewModel: ResumeStep2ViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isShowBottomSheet by remember { mutableStateOf(false) }
    val careerBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {false}
    )
    val startCareerDateNotValidMessage = stringResource(R.string.resume2Screen_startCareerDate_NotValid)
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is ResumeStep2Effect.SendStartCareerNotValidMessage -> {
                    Toast.makeText(context, startCareerDateNotValidMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    ResumeStep2Screen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        showBottomSheet = isShowBottomSheet,
        careerBottomSheetState = careerBottomSheetState,
        careerSheetState = uiState.careerFormData,
        onCareerSheetEvent = { sheetEvent ->
            viewModel.setEvent(
                ResumeStep2Event.CareerSheetEvent(
                    sheetEvent
                )
            )
        },
        careerBottomSheetClick = { isShowBottomSheet = true },
        careerBottomSheetDismiss = { isShowBottomSheet = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep2Screen(
    modifier: Modifier = Modifier,
    uiState: ResumeStep2State,
    careerSheetState: CareerFormData,
    onEvent: (ResumeStep2Event) -> Unit = {},
    onCareerSheetEvent: (CareerBottomSheetEvent) -> Unit = {},
    showBottomSheet: Boolean = false,
    careerBottomSheetState: SheetState,
    careerBottomSheetClick: () -> Unit = {},
    careerBottomSheetDismiss: () -> Unit = {},
) {
    if (showBottomSheet) {
        CareerBottomSheet(
            sheetState = careerBottomSheetState,
            uiState = careerSheetState,
            onEvent = onCareerSheetEvent,
            onDismissRequest = {
                onCareerSheetEvent(CareerBottomSheetEvent.DeleteCareerItem)
                careerBottomSheetDismiss()
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        TitleText(
            modifier = Modifier.padding(bottom = 8.dp),
            titleStringResId = R.string.resume2Screen_Title_careerTitle,
        )
        SubTitleText(
            modifier = Modifier.padding(bottom = 16.dp),
            subTitleStringResId = R.string.resume2Screen_Title_careerSubTitle
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            isSelect = true,
            enabled = false,
            onClick = {}, // do nothing
            titleText = getPeriodFormatter(uiState.totalCareer)
        )
        OutlinedIconButton(
            contentColor = NonggleTheme.colorScheme.g3,
            disableContentColor = NonggleTheme.colorScheme.g3,
            borderColor = NonggleTheme.colorScheme.g_line,
            titleText = stringResource(R.string.resume2Screen_Title_careerAddTitle),
            titleTextStyle = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.g3),
            onClick = careerBottomSheetClick
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = uiState.careerList.size,
                key = { index -> uiState.careerList[index].id },
                itemContent = {index ->
                    CareerItem(
                        careerItemTitle = uiState.careerList[index].careerDescription,
                        careerItemDetail = uiState.careerList[index].careerDetail,
                        careerItemId = uiState.careerList[index].id,
                        careerPeriod = getPeriodFormatter(period = Period.between(uiState.careerList[index].careerStartDate, uiState.careerList[index].careerEndDate)),
                        deleteCareerItem = { onEvent(ResumeStep2Event.DeleteCareerItem(uiState.careerList[index].id)) }
                    )
                }
            )
        }
    }
}
