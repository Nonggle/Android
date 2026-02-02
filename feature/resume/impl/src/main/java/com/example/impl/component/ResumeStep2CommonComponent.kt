package com.example.impl.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core.designsystem.component.NonggleBottomSheet
import com.example.core.designsystem.theme.NonggleTheme
import com.example.feature.resume.impl.R

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
    onDismissRequest: () -> Unit = {},
) {
    NonggleBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = stringResource(R.string.resume2Screen_Title_careerTitle),
        content = {

        }
    )
}