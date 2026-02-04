package com.example.feature.resume.impl.step3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.component.ContainedButton
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.component.NonggleTextField
import com.example.core.designsystem.component.TextFieldType
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R

@Composable
internal fun ResumeStep3Screen(
    modifier: Modifier = Modifier,
    viewModel: ResumeStep3ViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResumeStep3Screen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent
    )
}

@Composable
internal fun ResumeStep3Screen(
    modifier: Modifier = Modifier,
    uiState: ResumeStep3State,
    onEvent: (ResumeStep3Event) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.resume3Screen_Title_Introduce),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            NonggleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textFieldType = TextFieldType.Standard,
                value = uiState.introduce ?: "",
                onValueChange = {value -> onEvent(ResumeStep3Event.IntroduceChanged(value))},
                trailingIcon = {
                    if (!uiState.introduce.isNullOrEmpty()) {
                        NonggleIconButton(
                            image = painterResource(R.drawable.xcircle),
                            onClick = { onEvent(ResumeStep3Event.IntroduceChanged("")) }
                        )
                    }
                },
                hintText = stringResource(R.string.resume3Screen_HintText_Introduce),
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume3Screen_Title_personality),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NonggleTextField(
                    modifier = Modifier.weight(7f),
                    textFieldType = TextFieldType.Standard,
                    value = uiState.personality ?: "",
                    onValueChange = {value -> onEvent(ResumeStep3Event.PersonalityInput(value))},
                    trailingIcon = {
                        if (!uiState.personality.isNullOrEmpty()) {
                            NonggleIconButton(
                                image = painterResource(R.drawable.xcircle),
                                onClick = { onEvent(ResumeStep3Event.PersonalityInput("")) }
                            )
                        }
                    },
                    hintText = stringResource(R.string.resume3Screen_HintText_personality),
                )
                ContainedButton(
                    modifier = Modifier.weight(3f),
                    enabled = uiState.personality.isNullOrEmpty(),
                    onClick = { onEvent(ResumeStep3Event.AddPersonalityChip) },
                    titleText = stringResource(R.string.resume1Screen_confirmBtnText),
                )
            }
            if(uiState.personalityList.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .heightIn(max = 200.dp),
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = uiState.personalityList.size,
                        key = { index -> uiState.personalityList[index].id }
                    ) { index ->
                        personalityChipItem(
                            title = uiState.personalityList[index].personality,
                            removeChip = { onEvent(ResumeStep3Event.RemovePersonalityChip(uiState.personalityList[index].id)) }
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume3Screen_Title_extra),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
            NonggleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp),
                textFieldType = TextFieldType.Standard,
                value = uiState.introduceDetail ?: "",
                onValueChange = {value -> onEvent(ResumeStep3Event.IntroduceDetailInput(value))},
                hintText = stringResource(R.string.resume3Screen_HintText_extra),
            )
        }
    }
}

/// TODO: 디자인시스템으로 칩 공통화
@Composable
fun personalityChipItem(
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