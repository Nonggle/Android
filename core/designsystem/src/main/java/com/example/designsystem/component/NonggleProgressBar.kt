package com.example.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.theme.NonggleTheme

@Composable
fun NonggleCircularProgresssBar(
    percentage: Float,
    radius: Dp = 50.dp,
    color: Color = NonggleTheme.colorScheme.m1,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    // 진행도 애니메이션 설정
    val animatePercentage by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier.size(radius)
        ) {
            // 배경 원 그리기
            drawCircle(
                color = Color.LightGray,
                radius = radius.toPx() / 2,
                style = Stroke(width = strokeWidth.toPx())
            )

            //프로그래스 호 그리기
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * animatePercentage,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
fun ProgressbarPreview() {
    NonggleCircularProgresssBar(
        percentage = 0.5f,
        radius = 50.dp,
    )
}