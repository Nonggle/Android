package com.example.core.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import com.example.core.designsystem.theme.NonggleTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    modifier: Modifier = Modifier,
    state: PickerState = rememberPickerState(),
    items: List<String>,
    startIndex: Int = 0,
    unit: String,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int): String = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = ScrollableDefaults.flingBehavior()

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val itemHeightDp = remember(itemHeightPixels.value) {
        with(density) { itemHeightPixels.value.toDp() }
    }
    val dividerColor = NonggleTheme.colorScheme.white
    val fadingEdgeGradient = remember(dividerColor) {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to dividerColor,
            1f to Color.Transparent

        )
    }

    // items, startIndex가 바뀌면 시작 위치를 다시 바꿔줌 년도에 따라 월/일 리스트가 동적으로 바뀌기 때문
    LaunchedEffect(items.size, startIndex) {
        val newStart =
            listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex
        listState.scrollToItem(newStart)
    }

    // 스크롤 위치가 변할때 선택 값 갱신
    LaunchedEffect(listState, items) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { firstIndex ->
                val centerIndex = firstIndex + visibleItemsMiddle
                val realIndex = ((centerIndex % items.size) + items.size) % items.size
                realIndex to items[realIndex]
            }
            .distinctUntilChanged()
            .collect { (realIndex, value) ->
                state.selectedIndex = realIndex
                state.selectedItem = value
            }
    }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient),
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listScrollCount) { index ->
                Text(
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .then(textModifier),
                    text = "${getItem(index)} $unit",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colorScheme.black),
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle),
            color = NonggleTheme.colorScheme.m1,
        )
        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1)),
            color = NonggleTheme.colorScheme.m1,
        )
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedIndex by mutableIntStateOf(0)
    var selectedItem by mutableStateOf("")
}