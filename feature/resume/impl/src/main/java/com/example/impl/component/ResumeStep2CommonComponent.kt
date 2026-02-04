package com.example.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleBottomSheet
import com.example.core.designsystem.component.NonggleDialog
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.OutlinedButton
import com.example.core.designsystem.component.OutlinedIconButton
import com.example.core.designsystem.component.Picker
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.component.rememberPickerState
import com.example.core.designsystem.theme.NonggleTheme
import com.example.designsystem.component.NonggleDropDown
import com.example.designsystem.component.rememberExposedMenuStateHolder
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.step2.CareerBottomSheetEvent
import com.example.feature.resume.impl.step2.CareerFormData
import java.time.LocalDate

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    titleStringResId: Int,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = titleStringResId),
        style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.black)
    )
}

@Composable
fun SubTitleText(
    modifier: Modifier = Modifier,
    subTitleStringResId: Int,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = subTitleStringResId),
        style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareerBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    uiState: CareerFormData,
    onEvent: (CareerBottomSheetEvent) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {

    var showStartDateDialog by remember { mutableStateOf(false) }
    var showEndDateDialog by remember { mutableStateOf(false) }
    var showDismissBottomSheetDialog by remember { mutableStateOf(false) }

    val stateHolder = rememberExposedMenuStateHolder()

    val enableSubmit by remember {
        derivedStateOf {
            uiState.careerDescription.isNotEmpty()
                    && uiState.careerDetail.isNotEmpty()
                    && uiState.careerStartDate.isNotEmpty()
                    && (uiState.careerEndDate != null || uiState.careerPeriod != null)
        }
    }

    if (showStartDateDialog) {
        PeridSettingDialog(
            onDismiss = { showStartDateDialog = false },
            onDateInput = { onEvent(CareerBottomSheetEvent.SelectCareerStartDate(it)) },
            isStartDateDialog = true
        )
    }
    if (showEndDateDialog) {
        PeridSettingDialog(
            onDismiss = { showEndDateDialog = false },
            onDateInput = { onEvent(CareerBottomSheetEvent.SelectCareerEndDate(it)) },
            isStartDateDialog = false
        )
    }

    if (showDismissBottomSheetDialog) {
        CareerItemDeleteDialog(
            onDismiss = { showDismissBottomSheetDialog = false },
            onConfirm = {
                onEvent(CareerBottomSheetEvent.DeleteCareerItem)
                showDismissBottomSheetDialog = false
                onDismissRequest()
            }
        )
    }

    NonggleBottomSheet(
        modifier = modifier.fillMaxHeight(0.8f),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = stringResource(R.string.resume2Screen_Title_careerAddTitle),
        content = {
            Column(

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.resume2Screen_SubTitle_careerDescribeTitle),
                            style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1)
                        )
                        NonggleTextField(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                            textFieldType = TextFieldType.Standard,
                            value = uiState.careerDescription,
                            onValueChange = {
                                onEvent(
                                    CareerBottomSheetEvent.CareerDescriptionInput(
                                        it
                                    )
                                )
                            },
                            trailingIcon = {
                                if (uiState.careerDescription.isNotEmpty()) {
                                    NonggleIconButton(
                                        image = painterResource(R.drawable.xcircle),
                                        onClick = {
                                            onEvent(
                                                CareerBottomSheetEvent.CareerDescriptionInput(
                                                    ""
                                                )
                                            )
                                        }
                                    )
                                }
                            },
                            hintText = stringResource(R.string.resume2Screen_HintText_careerDescribe),
                        )
                        Text(
                            modifier = Modifier.padding(top = 32.dp),
                            text = stringResource(id = R.string.resume2Screen_Title_careerPeriod),
                            style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1)
                        )
                        // 작업 기간 1개월 이상 여부 고르는 버튼
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                modifier = modifier
                                    .padding(end = 16.dp)
                                    .weight(1f),
                                titleText = stringResource(R.string.resume2Screen_label_LessOneMonth),
                                onClick = {
                                    onEvent(
                                        CareerBottomSheetEvent.SelectCareerPeriodOverOneMonth(
                                            false
                                        )
                                    )
                                },
                                isSelect = uiState.isCareerOverOneMonth == false,
                            )
                            OutlinedButton(
                                modifier = modifier.weight(1f),
                                titleText = stringResource(R.string.resume2Screen_label_MoreOneMonth),
                                onClick = {
                                    onEvent(
                                        CareerBottomSheetEvent.SelectCareerPeriodOverOneMonth(
                                            true
                                        )
                                    )
                                },
                                isSelect = uiState.isCareerOverOneMonth == true,
                            )
                        }
                        // 1개월 이상일 경우 보여지는 기간 선택 위젯
                        if (uiState.isCareerOverOneMonth == true) {
                            Row(
                                modifier = modifier.fillMaxWidth()
                            ) {
                                OutlinedIconButton(
                                    modifier = modifier
                                        .padding(end = 16.dp)
                                        .weight(1f),
                                    titleText = uiState.careerStartDate.ifEmpty { stringResource(R.string.resume2Screen_label_CareerPeriodSelectDate) },
                                    onClick = { showStartDateDialog = true },
                                    icon = painterResource(id = R.drawable.date)
                                )
                                OutlinedIconButton(
                                    modifier = modifier
                                        .weight(1f),
                                    titleText = uiState.careerEndDate
                                        ?: stringResource(R.string.resume2Screen_label_CareerPeriodSelectDate),
                                    onClick = { showEndDateDialog = true },
                                    icon = painterResource(id = R.drawable.date)
                                )
                            }
                        } else { // 1개월 미만일때
                            Row(modifier = modifier.fillMaxWidth()) {
                                OutlinedIconButton(
                                    modifier = modifier
                                        .padding(end = 16.dp)
                                        .weight(1f),
                                    titleText = uiState.careerStartDate.ifEmpty { stringResource(R.string.resume2Screen_label_CareerPeriodSelectDate) },
                                    onClick = { showStartDateDialog = true },
                                    icon = painterResource(id = R.drawable.date)
                                )
                                NonggleDropDown(
                                    modifier = modifier.weight(1f),
                                    onClick = { stateHolder.onEnabled(true) },
                                    title = uiState.careerPeriod
                                        ?: stringResource(R.string.resume2Screen_label_CareerPeriodSelectOnlyDate),
                                    stateHolder = stateHolder,
                                    selectValue = {
                                        onEvent(
                                            CareerBottomSheetEvent.SelectCareerPeriodDate(
                                                stateHolder.value
                                            )
                                        )
                                    },
                                    icon = painterResource(R.drawable.caretdown),
                                    titleColor = NonggleTheme.colorScheme.g3
                                )
                            }
                        }
                        Text(
                            modifier = Modifier.padding(top = 32.dp),
                            text = stringResource(id = R.string.resume2Screen_Title_careerContent),
                            style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g1)
                        )
                        NonggleTextField(
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 20.dp)
                                .fillMaxWidth()
                                .height(144.dp),
                            textFieldType = TextFieldType.Standard,
                            value = uiState.careerDetail,
                            onValueChange = { onEvent(CareerBottomSheetEvent.CareerDetailInput(it)) },
                            hintText = stringResource(R.string.resume2Screen_HintText_careerContent),
                        )
                    }
                }
                FullButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    enabled = enableSubmit,
                    title = stringResource(R.string.resume2Screen_BottomSheet_AddButton),
                    onClick = {
                        onEvent(CareerBottomSheetEvent.AddCareerItem(uiState))
                        onDismissRequest()
                    },
                )
            }
        }
    )
}

