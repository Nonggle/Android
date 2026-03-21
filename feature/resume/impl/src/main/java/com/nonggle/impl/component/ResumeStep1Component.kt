package com.nonggle.resume.impl.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nonggle.designsystem.component.ContainedButton
import com.nonggle.designsystem.component.DatePickerModal
import com.nonggle.designsystem.component.NonggleIconButton
import com.nonggle.designsystem.component.NonggleTextField
import com.nonggle.designsystem.component.OutlinedButton
import com.nonggle.designsystem.component.TextFieldType
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.designsystem.component.NonggleChip
import com.nonggle.feature.resume.impl.R
import com.nonggle.resume.impl.step1.CertificationTag
import com.nonggle.resume.impl.step1.Gender
import java.time.LocalDate
import kotlin.collections.isNotEmpty

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
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_women),
            onClick = { onSelectGender(Gender.FEMALE) },
            isSelect = selectGenderResult == Gender.FEMALE,
        )
        OutlinedButton(
            modifier = Modifier.weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_man),
            onClick = { onSelectGender(Gender.MALE) },
            isSelect = selectGenderResult == Gender.MALE,
        )
    }
}

// 날짜 선택 박스로 공통으로 쓰일 컴포넌트
@Composable
fun dateSelectBox(
    modifier: Modifier = Modifier,
    hintText: String,
    selectDate: String,
    onClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(
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
                style = NonggleTheme.typography.b1_main.copy(color = if(selectDate.isEmpty()) NonggleTheme.colorScheme.g3 else NonggleTheme.colorScheme.black),
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
fun BirthDateSelectDialog(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    datePickerState: DatePickerState
) {
    DatePickerModal(
        modifier = modifier,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss,
        datePickerState = datePickerState
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
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            titleText = stringResource(R.string.resume1Screen_label_havecertificate),
            onClick = { onClickExistCertificationInfo(true) },
            isSelect = isCertificationExist == true,
        )
        OutlinedButton(
            modifier = Modifier.weight(1f),
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
    certificationList: List<CertificationTag>,
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
                            image = painterResource(R.drawable.xcircle),
                            onClick = { certificationInput("") }
                        )
                    }
                },
                hintText = stringResource(R.string.resume1Screen_certificateDetail_inputHintText),
            )
            ContainedButton(
                enabled = certificationName.isNotEmpty(),
                modifier = Modifier.weight(3f),
                onClick = { addCertificationList(certificationName) },
                titleText = stringResource(R.string.resume1Screen_confirmBtnText),
            )
        }
        if (certificationList.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                certificationList.forEach { item ->
                    NonggleChip(
                        title = item.certificationTitle,
                        removeChip = { removeCertificationItem(item.id) }
                    )
                }
            }
        }
    }

}
