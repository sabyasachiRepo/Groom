package edu.student.groom.onboarding.signup.model

import edu.student.groom.onboarding.signup.model.service.SingUpService
import edu.student.groom.onboarding.signup.ui.InstantExecutorExtension
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
class SingUpRepoTest{

    private val singUpService: SingUpService = mockk(relaxed = true)

    private val singUpRepo= SingUpRepo(singUpService)

    @Test
    fun `test getInstitutes calling SingUpService`(){
        runTest {
            singUpRepo.getInstitutes()
            coVerify {singUpService.signupApi.getInstitutes()}
        }
    }
    @Test
    fun `test signup calling SingUpService`(){
        runTest {
            val signupRequests:Requests.SignUpRequest = mockk()
            singUpRepo.signUp(signupRequests)
            coVerify {singUpService.signupApi.signUp(signupRequests)}
        }
    }

}