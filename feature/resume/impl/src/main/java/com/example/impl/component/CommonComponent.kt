package com.example.feature.resume.impl.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.component.ContainedButton
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleBottomSheet
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.OutlinedButton
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.theme.NonggleTheme
import com.example.designsystem.component.Picker
import com.example.designsystem.component.rememberPickerState
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.step1.Gender
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun genderSelectBox(
    modifier: Modifier = Modifier,
    onSelectGender: (Gender) -> Unit,
    selectGenderResult: Gender, // 현재 해당 버튼을 통해 성별 유형을 정했는지 여부
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
            .clickable { onClick() }
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
fun birthDatePickerBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    years: List<Int>,
    months: List<Int>,
    days: List<Int>,
    selectBirthDate: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val yearValues = remember { years }
    val yearPickerState = rememberPickerState()
    val monthValues = remember { months }
    val monthPickerState = rememberPickerState()
    val dayValues = remember { days }
    val dayPickerState = rememberPickerState()

    NonggleBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 32.dp),
                    text = stringResource(R.string.resume1Screen_birthDateTitle),
                    textAlign = TextAlign.Start,
                    style = NonggleTheme.typography.b1_main,
                    color = NonggleTheme.colorScheme.black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Picker(
                        state = yearPickerState,
                        items = yearValues,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "년"
                    )
                    Picker(
                        state = monthPickerState,
                        items = monthValues,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "월"
                    )
                    Picker(
                        state = dayPickerState,
                        items = dayValues,
                        visibleItemsCount = 3,
                        modifier = Modifier.weight(0.3f),
                        textModifier = Modifier.padding(10.dp),
                        unit = "일"
                    )
                    FullButton(
                        modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                        onClick = {
                            selectBirthDate("${yearPickerState.selectedItem}${monthPickerState.selectedItem}${dayPickerState.selectedItem}")
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
    isCertificationExist: Boolean, // 현재 해당 버튼을 통해 성별 유형을 정했는지 여부
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
            isSelect = isCertificationExist,
        )
        OutlinedButton(
            modifier = modifier.weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_nocertificate),
            onClick = { onClickExistCertificationInfo(false) },
            isSelect = isCertificationExist,
        )
    }
}

@Composable
fun certificationInput(
    modifier: Modifier = Modifier,
    certificationName: String,
    certificationInput: (String) -> Unit,
    addCertificationList: (String) -> Unit,
    certificationList: List<String>,
    removeCertificationItem: (Int) -> Unit
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth()
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
                modifier = Modifier.padding(top = 16.dp),
                columns = GridCells.Adaptive(minSize = 128.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = certificationList.size,
                    key = { index -> certificationList[index] }
                ) { index ->
                    certificationChipItem(
                        title = certificationList[index],
                        removeChip = { removeCertificationItem(index) }
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
            .wrapContentWidth()
            .wrapContentHeight()
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
                        onClick = removeChip
                    ),
                painter = painterResource(R.drawable.xcircle),
                contentDescription = null,
            )
        }
    }
}

