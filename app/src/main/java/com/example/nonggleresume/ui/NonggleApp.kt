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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.core.designsystem.component.NonggleMobileNavigationScaffold
import com.example.core.designsystem.component.NonggleNavigationBarItem
import com.example.core.navigation.Navigator
import com.example.core.navigation.toEntries
import com.example.feature.home.impl.navigation.homeEntryProvider
import com.example.feature.resume.impl.navigation.resumeEntryProvider
import com.example.feature.resume_view.impl.navigation.resumeViewEntryProvider
import com.example.nonggleresume.navigation.TOP_LEVEL_NAV_ITEMS
import com.example.setting.navigation.settingEntryProvider
import com.nonggle.feature.download.impl.navigation.downLoadEntryProvider

// 로그인 후에만 호출됨

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NonggleApp(
    appState: NonggleAppState,
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    LaunchedEffect(isOffline) {/// TODO: 네트워크 미연결시 다이얼로그 처리
        if (isOffline) Log.d("NOTCONNECT", "네트워크 미연결")
    }

    val mainNavigator = remember { Navigator(appState.mainNavigationState) }
    val shouldShowBottomBar = appState.mainNavigationState.currentKey in TOP_LEVEL_NAV_ITEMS.keys

    NonggleMobileNavigationScaffold(
        showBottomBar = shouldShowBottomBar,
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
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            Box(
                modifier = Modifier
            ) {
                val entryProvider = entryProvider {
                    homeEntryProvider(mainNavigator)
                    resumeEntryProvider(mainNavigator)
                    resumeViewEntryProvider(mainNavigator)
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
