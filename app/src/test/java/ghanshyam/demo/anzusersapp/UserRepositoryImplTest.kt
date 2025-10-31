package ghanshyam.demo.anzusersapp

import ghanshyam.demo.anzusersapp.core.errorhandling.ErrorType
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.data.models.UsersModel
import ghanshyam.demo.anzusersapp.data.remote.UsersApi
import ghanshyam.demo.anzusersapp.data.repository.UserRepositoryImpl
import ghanshyam.demo.anzusersapp.utils.UserDummyData.usermodelList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private lateinit var usersApi: UsersApi
    private lateinit var repository: UserRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        usersApi = mockk()
        repository = UserRepositoryImpl(usersApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers emits Success when API call succeeds`() = runTest {
        // Given
        val fakeUsers = usermodelList

        coEvery { usersApi.getUsers() } returns fakeUsers

        // When
        val result = repository.getUsers().first()

        // Then
        assertTrue(result is ResultModel.Success)
        assertEquals(fakeUsers, (result as ResultModel.Success).response)
        coVerify(exactly = 1) { usersApi.getUsers() }
    }

    @Test
    fun `getUsers emits Failure when API throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network down")
        coEvery { usersApi.getUsers() } throws exception

        // When
        val result = repository.getUsers().first()

        // Then
        assertTrue(result is ResultModel.Failure)
        val failure = result as ResultModel.Failure
        assertEquals(ErrorType.NETWORK_ERROR, failure.exceptions.errorType)
        assertEquals("Network down", failure.exceptions.localizedMessage)
        coVerify(exactly = 1) { usersApi.getUsers() }
    }
}
