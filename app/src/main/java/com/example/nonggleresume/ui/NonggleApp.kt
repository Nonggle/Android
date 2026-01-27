package com.example.nonggleresume.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.api.LoginEntryProvider
import com.example.api.LoginNavKey
import com.example.designsystem.component.NonggleMobileNavigationScaffold
import com.example.designsystem.component.NonggleNavigationBarItem
import com.example.download.navigation.downLoadEntryProvider
import com.example.home.navigation.homeEntryProvider
import com.example.navigation.Navigator
import com.example.navigation.toEntries
import com.example.nonggleresume.navigation.TOP_LEVEL_NAV_ITEMS
import com.example.setting.navigation.settingEntryProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NonggleApp(
    appState: NonggleAppState,
    modifier: Modifier = Modifier,
) {
    val isOfflne by appState.isOffline.collectAsStateWithLifecycle()
    val graphState by appState.graphState.collectAsStateWithLifecycle()

    LaunchedEffect(isOfflne) {
        if(isOfflne) Log.d("NOTCONNECT", "네트워크 미연결")
    }

    when(graphState) {
        RootGraph.Login -> {
            //val entryProvider = LoginEntryProvider(onLoginSuccess = { appState.onLoginSuccess() })

            NavDisplay(
                entries = entryProvider,
                onBack = {}
            )
        }

        RootGraph.Main -> {
            val mainNavigator = remember { Navigator(appState.mainNavigationState) }
            NonggleMobileNavigationScaffold(
                navigationBarItems = {
                    TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                        val selected = navKey == appState.mainNavigationState.currentTopLevelKey
                        NonggleNavigationBarItem(
                            selected = selected,
                            onClick = { mainNavigator.navigate(navKey) },
                            icon = { Icon(painterResource(navItem.unselectedIconRes), null) },
                            selectedIcon = { Icon(painterResource(navItem.selectedIconRes), null) },
                            label = { Text(navItem.title()) },
                        )
                    }
                }
            ) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                ) { padding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .consumeWindowInsets(padding) // 시스템 UI에 의해 가려지는 영역 이미 처리했음을 하위 컴포저블에 알리는 수정자
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                    ) {
                        var shouldShowTopAppBar = false

                        if (appState.mainNavigationState.currentKey in appState.mainNavigationState.topLevelKeys) {
                            shouldShowTopAppBar = true

                            val destination = TOP_LEVEL_NAV_ITEMS[appState.mainNavigationState.currentTopLevelKey] ?: error("Top level nav item not found for ${appState.mainNavigationState.currentTopLevelKey}")

                            TopAppBar(
                                title = { Text(destination.title()) }
                            )
                        }

                        Box(
                            modifier = Modifier.consumeWindowInsets(
                                if (shouldShowTopAppBar) {
                                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                                } else {
                                    WindowInsets(0, 0, 0, 0)
                                }
                            )
                        ) {

                            val entryProvider = entryProvider {
                                homeEntryProvider(mainNavigator)
                                downLoadEntryProvider(mainNavigator)
                                settingEntryProvider(mainNavigator)
                            }

                            NavDisplay(
                                entries = appState.mainNavigationState.toEntries(entryProvider),
                                onBack = { mainNavigator.goBack() }
                            )
                        }
                    }
                }
            }
        }
    }
}