package com.example.impl.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.OutlinedButton
import com.example.designsystem.theme.NonggleTheme
import com.example.impl.utils.Gender
import com.example.impl.R

@Composable
fun genderSelectButton(
    modifier: Modifier = Modifier,
    text: String,
    selectGender: (Gender) -> Unit,
    userGender: Gender,
    isSelect: Boolean, // 현재 해당 버튼을 통해 성별 유형을 정했는지 여부
) {
    OutlinedButton(
        modifier = modifier,
        titleText = text,
        onClick = { selectGender(userGender) },
        titleTextStyle = NonggleTheme.typography.b1_main,
        enableColor = if (isSelect) NonggleTheme.colorScheme.m1 else NonggleTheme.colorScheme.g3,
        enableContentColor = if (isSelect) NonggleTheme.colorScheme.m1 else Color(0xFFB0B0B0),
        isSelect = isSelect,
        selectColor = NonggleTheme.colorScheme.m1,
        pressedColor = NonggleTheme.colorScheme.m1
    )
}


@Composable
fun certificationChipItem(
    modifier: Modifier = Modifier,
    title: String,
    removeChip: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
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
                    .padding(start = 8.dp),
                painter = painterResource(R.drawable.xcircle),
                contentDescription = null,
            )
        }
    }
}
