package com.example.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.NonggleImageButton
import com.example.designsystem.theme.soYo

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    //onShowLoginFailSnackBar: suspend (String) -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = modifier,
        loginState = uiState.loginState,
        onLoginSuccess = onLoginSuccess,
        kakaoLoginButtonClick = { viewModel.setEvent(LoginEvent.KakaoLoginButtonClick) }
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    loginState: LoginUiState = LoginUiState.Idle,
    //onShowLoginFailSnackBar: suspend (String) -> Unit,
    onLoginSuccess: () -> Unit,
    kakaoLoginButtonClick: () -> Unit,
) {
    val kakaoLoginFailMessage = stringResource(R.string.fail_kakao_login)

        LaunchedEffect(loginState) {
        if(loginState is LoginUiState.LoginSuccess) {
            onLoginSuccess()
        } else if(loginState is LoginUiState.LoginFail) {
            // 토스트 메시지 표시하기
            //onShowLoginFailSnackBar(kakaoLoginFailMessage)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(loginState) {
            LoginUiState.Idle, LoginUiState.LoginFail -> {
                Spacer(modifier = modifier.weight(1f))
                AppLogoForLogin()
                Spacer(modifier = modifier.weight(1f))
                kakaoLoginButton(
                    modifier = Modifier.padding(bottom = 30.dp),
                    onClick = kakaoLoginButtonClick,
                )
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun AppLogoForLogin(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        text = stringResource(R.string.app_name),
        color = MaterialTheme.colorScheme.primary,
        style = TextStyle(
            fontFamily = soYo,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        textAlign = TextAlign.Center
    )
}


@Composable
fun kakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    NonggleImageButton(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = stringResource(R.string.start_with_kakao),
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        backgroundColor = Color(0xFFF9E000),
        titleTextStyle = MaterialTheme.typography.labelLarge,
        imageResource = R.drawable.kakaobtn
    )
}