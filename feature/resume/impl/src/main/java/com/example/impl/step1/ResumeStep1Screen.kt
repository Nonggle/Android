package com.example.feature.resume.impl.step1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.component.ContainedButton
import com.example.core.designsystem.component.FullButton
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.OutlinedButton
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.component.Picker
import com.example.feature.resume.impl.component.certificationChipItem
import com.example.feature.resume.impl.component.genderSelectButton
import com.example.feature.resume.impl.component.rememberPickerState
import com.example.impl.step1.Gender

//@Composable
//internal fun ResumeStep1Screen() {
//
//}

@Composable
internal fun ResumeStep1Screen(
    birthDate: String = "",
    selectUserCertificate: Boolean = false,
    haveCertificate: Boolean = false,
) {
    var userName by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.resume1Screen_profile_image),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g2,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.resume1Screen_introduceTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g2,
            )
            // 프로필 이미지
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_nameTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            nameInputField(
                value = userName,
                onValueChange = { userName = it },
                onClear = { userName = "" }
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_birthDateTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            birthDateSelectBox(birthDate = birthDate)
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_genderTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1
            )
            genderSelectBox(
                modifier = Modifier.padding(top = 12.dp),
                onSelectUserGender = {},
                isSelect = selectUserCertificate
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_label_certificate),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1
            )
            certificateSelectBox(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                onClick = {},
                haveCertificate = haveCertificate /// FIXME
            )
            if (haveCertificate) {

            }
        }
    }
}

@Composable
private fun nameInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit
) {

    NonggleTextField(
        modifier = Modifier
            .padding(bottom = 14.dp)
            .wrapContentHeight(),
        textFieldType = TextFieldType.Standard,
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            if (value.isNotEmpty()) {
                NonggleIconButton(
                    ImageResourceId = R.drawable.xcircle,
                    onClick = onClear
                )
            }
        },
        hintTextResId = R.string.resume1Screen_HintText_writeUserName,
    )
}

@Composable
private fun birthDateSelectBox(
    birthDate: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                // 생년월일 선택 필드
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = birthDate,
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

@Composable
private fun BirthDateSelectContent(
    modifier: Modifier = Modifier,
    years: List<String>,
    months: List<String>,
    days: List<String>,
    onClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val yearValues = remember { years }
    val yearPickerState = rememberPickerState()
    val monthValues = remember { months }
    val monthPickerState = rememberPickerState()
    val dayValues = remember { days }
    val dayPickerState = rememberPickerState()

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
            )
            Picker(
                state = monthPickerState,
                items = monthValues,
                visibleItemsCount = 3,
                modifier = Modifier.weight(0.3f),
                textModifier = Modifier.padding(10.dp),
            )
            Picker(
                state = dayPickerState,
                items = dayValues,
                visibleItemsCount = 3,
                modifier = Modifier.weight(0.3f),
                textModifier = Modifier.padding(10.dp),
            )
            FullButton(
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                onClick = {
                    onClick("${yearPickerState.selectedItem}${monthPickerState.selectedItem}${dayPickerState.selectedItem}")
                    onDismissRequest()
                },
                title = stringResource(R.string.resume1Screen_confirmBtnText)
            )
        }
    }
}

@Composable
private fun genderSelectBox(
    modifier: Modifier = Modifier,
    onSelectUserGender: (Gender) -> Unit,
    isSelect: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        genderSelectButton(
            modifier = modifier
                .weight(1f)
                .padding(end = 16.dp),
            text = stringResource(R.string.resume1Screen_label_women),
            selectGender = { onSelectUserGender(Gender.FEMALE) },
            userGender = Gender.FEMALE,
            isSelect = isSelect,
        )
        genderSelectButton(
            modifier = modifier
                .weight(1f),
            text = stringResource(R.string.resume1Screen_label_man),
            selectGender = { onSelectUserGender(Gender.MALE) },
            userGender = Gender.MALE,
            isSelect = isSelect,
        )
    }
}

@Composable
private fun certificateSelectBox(
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
    haveCertificate: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            isSelect = haveCertificate,
            titleText = stringResource(R.string.resume1Screen_label_havecertificate),
            onClick = { onClick(true) },
        )
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            isSelect = !haveCertificate,
            titleText = stringResource(R.string.resume1Screen_label_nocertificate),
            onClick = { onClick(false) },
        )
    }
}


@Composable
private fun certificateDetail(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    certificateTypeSubmit: () -> Unit,
    userSubmitCertificateList: List<String>,
    removeCertificateChip: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NonggleTextField(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                textFieldType = TextFieldType.Standard,
                value = value,
                onValueChange = onValueChange,
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = onClear
                        )
                    }
                },
                hintTextResId = R.string.resume1Screen_HintText_writeUserName,
            )
            //자격증 추가 버튼
            ContainedButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 13.dp),
                enabled = value.isNotEmpty(),
                onClick = certificateTypeSubmit,
                titleText = stringResource(R.string.resume1Screen_confirmBtnText),
                titleTextStyle = NonggleTheme.typography.b4_btn,
            )
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(
                count = userSubmitCertificateList.size,
                key = { index -> userSubmitCertificateList[index] }
            ) { index ->
                certificationChipItem(
                    title = userSubmitCertificateList[index],
                    removeChip = { removeCertificateChip(index) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDateSpinner() {
    BirthDateSelectContent(
        years = listOf("2000", "1999", "182939"),
        months = listOf("01", "02", "03"),
        days = listOf("1", "2", "3"),
        onClick = { },
        onDismissRequest = { }
    )
}
