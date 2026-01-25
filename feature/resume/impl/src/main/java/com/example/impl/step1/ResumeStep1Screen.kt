package com.example.impl.step1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.NonggleIconButton
import com.example.designsystem.component.NonggleTextField
import com.example.designsystem.component.OutlinedButton
import com.example.designsystem.component.TextFieldType
import com.example.impl.Gender
import com.example.impl.R

//@Composable
//internal fun ResumeStep1Screen() {
//
//}

@Composable
internal fun ResumeStep1Screen(

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
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.resume1Screen_introduceTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.surfaceTint,
            )
            // 프로필 이미지
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_nameTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            nameInputField(
                value = userName,
                onValueChange = { userName = it },
                onClear = { userName = "" }
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_birthDateTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            birthDateSelectBox()
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_genderTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            genderSelectBox(
                modifier = Modifier.padding(top = 12.dp),
                userGender = {  },
                onSelectUserGender = {},
                isSelect = {}
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_label_certificate),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            certificateSelectBox(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                onClick = {},
                haveCertificate = true /// FIXME
            )
            if(state.haveCertificate) {

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
        placeholder = {
            Text(
                text = stringResource(R.string.resume1Screen_HintText_writeUserName),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surfaceTint,
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                style = MaterialTheme.typography.labelLarge,
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
private fun genderSelectBox(
    modifier: Modifier = Modifier,
    userGender: String,
    onSelectUserGender: (Gender) -> Unit,
    isSelect: (Gender) -> Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            isSelect = isSelect(Gender.FEMALE),
            titleText = stringResource(R.string.resume1Screen_label_women),
            onClick = { onSelectUserGender(Gender.FEMALE) },
            titleTextStyle = MaterialTheme.typography.bodyLarge,
            enableColor = MaterialTheme.colorScheme.outline,
            enableContentColor = MaterialTheme.colorScheme.outline,
            pressedColor = MaterialTheme.colorScheme.primary,
            selectColor = MaterialTheme.colorScheme.primary,
        )
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            isSelect = isSelect(Gender.MALE),
            titleText = stringResource(R.string.resume1Screen_label_man),
            onClick = { onSelectUserGender(Gender.MALE) },
            titleTextStyle = MaterialTheme.typography.bodyLarge,
            enableColor = MaterialTheme.colorScheme.outline,
            enableContentColor = MaterialTheme.colorScheme.outline,
            pressedColor = MaterialTheme.colorScheme.primary,
            selectColor = MaterialTheme.colorScheme.primary,
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
            titleTextStyle = MaterialTheme.typography.bodyLarge,
            enableColor = MaterialTheme.colorScheme.outline,
            enableContentColor = MaterialTheme.colorScheme.outline,
            pressedColor = MaterialTheme.colorScheme.primary,
            selectColor = MaterialTheme.colorScheme.primary,
        )
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            isSelect = !haveCertificate,
            titleText = stringResource(R.string.resume1Screen_label_nocertificate),
            onClick = { onClick(false) },
            titleTextStyle = MaterialTheme.typography.bodyLarge,
            enableColor = MaterialTheme.colorScheme.outline,
            enableContentColor = MaterialTheme.colorScheme.outline,
            pressedColor = MaterialTheme.colorScheme.primary,
            selectColor = MaterialTheme.colorScheme.primary,
        )
    }
}

