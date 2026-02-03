package com.example.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.core.designsystem.theme.NonggleTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.core.designsystem.R

/// Toast, Dialog
@Composable
fun NonggleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dialogTitle: String,
    dialogTitleStyle: TextStyle = NonggleTheme.typography.t3,
    dialogTitleColor: Color = NonggleTheme.colorScheme.black,
    dialogContent: @Composable () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,

            ),
        onDismissRequest = onDismiss,
        content = {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = NonggleTheme.colorScheme.white)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp, top = 8.dp),
                        text = dialogTitle,
                        style = dialogTitleStyle.copy(color = dialogTitleColor)
                    )
                    dialogContent()
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // 버튼 사이 간격
                    ) {
                        // 취소 버튼
                        ContainedButton(
                            modifier = Modifier.weight(1f),
                            titleText = stringResource(R.string.dialog_Cancel_ButtonTitle),
                            backgroundColor = NonggleTheme.colorScheme.g4,
                            onClick = onDismiss
                        )
                        // 확인 버튼
                        ContainedButton(
                            modifier = Modifier.weight(1f),
                            titleText = stringResource(R.string.dialog_Confirm_ButtonTitle),
                            titleTextStyle = NonggleTheme.typography.b4_btn.copy(color = NonggleTheme.colorScheme.white),
                            onClick = onConfirm
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 28.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    NonggleDialog(
        onDismiss = {},
        onConfirm = {},
        dialogTitle = "제목",
        dialogContent = {
            Text(text = "다이얼로그 내용")
        }
    )
}