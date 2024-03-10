package edu.student.groom.data

import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.service.LoginService
import edu.student.groom.onboarding.signup.model.SingUpRepo
import edu.student.groom.onboarding.signup.model.service.SingUpService
import edu.student.groom.onboarding.signup.ui.InstantExecutorExtension
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class)
class DefaultLoginRepoTest {


    private val loginService: LoginService = mockk(relaxed = true)

    private val defaultLoginRepo= DefaultLoginRepo(loginService)


    @Test
    fun `test logIn() method calling login service`() {
        runTest {
            val loginRequests= LoginRequest("","")
            defaultLoginRepo.logIn(loginRequests)
            coVerify { loginService.loginApi.login(loginRequest = loginRequests) }
        }

    }
}