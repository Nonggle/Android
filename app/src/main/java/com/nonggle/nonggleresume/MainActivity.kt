package com.nonggle.nonggleresume

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.nonggle.designsystem.theme.NonggleTheme
import com.nonggle.navigation.rememberNavigationState
import com.nonggle.navigation.toEntries
import com.nonggle.api.HomeNavKey
import com.nonggle.api.LoginNavKey
import com.nonggle.data.util.NetworkMonitor
import com.nonggle.impl.navigation.LoginEntryProvider
import com.nonggle.nonggleresume.navigation.TOP_LEVEL_NAV_ITEMS
import com.nonggle.nonggleresume.ui.NonggleApp
import com.nonggle.nonggleresume.ui.rememberNonggleAppState
import com.nonggle.nonggleresume.util.isSystemInDarkTheme
import com.nonggle.nonggleresume.util.isSysyemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

//    @Inject
//    lateinit var analyticsHelper: AnalyticsHelper

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme
            )
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSysyemInDarkTheme(),
                    viewModel.uiState,
                ) { systemDark, uiState ->
                    ThemeSettings(darkTheme = uiState.shouldUseDarkTheme(systemDark))
                }
                    .onEach { themeSettings = it }
                    .map { it.darkTheme }
                    .distinctUntilChanged()
                    .collect { darkTheme ->
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                lightScrim = Color.TRANSPARENT,
                                darkScrim = Color.TRANSPARENT
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                lightScrim = lightScrim,
                                darkScrim = darkScrim
                            ) { darkTheme }
                        )
                    }
            }
        }

        setContent {
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            CompositionLocalProvider(
                // LocalAnalyticsHelper provides analyticsHelper, //여기에 firebase analytics를 연동할 수 있지 않을까
            ) {
                NonggleTheme(
                    darkTheme = themeSettings.darkTheme,
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                            .statusBarsPadding(),
                        color = NonggleTheme.colorScheme.white
                    ) {
                        if (isLoggedIn) {
                            val appState = rememberNonggleAppState(
                                networkMonitor = networkMonitor,
                                navigatorState = rememberNavigationState(
                                    HomeNavKey,
                                    TOP_LEVEL_NAV_ITEMS.keys
                                )
                            )
                            NonggleApp(appState)
                        } else {
                            val loginNavigationState = rememberNavigationState(
                                startKey = LoginNavKey,
                                topLevelKeys = setOf(LoginNavKey)
                            )

                            val entryProvider = entryProvider {
                                LoginEntryProvider(navigateToMain = viewModel::onLoginSuccess)
                            }

                            NavDisplay(
                                entries = loginNavigationState.toEntries(entryProvider),
                                onBack = { /* 로그인 화면에서는 보통 뒤로가기를 막습니다 */ }
                            )
                        }
                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

data class ThemeSettings(
    val darkTheme: Boolean,
)
