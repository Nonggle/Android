package com.example.impl.step1

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.impl.R

//@Composable
//internal fun ResumeStep1Screen() {
//
//}

@Composable
internal fun ResumeStep1Screen(

) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.resume1Screen_profile_image),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.resume1Screen_introduceTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.surfaceTint,
            )
            // 프로필 이미지
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_nameTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            // 농글 디자인시스템 텍스트 필드
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_birthDateTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            // box
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.resume1Screen_birthDateTitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

}