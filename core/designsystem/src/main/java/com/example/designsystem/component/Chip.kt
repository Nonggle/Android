package com.example.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R
import com.example.core.designsystem.component.NonggleIconButton
import com.example.core.designsystem.theme.NonggleTheme

@Composable
fun NonggleChip(
    modifier: Modifier = Modifier,
    title: String,
    removeChip: () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = NonggleTheme.colorScheme.g4,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 11.dp)
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = NonggleTheme.typography.b2_sub,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        NonggleIconButton(
            image = painterResource(R.drawable.xcircle),
            onClick = removeChip
        )
    }
}