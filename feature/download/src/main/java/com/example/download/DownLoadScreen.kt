package com.example.download

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun DownloadScreen(onBackClicked: () -> Unit) {
    DownloadScreen()
}

@Composable
internal fun DownloadScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(100) {
                Text(text = "다운로드 화면")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DownloadPreviewScreen() {
}
