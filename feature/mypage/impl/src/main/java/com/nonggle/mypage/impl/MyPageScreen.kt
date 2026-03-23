package com.nonggle.mypage.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nonggle.designsystem.component.FullButton
import com.nonggle.designsystem.component.NonggleDialog
import com.nonggle.designsystem.component.NonggleMainTopAppBar
import com.nonggle.designsystem.theme.NonggleTheme

@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    uiState: MyPageState,
    onEvent: (MyPageEvent) -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    if(showLogoutDialog) {
        logoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                onEvent(MyPageEvent.LogoutClicked)
            }
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NonggleMainTopAppBar(appBarTitle = stringResource(R.string.MyPageScreen_MainTitle))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            FullButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                onClick = { showLogoutDialog = true },
                title = stringResource(R.string.MyPageScreen_LogoutButton)
            )
        }
    }
}

@Composable
fun logoutDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    NonggleDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        dialogTitle = stringResource(R.string.Logout_DialogTitle),
        dialogContent = {
            Text(
                text = stringResource(R.string.Logout_DialogSubTitle),
                style = NonggleTheme.typography.b3_small.copy(color = NonggleTheme.colorScheme.g2)
            )
        }
    )
}
