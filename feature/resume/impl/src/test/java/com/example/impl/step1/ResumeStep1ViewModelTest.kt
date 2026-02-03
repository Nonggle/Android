package com.example.impl.step1

import android.net.Uri
import app.cash.turbine.test
import com.example.feature.resume.impl.step1.ResumeStep1Event
import com.example.feature.resume.impl.step1.ResumeStep1ViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResumeStep1ViewModelTest {

    private lateinit var viewModel: ResumeStep1ViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ResumeStep1ViewModel()
    }

    @Test
    fun `userNameChanged 이벤트를 보내면 state의 userName이 변경된다`() = runTest {
        val newUserName = "농글이"

        viewModel.onEvent(ResumeStep1Event.UserNameChanged(newUserName))

        viewModel.state.test {
            val latestState = awaitItem()
            assertThat(latestState.info.userName).isEqualTo(newUserName)
        }
    }

    @Test
    fun `addCertification 이벤트 발생 시 certification이 추가되고 input은 초기화된다`() = runTest {
        val newCertification = "정보처리기사"
        viewModel.onEvent(ResumeStep1Event.CertificationChanged(newCertification))

        viewModel.onEvent(ResumeStep1Event.AddCertification)

        viewModel.state.test {
            val latestState = awaitItem()
            assertThat(latestState.info.certificationList).hasSize(1)
            assertThat(latestState.info.certificationList.first().certificationTitle).isEqualTo(newCertification)
            assertThat(latestState.certificationInput).isEmpty()
        }
    }

    @Test
    fun `removeCertificationChip 이벤트를 보내면 해당하는 자격증이 삭제된다`() = runTest {
        // Given: 2개의 자격증 추가
        viewModel.onEvent(ResumeStep1Event.CertificationChanged("정보처리기사"))
        viewModel.onEvent(ResumeStep1Event.AddCertification)
        viewModel.onEvent(ResumeStep1Event.CertificationChanged("SQLD"))
        viewModel.onEvent(ResumeStep1Event.AddCertification)

        val currentState = viewModel.state.value
        val itemToRemove = currentState.info.certificationList.first()

        // When
        viewModel.onEvent(ResumeStep1Event.RemoveCertificationChip(itemToRemove.id))

        // Then
        viewModel.state.test {
            val latestState = awaitItem()
            assertThat(latestState.info.certificationList).hasSize(1)
            assertThat(latestState.info.certificationList.find { it.id == itemToRemove.id }).isNull()
        }
    }

    @Test
    fun `selectUserProfileImage 이벤트를 보내면 프로필 이미지가 변경된다`() = runTest {
        val mockUri = mockk<Uri>()

        viewModel.onEvent(ResumeStep1Event.SelectImage(mockUri))

        viewModel.state.test {
            val latestState = awaitItem()
            assertThat(latestState.info.profileImageUrl).isEqualTo(mockUri)
        }
    }

    @Test
    fun `removeProfileImage 이벤트를 보내면 프로필 이미지가 null로 변경된다`() = runTest {
        // Given: 이미지 설정
        val mockUri = mockk<Uri>()
        viewModel.onEvent(ResumeStep1Event.SelectImage(mockUri))

        // When
        viewModel.onEvent(ResumeStep1Event.RemoveProfileImage)

        // Then
        viewModel.state.test {
            val latestState = awaitItem()
            assertThat(latestState.info.profileImageUrl).isNull()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
