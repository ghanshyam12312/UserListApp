package ghanshyam.demo.anzusersapp

import ghanshyam.demo.anzusersapp.core.errorhandling.AppExceptions
import ghanshyam.demo.anzusersapp.core.errorhandling.ErrorType
import ghanshyam.demo.anzusersapp.core.errorhandling.toCustomError
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.data.mapper.UsersMapper
import ghanshyam.demo.anzusersapp.data.models.UsersModel
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import ghanshyam.demo.anzusersapp.domain.repository.UsersRepository
import ghanshyam.demo.anzusersapp.domain.usecase.GetUsersUseCase
import ghanshyam.demo.anzusersapp.utils.UserDummyData.userEntity
import ghanshyam.demo.anzusersapp.utils.UserDummyData.validUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUsersUseCaseTest {

    private lateinit var repository: UsersRepository
    private lateinit var usersMapper: UsersMapper
    private lateinit var useCase: GetUsersUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        usersMapper = mockk()
        useCase = GetUsersUseCase(repository, usersMapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke returns mapped Success result when repository emits Success`() = runTest {
        // Given
        val networkResponse = listOf(validUser)
        val mappedEntities = listOf(userEntity)
        val successResult = ResultModel.Success(networkResponse)

        coEvery { repository.getUsers() } returns flowOf(successResult)
        every { usersMapper.mapList(networkResponse) } returns mappedEntities

        // When
        val resultList = mutableListOf<ResultModel<List<UsersEntity?>>>()
        useCase(Unit).toList(resultList)

        // Then
        assertEquals(1, resultList.size)
        val result = resultList.first()
        assertTrue(result is ResultModel.Success)
        assertEquals(mappedEntities, (result as ResultModel.Success).response)
    }

    @Test
    fun `invoke returns Failure when repository emits Failure`() = runTest {
        // Given
        val error = AppExceptions("Network error".toCustomError(), ErrorType.NETWORK_ERROR)
        val failureResult = ResultModel.Failure<List<UsersModel>>(error)

        coEvery { repository.getUsers() } returns flowOf(failureResult)

        // When
        val resultList = mutableListOf<ResultModel<List<UsersEntity?>>>()
        useCase(Unit).toList(resultList)

        // Then
        val result = resultList.first()
        assertTrue(result is ResultModel.Failure)
    }
}
