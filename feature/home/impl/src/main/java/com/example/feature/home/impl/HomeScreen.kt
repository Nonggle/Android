package com.example.feature.home.impl.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    navigateToResume: () -> Unit
) {
    Button(
        onClick = navigateToResume
    ) {
        Text("Resume")
    }
}