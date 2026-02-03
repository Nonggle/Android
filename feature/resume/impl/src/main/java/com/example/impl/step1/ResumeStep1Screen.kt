package com.example.feature.resume.impl.step1

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R
import com.example.feature.resume.impl.component.BirthDatePickerBottomSheet
import com.example.feature.resume.impl.component.certificateSelectBox
import com.example.feature.resume.impl.component.certificationInput
import com.example.feature.resume.impl.component.dateSelectBox
import com.example.feature.resume.impl.component.genderSelectBox

/**
 * 상태와 로직을 모두 관리하는 'Stateful' 컨테이너 컴포저블
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep1Screen(
    modifier: Modifier = Modifier,
    viewModel: ResumeStep1ViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { viewModel.setEvent(ResumeStep1Event.SelectImage(it)) }
        }
    )

    // BottomSheet와 관련된 UI 상태는 Screen 레벨에서 관리
    var showBottomSheet by remember { mutableStateOf(false) }
    val birthDateBottomSheetState = rememberModalBottomSheetState()

    ResumeStep1Content(
        modifier = modifier,
        uiState = uiState, // 1. 상태 객체를 통째로 전달
        onEvent = viewModel::setEvent, // 2. 이벤트 핸들러를 단일 통로로 전달
        onProfileImageClick = { // 3. UI 로직(런처 실행)은 여기서 처리
            imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        showBottomSheet = showBottomSheet,
        birthDateBottomSheetState = birthDateBottomSheetState,
        onBirthDateClick = { showBottomSheet = true },
        onBirthDatePickerDismiss = { showBottomSheet = false }
    )
}

/**
 * 오직 UI를 그리는 책임만 가지는 'Stateless' 프리젠터 컴포저블
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep1Content(
    modifier: Modifier = Modifier,
    uiState: ResumeStep1State,
    onEvent: (ResumeStep1Event) -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    showBottomSheet: Boolean = false,
    birthDateBottomSheetState: SheetState,
    onBirthDateClick: () -> Unit = {},
    onBirthDatePickerDismiss: () -> Unit = {},
) {
    if (showBottomSheet) {
        BirthDatePickerBottomSheet(
            sheetState = birthDateBottomSheetState,
            selectBirthDate = { date -> onEvent(ResumeStep1Event.BirthDateChanged(date)) },
            onDismissRequest = onBirthDatePickerDismiss
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.resume1Screen_profile_image),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.resume1Screen_introduceTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g2,
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(96.dp)
                    .background(
                        color = NonggleTheme.colorScheme.g_line_light,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource()},
                        indication = LocalIndication.current,
                        onClick = onProfileImageClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.info.profileImageUrl != null) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = uiState.info.profileImageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.imageupload),
                        placeholder = painterResource(R.drawable.imageupload),
                    )
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource()},
                                indication = LocalIndication.current,
                                onClick = { onEvent(ResumeStep1Event.RemoveProfileImage) }
                            ),
                        painter = painterResource(R.drawable.xcircle),
                        contentDescription = null,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.imageupload),
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_nameTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            NonggleTextField(
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .wrapContentHeight(),
                textFieldType = TextFieldType.Standard,
                value = uiState.info.userName,
                onValueChange = { userName -> onEvent(ResumeStep1Event.UserNameChanged(userName)) },
                trailingIcon = {
                    if (uiState.info.userName.isNotEmpty()) {
                        NonggleIconButton(
                            ImageResourceId = R.drawable.xcircle,
                            onClick = { onEvent(ResumeStep1Event.UserNameCleared) }
                        )
                    }
                },
                hintText = stringResource(R.string.resume1Screen_HintText_writeUserName),
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_birthDateTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            dateSelectBox(
                hintText = stringResource(R.string.resume1Screen_birthDateSubTitle),
                selectDate = uiState.info.birthDate,
                onClick = onBirthDateClick,
                paddingValues = PaddingValues(top = 8.dp)
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_genderTitle),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1
            )
            genderSelectBox(
                modifier = Modifier.padding(top = 12.dp),
                onSelectGender = { gender -> onEvent(ResumeStep1Event.SelectGender(gender)) },
                selectGenderResult = uiState.info.gender
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_label_certificate),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1
            )
            certificateSelectBox(
                modifier = Modifier.padding(top = 12.dp),
                onClickExistCertificationInfo = { exist -> onEvent(ResumeStep1Event.ExistCertification(exist)) },
                isCertificationExist = uiState.certificationExist
            )
            if(uiState.certificationExist == true) {
                certificationInput(
                    modifier = Modifier.padding(top = 12.dp),
                    certificationName = uiState.certificationInput,
                    certificationInput = { newValue -> onEvent(ResumeStep1Event.CertificationChanged(newValue)) },
                    addCertificationList = { onEvent(ResumeStep1Event.AddCertification) },
                    certificationList = uiState.info.certificationList,
                    removeCertificationItem = { onEvent(ResumeStep1Event.RemoveCertificationChip(it)) },
                )
            }
        }
    }
}