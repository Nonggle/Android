package com.example.nonggleresume.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.core.data.util.NetworkMonitor
import com.example.core.navigation.NavigationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
/// Login 판단 여부 모두 MainActivityViewModel로 이전
@Composable
fun rememberNonggleAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigatorState: NavigationState
): NonggleAppState {

    return remember(
        navigatorState,
        coroutineScope,
        networkMonitor,
    ) {
        NonggleAppState(
            mainNavigationState = navigatorState,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
        )
    }
}

@Stable
class NonggleAppState(
    val mainNavigationState: NavigationState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

}
