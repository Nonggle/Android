package com.example.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
                Image(
                    modifier = Modifier
                        .padding(end = 20.dp, top = 20.dp, bottom = 8.dp)
                        .size(24.dp)
                        .clickable { onDismissRequest() },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null
                )
                content()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NoggleBottomSheetPreview() {
    // --- 이렇게 사용합니다 ---
    // 1. 바텀시트의 표시 여부를 제어하는 상태
    var showBottomSheet by remember { mutableStateOf(true) }

    // 2. 바텀시트의 세부 상태(열림, 닫힘 등 애니메이션)를 제어하는 상태
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { false } // 사용자가 아래로 스와이프하여 닫는 것을 허용하지 않음
    )

    // 바텀시트를 보여주는 버튼 (실제 화면에서는 이 버튼을 누르면 showBottomSheet가 true가 됨)
    Button(onClick = { showBottomSheet = true }) {
        Text("바텀시트 열기")
    }

    // 3. showBottomSheet가 true일 때만 NonggleBottomSheet를 렌더링
    if (showBottomSheet) {
        NonggleBottomSheet(
            onDismissRequest = { showBottomSheet = false }, // 배경 클릭 또는 뒤로가기 시 닫기
            sheetState = sheetState,
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("이것이 바텀시트의 내용입니다.")
                    // 닫기 버튼을 누르면 상태를 변경하여 바텀시트를 숨김
                    Button(onClick = { showBottomSheet = false }) {
                        Text("닫기 버튼")
                    }
                }
            }
        )
    }
}