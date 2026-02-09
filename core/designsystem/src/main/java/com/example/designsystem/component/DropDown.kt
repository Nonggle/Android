package com.example.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.core.designsystem.R
import com.example.core.designsystem.theme.NonggleTheme

// 드롭다운

class ExposedDropMenuStateHolder {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    fun getIcon(): Int = if (enabled) {
        R.drawable.caretup
    } else {
        R.drawable.caretdown
    }

    val items = (1..31).map { "${it}일" }
    fun onEnabled(newValue: Boolean) {
        enabled = newValue
    }

    fun onSelectedIndex(newValue: Int) {
        selectedIndex = newValue
        value = items[selectedIndex]
    }

    fun onSize(newValue: Size) {
        size = newValue
    }
}

@Composable
fun rememberExposedMenuStateHolder() = remember {
    ExposedDropMenuStateHolder()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleDropDown(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    selectValue: () -> Unit = {},
    icon: Painter = painterResource(R.drawable.caretdown),
    title: String = "",
    titleColor: Color = NonggleTheme.colorScheme.g3,
    stateHolder: ExposedDropMenuStateHolder
) {
    ExposedDropdownMenuBox(
        expanded = stateHolder.enabled,
        onExpandedChange = {stateHolder.onEnabled(!stateHolder.enabled)},
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(
                    BorderStroke(1.dp, NonggleTheme.colorScheme.g_line),
                    shape = RoundedCornerShape(4.dp)
                )
                .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) }
                .clickable(
                    onClick = onClick
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = NonggleTheme.typography.b4_btn,
                    textAlign = TextAlign.Start,
                    color = titleColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = icon,
                    contentDescription = null,
                )
            }
        }
        ExposedDropdownMenu (
            modifier = Modifier.width(with(LocalDensity.current) {stateHolder.size.width.toDp()}),
            expanded = stateHolder.enabled,
            onDismissRequest = {
                stateHolder.onEnabled(false)
            },
        ) {
            stateHolder.items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = { Text(text = s) },
                    colors = MenuItemColors(
                        NonggleTheme.colorScheme.g3,
                        NonggleTheme.colorScheme.g3,
                        NonggleTheme.colorScheme.g3,
                        NonggleTheme.colorScheme.g1,
                        NonggleTheme.colorScheme.g1,
                        disabledTrailingIconColor = NonggleTheme.colorScheme.g1,
                    ),
                    onClick = {
                        stateHolder.onSelectedIndex(index)
                        selectValue()
                        stateHolder.onEnabled(false)
                    }
                )
            }
        }
    }
}