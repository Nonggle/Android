package com.example.nonggleresume.util

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.core.util.Consumer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun ComponentActivity.isSysyemInDarkTheme() = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)
// 새 값을 받으면 바로 Flow로 흘려보낸다 non
    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }
// 이 리스너로 설정 변경에 대한 이벤트를 받는다
    addOnConfigurationChangedListener(listener)
    awaitClose {removeOnConfigurationChangedListener(listener)}
}
    .distinctUntilChanged()
    .conflate()
// 변화가 발생하면 컴포지션이 트리거 된다
// conflate로 인해 가장최근 값만 처리된다