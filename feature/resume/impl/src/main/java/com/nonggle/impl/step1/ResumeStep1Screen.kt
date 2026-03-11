package com.nonggle.resume.impl.step1

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.nonggle.common.policy.Policy
import com.nonggle.common.utils.getImageSizeFromUri
import com.nonggle.designsystem.component.NonggleIconButton
import com.nonggle.designsystem.component.NonggleTextField
import com.nonggle.designsystem.component.TextFieldType
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume.impl.R
import com.nonggle.resume.impl.component.BirthDateSelectDialog
import com.nonggle.resume.impl.component.certificateSelectBox
import com.nonggle.resume.impl.component.certificationInput
import com.nonggle.resume.impl.component.dateSelectBox
import com.nonggle.resume.impl.component.genderSelectBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep1Screen(
    modifier: Modifier = Modifier,
    viewModel: ResumeStep1ViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState()

    var showDateSelectDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.certificationExist, uiState.info.certificationList.size) {
        if (uiState.certificationExist == true) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ResumeStep1Effect.SendToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            val sizeInBytes = getImageSizeFromUri(context, uri)
            val sizeInMB = sizeInBytes / (1024.0 * 1024.0)
            if (sizeInMB > Policy.MAX_PROFILE_IMAGE_SIZE_IN_BYTES) {
                viewModel.setEvent(ResumeStep1Event.ImageVolumeExceeded(message = "업로드 가능한 이미지 용량을 초과했습니다."))
                return@rememberLauncherForActivityResult
            } else {
                uri.let { viewModel.setEvent(ResumeStep1Event.SelectImage(it)) }
            }
        }
    )

    ResumeStep1Screen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        onProfileImageClick = {
            imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        showDateSelectDialog = showDateSelectDialog,
        datePickerState = datePickerState,
        onBirthDateClick = { showDateSelectDialog = true },
        onBirthDatePickerDismiss = { showDateSelectDialog = false },
        scrollState = scrollState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResumeStep1Screen(
    modifier: Modifier = Modifier,
    uiState: ResumeStep1State,
    onEvent: (ResumeStep1Event) -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    showDateSelectDialog: Boolean = false,
    datePickerState: DatePickerState,
    onBirthDateClick: () -> Unit = {},
    onBirthDatePickerDismiss: () -> Unit = {},
    scrollState: ScrollState,
) {
    if (showDateSelectDialog) {
        BirthDateSelectDialog(
            datePickerState = datePickerState,
            onDateSelected = { date -> onEvent(ResumeStep1Event.BirthDateChanged(date)) },
            onDismiss = onBirthDatePickerDismiss
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
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
                    onClick = onProfileImageClick
                ),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.info.profileImageUrl != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
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
                            interactionSource = remember { MutableInteractionSource() },
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
        NonggleTextField(
            modifier = Modifier
                .padding(bottom = 14.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            label = {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(R.string.resume1Screen_nameTitle),
                    style = NonggleTheme.typography.b2_sub,
                    color = NonggleTheme.colorScheme.g1,
                )
            },
            supportText = {
                if (uiState.info.userName.isEmpty()) {
                    Text(
                        text = stringResource(R.string.resume1Screen_supportTitle),
                        style = NonggleTheme.typography.HintTextAppearance.copy(color = NonggleTheme.colorScheme.error)
                    )
                }
            },
            isError = uiState.info.userName.isEmpty(),
            textFieldType = TextFieldType.Standard,
            value = uiState.info.userName,
            maxLength = 4,
            onValueChange = { userName -> onEvent(ResumeStep1Event.UserNameChanged(userName)) },
            trailingIcon = {
                if (uiState.info.userName.isNotEmpty()) {
                    NonggleIconButton(
                        image = painterResource(R.drawable.xcircle),
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
            modifier = Modifier.padding(top = 4.dp),
            hintText = stringResource(R.string.resume1Screen_birthDateSubTitle),
            selectDate = uiState.info.birthDate ?: stringResource(R.string.resume1Screen_birthDateSubTitle),
            onClick = onBirthDateClick,
            paddingValues = PaddingValues(top = 8.dp)
        )
        if (uiState.info.birthDate?.isEmpty() == true) {
            Text(
                text = stringResource(R.string.resume1Screen_ErrorTitle_BirthDateInput),
                style = NonggleTheme.typography.HintTextAppearance.copy(color = NonggleTheme.colorScheme.error)
            )
        }
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
        if (uiState.info.gender == null) {
            Text(
                text = stringResource(R.string.resume1Screen_ErrorTitle_GenderInput),
                style = NonggleTheme.typography.HintTextAppearance.copy(color = NonggleTheme.colorScheme.error)
            )
        }
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(R.string.resume1Screen_label_certificate),
            style = NonggleTheme.typography.b2_sub,
            color = NonggleTheme.colorScheme.g1
        )
        certificateSelectBox(
            modifier = Modifier.padding(top = 12.dp),
            onClickExistCertificationInfo = { exist ->
                onEvent(
                    ResumeStep1Event.ExistCertification(
                        exist
                    )
                )
            },
            isCertificationExist = uiState.certificationExist
        )
        if (uiState.certificationExist == true) {
            certificationInput(
                modifier = Modifier.padding(top = 12.dp),
                certificationName = uiState.certificationInput,
                certificationInput = { newValue ->
                    onEvent(
                        ResumeStep1Event.CertificationChanged(
                            newValue
                        )
                    )
                },
                addCertificationList = { onEvent(ResumeStep1Event.AddCertification) },
                certificationList = uiState.info.certificationList,
                removeCertificationItem = { onEvent(ResumeStep1Event.RemoveCertificationChip(it)) },
            )
        }
    }
}