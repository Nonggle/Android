package com.example.nonggleresume

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.util.NetworkMonitor
import com.example.designsystem.theme.NonggleTheme
import com.example.nonggleresume.ui.NonggleApp
import com.example.nonggleresume.ui.rememberNonggleAppState
import com.example.nonggleresume.util.isSystemInDarkTheme
import com.example.nonggleresume.util.isSysyemInDarkTheme
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
            val appState = rememberNonggleAppState(networkMonitor = networkMonitor)

            CompositionLocalProvider(
                //LocalAnalyticsHelper provides analyticsHelper, //여기에 firebase analytics를 연동할 수 있지 않을까
            ) {
                NonggleTheme(
                    darkTheme = themeSettings.darkTheme,
                ) {
                    NonggleApp(appState)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
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