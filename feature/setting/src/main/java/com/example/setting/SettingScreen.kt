package com.example.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun SettingScreen(onBackClick: () -> Unit) {
    SettingScreen()
}

@Composable
internal fun SettingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(100) {
                Text(text = "설정")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingScreenPreview() {

}