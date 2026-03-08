package com.nonggle.feature.resume_view.impl

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.example.core.designsystem.theme.NonggleTheme
import com.nonggle.feature.resume_view.impl.navigation.ScreenMode


@Composable
fun userProfile(
    context: Context,
    modifier: Modifier,
    screenMode: ScreenMode,
    profileImageUrl: String
) {
    AsyncImage(
        modifier = modifier
            .size(80.dp)
            .clip(RoundedCornerShape(99.dp)),
        model = ImageRequest.Builder(context)
            .data(profileImageUrl)
            .allowHardware(screenMode == ScreenMode.SCREEN)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.user),
        placeholder = painterResource(R.drawable.user),
    )
}

@Composable
fun titleDot(
    color: Color = NonggleTheme.colorScheme.m1,
) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .background(color = color, shape = CircleShape)
    )
}

@Composable
fun typeCard(title: String) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = NonggleTheme.colorScheme.m5),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = title,
            style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.m1)
        )
    }
}

@Composable
fun careerCard(
    modifier: Modifier = Modifier,
    careerTitle: String,
    careerPeriod: String, // 작업 진행 날짜 ex. 시작날짜 ~ 종료 날자
    careerPeriodTotal: String, // 작업 진행 총 기간
    careerExplanation: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .border(
                BorderStroke(1.dp, NonggleTheme.colorScheme.g_line_light),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                titleDot()
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = careerTitle,
                    style = NonggleTheme.typography.b4_btn
                )
                Spacer(modifier = Modifier.weight(1f))
                typeCard(careerPeriodTotal)
            }
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = careerPeriod,
                style = NonggleTheme.typography.b2_sub.copy(color = NonggleTheme.colorScheme.g2)
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = careerExplanation,
                style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NonggleCareerCardPreview() {
    NonggleTheme {
        careerCard(
            careerTitle = "경력 제목",
            careerPeriod = "2013.10 ~ 2014.10",
            careerPeriodTotal = "1년",
            careerExplanation = "상세 설명"
        )
    }
}