package com.example.nonggleresume.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.core.data.util.NetworkMonitor
import com.example.core.navigation.NavigationState
import com.example.core.navigation.rememberNavigationState
import com.example.feature.home.api.HomeNavKey
import com.example.nonggleresume.navigation.RootNavKey
import com.example.nonggleresume.navigation.TOP_LEVEL_NAV_ITEMS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberNonggleAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    isLogin: Boolean = false,
): NonggleAppState {
    val mainNavigationState = rememberNavigationState(HomeNavKey, TOP_LEVEL_NAV_ITEMS.keys)

    return remember(
        mainNavigationState,
        coroutineScope,
        networkMonitor,
        isLogin,
    ) {
        NonggleAppState(
            mainNavigationState = mainNavigationState,
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
    private val _isLogin = MutableStateFlow(true)

    val rootNavState: StateFlow<RootNavKey> =
        _isLogin.map { login ->
            if (login) RootNavKey.MainNavKey else RootNavKey.LoginNavKey
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = RootNavKey.LoginNavKey
        )

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun logout() {
        _isLogin.value = false
    }

    fun goMain() {
        _isLogin.value = true
    }
}
