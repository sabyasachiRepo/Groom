package edu.student.groom.data

import edu.student.groom.onboarding.signup.ui.InstantExecutorExtension
import edu.student.groom.util.AccessTokenManager
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class)
class DefaultUserDataRepoTest {

    private val accessTokenManager: AccessTokenManager = mockk(relaxed = true)

    private val defaultLoginRepo = DefaultUserDataRepo(accessTokenManager)

    @Test
    fun `test getUserToken calling accessToken manager`() {
        runTest {
            defaultLoginRepo.getUserToken();
            coVerify { accessTokenManager.getAccessToken() }
        }
    }

    @Test
    fun `test saveUserToken calling accessToken manager`() {
        runTest {
            defaultLoginRepo.saveUserToken("");
            coVerify { accessTokenManager.saveAccessToken("") }
        }
    }
    @Test
    fun `test removeUserToken calling accessToken manager`() {
        runTest {
            defaultLoginRepo.removeUserToken();
            coVerify { accessTokenManager.clearAccessToken() }
        }
    }
}