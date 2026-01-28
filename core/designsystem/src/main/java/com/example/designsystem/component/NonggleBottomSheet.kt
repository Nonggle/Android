package com.example.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(confirmValueChange = {false}),
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
                        .size(24.dp)
                        .padding(end = 20.dp, top = 20.dp, bottom = 8.dp)
                        .clickable { onDismissRequest() },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null
                )
                content()
            }
        }
    )
}