@Composable
fun PeridSettingDialog(
    onDismiss: () -> Unit = {},
    onDateInput: (String) -> Unit = {},
    isStartDateDialog: Boolean = true,
) {
    val maxDate = LocalDate.now()
    val minYear = 1900
    val maxYear = maxDate.year

    val yearItems: List<String> = remember(maxYear) {
        (minYear..maxYear).map { it.toString() }
    }

    val monthItemsAll: List<String> = remember {
        (1..12).map { "%02d".format(it) }
    }

    val yearPickerState = rememberPickerState()
    val monthPickerState = rememberPickerState()

    // 현재 선택된 값(없으면 안전한 기본값)
    val selectedYear: Int =
        yearItems.getOrNull(yearPickerState.selectedIndex)?.toIntOrNull() ?: maxYear

    // 선택년도가 올해면 현시점 월까지만 아니라면 12월까지 리스트에 포함
    val monthItems: List<String> = remember(selectedYear, maxDate) {
        if (selectedYear == maxYear) {
            (1..maxDate.monthValue).map { "%02d".format(it) }
        } else {
            monthItemsAll
        }
    }

    // monthItems가 줄어들 때 선택 인덱스가 범위를 벗어나면 보정
    LaunchedEffect(monthItems.size) {
        if (monthItems.isNotEmpty() && monthPickerState.selectedIndex > monthItems.lastIndex) {
            monthPickerState.selectedIndex = monthItems.lastIndex
        }
    }

    NonggleDialog(
        onDismiss = onDismiss,
        onConfirm = {
            val y = yearItems.getOrNull(yearPickerState.selectedIndex)
                ?: maxYear.toString()
            val m = monthItems.getOrNull(monthPickerState.selectedIndex) ?: "01"
            onDateInput("$y-$m")
            onDismiss()
        },
        dialogTitle = stringResource(if (isStartDateDialog) R.string.resume2Screen_label_WorkStart else R.string.resume2Screen_label_WorkEnd),
        dialogContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Picker(
                    state = yearPickerState,
                    items = yearItems,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.5f),
                    textModifier = Modifier.padding(10.dp),
                    unit = "년"
                )
                Picker(
                    state = monthPickerState,
                    items = monthItems,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.5f),
                    textModifier = Modifier.padding(10.dp),
                    unit = "월"
                )
            }
        }
    )
}

@Composable
fun CareerItemDeleteDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},// bottomsheet 없애는 기능
) {
    NonggleDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        dialogTitle = stringResource(R.string.resume2Screen_DialogTitle_CareerDelete),
        dialogContent = {
            Text(
                text = stringResource(R.string.resume2Screen_DialogDetail_CareerDelete),
                style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
            )
        }
    )
}

@Composable
fun CareerItem(
    modifier: Modifier = Modifier,
    careerItemTitle: String,
    careerItemDetail: String,
    careerItemId: String,
    careerPeriod: String,
    deleteCareerItem: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .background(color = NonggleTheme.colorScheme.g4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = careerItemTitle,
                    style = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.black)
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = careerPeriod,
                    style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = careerItemDetail,
                    style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
                )
            }
            NonggleIconButton(
                onClick = {}, // TODO: 편집 기능 구현 예정
                image = painterResource(R.drawable.pencil),
                iconColor = NonggleTheme.colorScheme.g2,
                iconWidth = 24.dp,
                iconHeight = 24.dp
            )
            NonggleIconButton(
                onClick = { deleteCareerItem(careerItemId) },
                image = painterResource(R.drawable.xcircle),
                iconColor = NonggleTheme.colorScheme.g2,
                iconWidth = 24.dp,
                iconHeight = 24.dp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PeriodSettingPreviewDialog() {
    PeridSettingDialog()
}