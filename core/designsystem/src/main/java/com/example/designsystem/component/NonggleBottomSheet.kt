package com.example.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        content = {
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 8.dp, top = 10.dp, bottom = 8.dp)
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { onDismissRequest() },
                        alignment = Alignment.TopEnd,
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null
                    )
                }
                content()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NoggleBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { false } // 사용자가 아래로 스와이프하여 닫는 것을 허용하지 않음
    )

    Button(onClick = { showBottomSheet = true }) {
        Text("바텀시트 열기")
    }

    if (showBottomSheet) {
        NonggleBottomSheet(
            onDismissRequest = { showBottomSheet = false }, // 배경 클릭 또는 뒤로가기 시 닫기
            sheetState = sheetState,
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("이것이 바텀시트의 내용입니다.")
                    Button(onClick = { showBottomSheet = false }) {
                        Text("닫기 버튼")
                    }
                }
            }
        )
    }
}