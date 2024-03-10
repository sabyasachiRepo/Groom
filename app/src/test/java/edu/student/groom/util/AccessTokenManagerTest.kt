package edu.student.groom.util

import edu.student.groom.data.GroomLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AccessTokenManagerTest {



    private val mockLocalDataSource: GroomLocalDataSource = mockk(relaxed = true)
    private val accessTokenManager: AccessTokenManager=AccessTokenManager(mockLocalDataSource)


    @Test
    fun `getAccessToken - retrieves saved token successfully`()  {
        runTest {
            val expectedToken = "valid_access_token"
            coEvery { mockLocalDataSource.getPreference(PreferenceDataStoreConstants.ACCESS_TOKEN, "") } returns flowOf(expectedToken)
            val actualToken = accessTokenManager.getAccessToken().first()
            assertEquals(expectedToken,actualToken)
        }
    }

}