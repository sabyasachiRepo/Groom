package edu.student.groom.util

import androidx.datastore.dataStore
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
    private val accessTokenManager: AccessTokenManager = AccessTokenManager(mockLocalDataSource)


    @Test
    fun `getAccessToken - retrieves saved token successfully`() {
        runTest {
            val expectedToken = "valid_access_token"
            coEvery {
                mockLocalDataSource.getPreference(
                    PreferenceDataStoreConstants.ACCESS_TOKEN, ""
                )
            } returns flowOf(expectedToken)
            val actualToken = accessTokenManager.getAccessToken().first()
            assertEquals(expectedToken, actualToken)
        }
    }

    @Test
    fun `getAccessToken - retrieves empty token if no token is saved`() {
        runTest {
            coEvery {
                mockLocalDataSource.getPreference(
                    PreferenceDataStoreConstants.ACCESS_TOKEN, ""
                )
            } returns flowOf("")
            val actualToken = accessTokenManager.getAccessToken().first()
            assertEquals("", actualToken)
        }
    }

    @Test
    fun `saveAccessToken - saves token to data source`() {
        runTest {
            val token = "saved_access_token"
            accessTokenManager.saveAccessToken(token)
          coVerify { mockLocalDataSource.putPreference( PreferenceDataStoreConstants.ACCESS_TOKEN,token) }
        }
    }

    @Test
    fun `clearAccessToken - clears access token in data source`() {
        runTest {
            accessTokenManager.clearAccessToken()
            coVerify { mockLocalDataSource.removePreference( PreferenceDataStoreConstants.ACCESS_TOKEN) }
        }
    }

}