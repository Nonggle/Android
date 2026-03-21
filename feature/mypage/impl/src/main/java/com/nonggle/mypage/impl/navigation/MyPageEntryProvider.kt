package com.nonggle.mypage.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.nonggle.mypage.api.MyPageNavKey
import com.nonggle.mypage.impl.MyPageScreen
import com.nonggle.navigation.Navigator

fun EntryProviderScope<NavKey>.myPageEntryProvider(navigator: Navigator) {
    entry<MyPageNavKey> {
        MyPageScreen()
    }
}