package com.example.feature.resume.impl.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.component.ContainedButton
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleBottomSheet
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.OutlinedButton
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.theme.NonggleTheme
import com.example.core.designsystem.component.Picker
import com.example.core.designsystem.component.rememberPickerState
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.step1.CertificationData
import com.example.feature.resume.impl.step1.Gender
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun genderSelectBox(
    modifier: Modifier = Modifier,
    onSelectGender: (Gender) -> Unit,
    selectGenderResult: Gender?, // 현재 해당 버튼을 통해 성별 유형을 정했는지 여부
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            modifier = modifier
                .padding(end = 16.dp)
                .weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_women),
            onClick = { onSelectGender(Gender.FEMALE) },
            isSelect = selectGenderResult == Gender.FEMALE,
        )
        OutlinedButton(
            modifier = modifier.weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_man),
            onClick = { onSelectGender(Gender.MALE) },
            isSelect = selectGenderResult == Gender.MALE,
        )
    }
}

// 날짜 선택 박스로 공통으로 쓰일 컴포넌트
@Composable
fun dateSelectBox(
    hintText: String,
    selectDate: String,
    onClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectDate.ifEmpty { hintText },
                style = NonggleTheme.typography.b1_main,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.date),
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePickerBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    selectBirthDate: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
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
    val dayPickerState = rememberPickerState()

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

    val selectedMonth: Int =
        monthItems.getOrNull(monthPickerState.selectedIndex)?.toIntOrNull() ?: 1

    //선택된 년/월에 맞는 일 계산
    val dayInSelectedMonth: Int = remember(selectedYear, selectedMonth) {
        YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
    }

    val lastSelectableDay: Int =
        remember(selectedYear, selectedMonth, maxDate, dayInSelectedMonth) {
            if (selectedYear == maxYear && selectedMonth == maxDate.monthValue) {
                minOf(maxDate.dayOfMonth, dayInSelectedMonth)
            } else {
                dayInSelectedMonth
            }
        }

    val dayItems: List<String> = remember(selectedYear, selectedMonth, lastSelectableDay) {
        (1..lastSelectableDay).map { "%02d".format(it) }
    }

    LaunchedEffect(dayItems.size) {
        if (dayItems.isNotEmpty() && dayPickerState.selectedIndex > dayItems.lastIndex) {
            dayPickerState.selectedIndex = dayItems.lastIndex
        }
    }

    NonggleBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = stringResource(R.string.resume1Screen_birthDateTitle),
        content = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Picker(
                        state = yearPickerState,
                        items = yearItems,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "년"
                    )
                    Picker(
                        state = monthPickerState,
                        items = monthItems,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "월"
                    )
                    Picker(
                        state = dayPickerState,
                        items = dayItems,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "일"
                    )
                    FullButton(
                        modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                        onClick = {
                            val y = yearItems.getOrNull(yearPickerState.selectedIndex)
                                ?: maxYear.toString()
                            val m = monthItems.getOrNull(monthPickerState.selectedIndex) ?: "01"
                            val d = dayItems.getOrNull(dayPickerState.selectedIndex) ?: "01"

                            selectBirthDate("$y-$m-$d")
                            onDismissRequest()
                        },
                        title = stringResource(R.string.resume1Screen_confirmBtnText)
                    )
                }
            }
        }
    )
}

@Composable
fun certificateSelectBox(
    modifier: Modifier = Modifier,
    onClickExistCertificationInfo: (Boolean) -> Unit,
    isCertificationExist: Boolean?, // 현재 해당 버튼을 통해 성별 유형을 정했는지 여부
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            modifier = modifier
                .padding(end = 16.dp)
                .weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_havecertificate),
            onClick = { onClickExistCertificationInfo(true) },
            isSelect = isCertificationExist == true,
        )
        OutlinedButton(
            modifier = modifier.weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_nocertificate),
            onClick = { onClickExistCertificationInfo(false) },
            isSelect = isCertificationExist == false,
        )
    }
}

@Composable
fun certificationInput(
    modifier: Modifier = Modifier,
    certificationName: String,
    certificationInput: (String) -> Unit,
    addCertificationList: (String) -> Unit,
    certificationList: List<CertificationData>,
    removeCertificationItem: (String) -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NonggleTextField(
                modifier = Modifier.weight(7f),
                textFieldType = TextFieldType.Standard,
                value = certificationName,
                onValueChange = certificationInput,
                trailingIcon = {
                    if (certificationName.isNotEmpty()) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = { certificationInput("") }
                        )
                    }
                },
                hintTextResId = R.string.resume1Screen_certificateDetail_inputHintText,
            )
            ContainedButton(
                modifier = Modifier.weight(3f),
                onClick = { addCertificationList(certificationName) },
                titleText = stringResource(R.string.resume1Screen_confirmBtnText),
            )
        }
        if (certificationList.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 40.dp)
                    .heightIn(max = 200.dp),
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = certificationList.size,
                    key = { index -> certificationList[index].id }
                ) { index ->
                    certificationChipItem(
                        //modifier = Modifier.fillMaxWidth(),
                        title = certificationList[index].certificationTitle,
                        removeChip = { removeCertificationItem(certificationList[index].id) }
                    )
                }
            }
        }
    }

}


@Composable
fun certificationChipItem(
    modifier: Modifier = Modifier,
    title: String,
    removeChip: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = NonggleTheme.colorScheme.g4,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = NonggleTheme.typography.b2_sub
            )
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = removeChip
                    ),
                painter = painterResource(R.drawable.xcircle),
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun birthDateSpinnerPreview() {
    val sheetState = rememberModalBottomSheetState()
    NonggleTheme {
        BirthDatePickerBottomSheet(
            sheetState = sheetState,
        )
    }
}

