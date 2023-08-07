package edu.student.groom.onboarding.signup.model

import edu.student.groom.onboarding.signup.model.service.SingUpService
import edu.student.groom.onboarding.signup.ui.InstantExecutorExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
class SingUpRepoTest{
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()


    private val singUpService: SingUpService = mockk()

    private val singUpRepo= SingUpRepo(singUpService)

    @Test
    fun `test getInstitutes calling SingUpService`(){

        verify {singUpService.signupApi  }
    }

}