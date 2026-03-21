package com.nonggle.impl.step2

import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.model.ResumeWritingModel
import com.nonggle.resume.impl.step2.CareerBottomSheetEvent
import com.nonggle.resume.impl.step2.CareerFormData
import com.nonggle.resume.impl.step2.ResumeStep2Event
import com.nonggle.resume.impl.step2.ResumeStep2ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.Period

/**
 * ViewModel의 로직을 검증하기 위한 단위 테스트 클래스입니다.
 */
@ExperimentalCoroutinesApi
class ResumeStep2ViewModelTest {

    // 테스트할 ViewModel
    private lateinit var viewModel: ResumeStep2ViewModel

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

        // ViewModel에 가짜 저장소를 주입하여 생성
        viewModel = ResumeStep2ViewModel(fakeResumeStore)
    }

    @After
    fun tearDown() {
        // 테스트가 끝나면 Main Dispatcher를 원래대로 복원
        Dispatchers.resetMain()
    }

    @Test
    fun `addCareerItem - 새 경력을 추가하면, 상태와 임시저장소가 올바르게 업데이트되어야 한다`() = runTest {
        // GIVEN (준비)
        val newCareer = CareerFormData(
            id = "1",
            careerStartDate = LocalDate.of(2023, 1, 1),
            careerEndDate = LocalDate.of(2023, 12, 31),
            careerDescription = "Android Developer",
            careerDetail = "Nonggle App Development"
        )

        // WHEN (실행)
        viewModel.setEvent(ResumeStep2Event.CareerSheetEvent(CareerBottomSheetEvent.AddCareerItem(newCareer)))

        // THEN (검증)
        val currentState = viewModel.uiState.value
        val storedDraft = fakeResumeStore.draft.value

        // 1. ViewModel의 상태 검증
        assertEquals(1, currentState.careerList.size)
        assertEquals("Android Developer", currentState.careerList.first().careerDescription)
        assertEquals(CareerFormData(), currentState.careerFormData) // 입력 폼은 초기화되었는가?

        // 2. 총 경력 기간 계산 검증 (11개월 30일)
        val expectedPeriod = Period.between(newCareer.careerStartDate, newCareer.careerEndDate)
        assertEquals(expectedPeriod, currentState.totalCareer)

        // 3. 임시 저장소(DraftStore)의 상태 검증
        assertEquals(1, storedDraft.careerList.size)
        assertEquals("Android Developer", storedDraft.careerList.first().careerDescription)
        assertEquals(expectedPeriod, storedDraft.totalCareer)
    }

    @Test
    fun `deleteCareerItem - 기존 경력을 삭제하면, 상태와 임시저장소가 올바르게 업데이트되어야 한다`() = runTest {
        // GIVEN (준비): 2개의 경력이 미리 추가된 상태
        val career1 = CareerFormData("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31), "Career 1", "...")
        val career2 = CareerFormData("2", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), "Career 2", "...")
        viewModel.setEvent(ResumeStep2Event.CareerSheetEvent(CareerBottomSheetEvent.AddCareerItem(career1)))
        viewModel.setEvent(ResumeStep2Event.CareerSheetEvent(CareerBottomSheetEvent.AddCareerItem(career2)))

        // WHEN (실행): ID가 "1"인 경력을 삭제
        viewModel.setEvent(ResumeStep2Event.DeleteCareerItem("1"))

        // THEN (검증)
        val currentState = viewModel.uiState.value
        val storedDraft = fakeResumeStore.draft.value

        // 1. ViewModel의 상태 검증
        assertEquals(1, currentState.careerList.size)
        assertEquals("Career 2", currentState.careerList.first().careerDescription) // career2만 남았는가?

        // 2. 총 경력 기간 계산 검증 (career2의 기간만 남아야 함)
        val expectedPeriod = Period.between(career2.careerStartDate, career2.careerEndDate)
        assertEquals(expectedPeriod, currentState.totalCareer)

        // 3. 임시 저장소(DraftStore)의 상태 검증
        assertEquals(1, storedDraft.careerList.size)
        assertEquals("Career 2", storedDraft.careerList.first().careerDescription)
        assertEquals(expectedPeriod, storedDraft.totalCareer)
    }
}

/**
 * 테스트를 위한 임의의 ResumeDraftStore 구현체.
 * 실제 저장소 대신 메모리 상에서 상태를 관리
 */
class FakeResumeDraftStore : ResumeDraftStoreInterface {

    private val _draft = MutableStateFlow(ResumeWritingModel())
    val draft: StateFlow<ResumeWritingModel> = _draft

    override fun update(reducer: (ResumeWritingModel) -> ResumeWritingModel) {
        _draft.update(reducer)
    }

    override fun snapshot(): ResumeWritingModel = _draft.value

}