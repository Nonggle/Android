package com.example.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.ImageButton
import com.example.designsystem.theme.NonggleTheme
import com.example.designsystem.theme.soYo

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    //onShowLoginFailSnackBar: suspend (String) -> Unit,
    navigateToMain: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = modifier,
        loginState = uiState.loginState,
        navigateToMain = navigateToMain,
        kakaoLoginButtonClick = { viewModel.setEvent(LoginEvent.KakaoLoginButtonClick) }
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    loginState: LoginUiState = LoginUiState.Idle,
    //onShowLoginFailSnackBar: suspend (String) -> Unit,
    navigateToMain: () -> Unit,
    kakaoLoginButtonClick: () -> Unit,
) {
    val kakaoLoginFailMessage = stringResource(R.string.fail_kakao_login)

        LaunchedEffect(loginState) {
        if(loginState is LoginUiState.LoginSuccess) {
            navigateToMain()
        } else if(loginState is LoginUiState.LoginFail) {
            // 토스트 메시지 표시하기
            //onShowLoginFailSnackBar(kakaoLoginFailMessage)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(loginState) {
            LoginUiState.Idle, LoginUiState.LoginFail -> {
                Spacer(modifier = Modifier.weight(1f))
                AppLogoForLogin()
                Spacer(modifier = Modifier.weight(1f))
                KakaoLoginButton(
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
        color = NonggleTheme.colorScheme.m1,
        style = TextStyle(
            fontFamily = soYo,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        textAlign = TextAlign.Center
    )
}


@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ImageButton(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        onClick = onClick,
        titleText = stringResource(R.string.start_with_kakao),
        contentColor = NonggleTheme.colorScheme.g1,
        backgroundColor = Color(0xFFF9E000),
        titleTextStyle = NonggleTheme.typography.b4_btn,
        imageResource = R.drawable.kakaobtn
    )
}