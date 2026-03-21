package com.nonggle.nonggleresume

import com.nonggle.common.result.AuthEvent
import com.nonggle.common.result.DefaultAuthEventBus
import com.nonggle.domain.repository.LoginRepository
import com.nonggle.model.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var loginRepository: LoginRepository
    private lateinit var authEventBus: DefaultAuthEventBus

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginRepository = mockk(relaxed = true)
        authEventBus = DefaultAuthEventBus()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * SessionExpired 수신 → logOut() 호출 + _isLoggedIn = false (로그인 화면 이동)
     */
    @Test
    fun `SessionExpired 이벤트 수신시 logOut을 호출하고 isLoggedIn을 false로 변경한다`() = runTest {
        coEvery { loginRepository.isLoggedIn() } returns flowOf(true)
        coEvery { loginRepository.logOut() } returns AppResult.Success(Unit)

        val viewModel = MainActivityViewModel(
            authEventBus = authEventBus,
            loginRepository = loginRepository,
        )

        // init 블록의 코루틴들이 시작되도록 대기
        advanceUntilIdle()

        authEventBus.emit(AuthEvent.SessionExpired)

        // SessionExpired 처리 코루틴이 완료되도록 대기
        advanceUntilIdle()

        coVerify(exactly = 1) { loginRepository.logOut() }
        assertFalse(viewModel.isLoggedIn.value)
    }
}
