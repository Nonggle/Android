package com.example.impl.step1

import com.example.feature.resume.impl.step1.ResumeStep1Event
import com.example.feature.resume.impl.step1.ResumeStep1ViewModel
import com.example.feature.resume.impl.step2.FakeResumeDraftStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResumeStep1ViewModelTest {

    // 테스트할 ViewModel
    private lateinit var viewModel: ResumeStep1ViewModel

    // 가짜 저장소 (Fake)
    private lateinit var fakeResumeStore: FakeResumeDraftStore

    // 코루틴 테스트를 위한 Dispatcher
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Main Dispatcher를 테스트용 Dispatcher로 교체
        Dispatchers.setMain(testDispatcher)

        // 의존성인 가짜 저장소를 생성
        fakeResumeStore = FakeResumeDraftStore()

        viewModel = ResumeStep1ViewModel(fakeResumeStore)
    }


    @Test
    fun `userNameChanged 이벤트를 보내면 state의 userName이 변경된다`() = runTest {
        // GIVEN
        val newUserName = "농글이"

        // WHEN
        viewModel.setEvent(ResumeStep1Event.UserNameChanged(newUserName))

        // viewModelScope에서 실행되도록 예약된 상태 업데이트 작업을 즉시 실행
        advanceUntilIdle()

        // THEN
        // 행동(WHEN)이 끝난 후, 최신 상태를 가져와서 검증합니다.
        val currentState = viewModel.uiState.value
        assertEquals(newUserName, currentState.info.userName)
    }

    @Test
    fun `addCertification 이벤트 발생 시 certification이 추가되고 input은 초기화된다`() = runTest {
        val newCertification = "정보처리기사"
        // WHEN (실행): 새로운 자격증 항목 추가
        viewModel.setEvent(ResumeStep1Event.CertificationChanged(newCertification))
        advanceUntilIdle()
        viewModel.setEvent(ResumeStep1Event.AddCertification)
        advanceUntilIdle()

        // THEN (검증)
        val currentState = viewModel.uiState.value

        // 1. ViewModel의 상태 검증
        assertEquals(1, currentState.info.certificationList.size)
        assertEquals(
            newCertification,
            currentState.info.certificationList.first().certificationTitle
        )
    }

    @Test
    fun `removeCertificationChip 이벤트를 보내면 해당하는 자격증이 삭제된다`() = runTest {
        // Given: 2개의 자격증 추가
        viewModel.setEvent(ResumeStep1Event.CertificationChanged("정보처리기사"))
        viewModel.setEvent(ResumeStep1Event.AddCertification)
        viewModel.setEvent(ResumeStep1Event.CertificationChanged("SQLD"))
        viewModel.setEvent(ResumeStep1Event.AddCertification)

        advanceUntilIdle()

        // When(실행): 첫번째 자격증 항목 삭제
        viewModel.setEvent(
            ResumeStep1Event.RemoveCertificationChip(
                viewModel.uiState.value.info.certificationList[0].id
            )
        )

        advanceUntilIdle()

        // THEN (검증)
        val currentState = viewModel.uiState.value

        // 1. ViewModel의 상태 검증
        assertEquals(1, currentState.info.certificationList.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
