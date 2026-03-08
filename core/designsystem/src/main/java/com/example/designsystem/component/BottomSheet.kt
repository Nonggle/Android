package com.example.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
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
import com.example.core.designsystem.theme.NonggleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleBottomSheet(
    height: Float,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        dragHandle = null,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        containerColor = NonggleTheme.colorScheme.white,
        contentColor = NonggleTheme.colorScheme.black,
        contentWindowInsets = { WindowInsets(top = 0) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(height)
                    .padding(horizontal = 20.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 8.dp)
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
                Text(
                    text = title,
                    style = NonggleTheme.typography.t3.copy(color = NonggleTheme.colorScheme.black)
                )
                // --- Scroll Area ---
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(top = 12.dp)
                ) {
                    content()
                }
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
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetValue ->
            // SheetValue.Hidden으로 바뀌는 것(닫히는 것)을 거부
            sheetValue != SheetValue.Hidden
        }
    )

    Box(Modifier.fillMaxSize()) {
        Button(onClick = { showBottomSheet = true }) {
            Text("바텀시트 열기")
        }

        if (showBottomSheet) {
            NonggleBottomSheet(
                height = 0.7f,
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                title = "제목",
            ) {
                Column(Modifier.padding(16.dp)) {
                    repeat(30) { Text("item $it") } // 스크롤 확인용
                    Button(onClick = { showBottomSheet = false }) {
                        Text("닫기 버튼")
                    }
                }
            }
        }
    }
}
