package edu.student.groom.onboarding.signup.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.onboarding.signup.model.SingUpRepo
import edu.student.groom.util.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
class SignupViewModelTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()


    private lateinit var signupViewModel: SignupViewModel


    private val signUpRepo: SingUpRepo = mockk()
    private val observer:Observer<UiState<String>> = mockk()


    @BeforeEach
    fun setUp() {
        val list = emptyList<Responses.Institute>()

        val instituteResponse: Responses.InstituteResponse = Responses.InstituteResponse(list)
        coEvery {
            signUpRepo.getInstitutes()
        } returns instituteResponse
        signupViewModel = SignupViewModel(signUpRepo)
      //  signupViewModel.singUpResponse.observeForever(observer)

        Dispatchers.setMain(dispatcher)
        every { observer.onChanged(any()) } answers {}

    }


    @Test
    fun `test Signup Failure`() {

        val signUpRequest = Requests.SignUpRequest("", "", "", 234, "", "");

        coEvery {
            signUpRepo.signUp(signUpRequest)
        } returns Responses.BaseResponse("400","Error")
        signupViewModel.signUp(signUpRequest)
        coVerify {
            signUpRepo.signUp(signUpRequest)
        }
        Assert.assertEquals(UiState.Error("Error"), signupViewModel.singUpResponse.value)


    }

    @Test
    fun `test Signup Success`() {

        val signUpRequest = Requests.SignUpRequest("", "", "", 234, "", "");

        coEvery {
            signUpRepo.signUp(signUpRequest)
        } returns Responses.BaseResponse("200","Success")
        signupViewModel.signUp(signUpRequest)
        coVerify {
            signUpRepo.signUp(signUpRequest)
        }
        Assert.assertEquals(UiState.Success("Success"), signupViewModel.singUpResponse.value)


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}