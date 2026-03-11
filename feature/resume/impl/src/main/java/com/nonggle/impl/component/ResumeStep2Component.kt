package com.nonggle.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nonggle.common.utils.getDateTimeFormatter
import com.nonggle.designsystem.component.DatePickerModal
import com.nonggle.designsystem.component.FullButton
import com.nonggle.designsystem.component.NonggleBottomSheet
import com.nonggle.designsystem.component.NonggleDialog
import com.nonggle.designsystem.component.NonggleIconButton
import com.nonggle.designsystem.component.NonggleTextField
import com.nonggle.designsystem.component.OutlinedIconButton
import com.nonggle.designsystem.component.TextFieldType
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume.impl.R
import com.nonggle.resume.impl.step2.CareerBottomSheetEvent
import com.nonggle.resume.impl.step2.CareerFormData

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
    sheetState: SheetState,
    uiState: CareerFormData,
    onEvent: (CareerBottomSheetEvent) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    var showStartDateDialog by remember { mutableStateOf(false) }
    var showEndDateDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()

    val enableSubmit by remember(uiState) {
        derivedStateOf {
            uiState.careerDescription.isNotEmpty()
                    && uiState.careerDetail.isNotEmpty()
                    && uiState.careerStartDate != null
                    && uiState.careerEndDate != null
        }
    }

    if (showStartDateDialog) {
        DatePickerModal(
            onDateSelected = { onEvent(CareerBottomSheetEvent.SelectCareerStartDate(it)) },
            onDismiss = { showStartDateDialog = false },
            datePickerState = startDatePickerState
        )
    }
    if (showEndDateDialog) {
        DatePickerModal(
            onDateSelected = { onEvent(CareerBottomSheetEvent.SelectCareerEndDate(it)) },
            onDismiss = { showEndDateDialog = false },
            datePickerState = endDatePickerState
        )
    }

    if (showCancelDialog) {
        CareerItemDeleteDialog(
            onDismiss = { showCancelDialog = false },
            onConfirm = {
                onEvent(CareerBottomSheetEvent.DeleteCareerItem)
                showCancelDialog = false
                onDismissRequest()
            }
        )
    }

    NonggleBottomSheet(
        height = 0.8f,
        sheetState = sheetState,
        onDismissRequest = { showCancelDialog = true },
        title = stringResource(R.string.resume2Screen_Title_careerAddTitle),
    ) {
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
                onEvent(CareerBottomSheetEvent.CareerDescriptionInput(it))
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            OutlinedIconButton(
                modifier = Modifier
                    .weight(1f),
                titleText = if(uiState.careerStartDate == null) stringResource(R.string.resume2Screen_label_CareerPeriodSelectDate) else getDateTimeFormatter(uiState.careerStartDate),
                contentColor = if(uiState.careerStartDate == null) NonggleTheme.colorScheme.g3 else NonggleTheme.colorScheme.black,
                onClick = { showStartDateDialog = true },
                icon = painterResource(id = R.drawable.date)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedIconButton(
                modifier = Modifier
                    .weight(1f),
                titleText = if(uiState.careerEndDate == null) stringResource(R.string.resume2Screen_label_CareerPeriodSelectDate) else getDateTimeFormatter(uiState.careerEndDate),
                contentColor = if(uiState.careerStartDate == null) NonggleTheme.colorScheme.g3 else NonggleTheme.colorScheme.black,
                onClick = { showEndDateDialog = true },
                icon = painterResource(id = R.drawable.date)
            )
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
                .height(200.dp),
            textFieldType = TextFieldType.Outlined,
            value = uiState.careerDetail,
            onValueChange = { onEvent(CareerBottomSheetEvent.CareerDetailInput(it)) },
            hintText = stringResource(R.string.resume2Screen_HintText_careerContent),
        )
        Spacer(modifier = Modifier.weight(1f))
        FullButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            enabled = enableSubmit,
            title = stringResource(R.string.resume2Screen_BottomSheet_AddButton),
            onClick = {
                onEvent(CareerBottomSheetEvent.AddCareerItem(uiState))
                onDismissRequest()
            },
        )
    }
}

@Composable
fun CareerItemDeleteDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
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
            .background(color = NonggleTheme.colorScheme.g4)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
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
fun CareerItemPreview() {
    CareerItem(
        careerItemTitle = "테스트",
        careerItemDetail = "테스트",
        careerItemId = "",
        careerPeriod = "1년 2개월"
    )
}