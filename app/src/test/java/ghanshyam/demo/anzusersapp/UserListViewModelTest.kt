
@file:OptIn(ExperimentalCoroutinesApi::class)
package ghanshyam.demo.anzusersapp

import app.cash.turbine.test
import ghanshyam.demo.anzusersapp.core.errorhandling.AppExceptions
import ghanshyam.demo.anzusersapp.core.errorhandling.ErrorType
import ghanshyam.demo.anzusersapp.core.errorhandling.toCustomError
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProvider
import ghanshyam.demo.anzusersapp.domain.usecase.GetUsersUseCase
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListEffect
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListIntent
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListState
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListViewModel
import ghanshyam.demo.anzusersapp.utils.TestCoroutineContextProvider
import ghanshyam.demo.anzusersapp.utils.UserDummyData.usersEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var coroutineContextProvider: TestCoroutineContextProvider

    private val testDispatcher = StandardTestDispatcher()

    private val fakeUsers = usersEntity

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coroutineContextProvider = TestCoroutineContextProvider()
        getUsersUseCase = mockk()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ✅ 1. Success case
    @Test
    fun `getUsers() emits Success and updates state with data`() = runTest {
        coEvery { getUsersUseCase.invoke(Unit) } returns flow {
            emit(ResultModel.Success(fakeUsers))
        }

        viewModel = UserListViewModel(getUsersUseCase, coroutineContextProvider)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(fakeUsers, state.usersData)
    }

    // ✅ 2. Failure case
    @Test
    fun `getUsers() emits Failure and triggers ShowErrorMessage effect`() = runTest {
        coEvery { getUsersUseCase.invoke(Unit) } returns flow {
            emit(ResultModel.Failure(AppExceptions("Network error".toCustomError(), errorType = ErrorType.NETWORK_ERROR)))
        }

        viewModel = UserListViewModel(getUsersUseCase, coroutineContextProvider)

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is UserListEffect.ShowErrorMessage)
            assertEquals("Network error", (effect as UserListEffect.ShowErrorMessage).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ 3. Loading state toggle test
    @Test
    fun `when Init intent is sent then loading state toggles true then false`() = runTest {
        val emittedStates = mutableListOf<UserListState>()

        coEvery { getUsersUseCase.invoke(Unit) } returns flow {
            delay(100)
            emit(ResultModel.Success(fakeUsers))
        }

        viewModel = UserListViewModel(getUsersUseCase, coroutineContextProvider)

        val job = launch { viewModel.state.collect { emittedStates.add(it) } }

        advanceUntilIdle()

        assertTrue(emittedStates.any { it.isLoading })
        val finalState = emittedStates.last()
        assertFalse(finalState.isLoading)
        assertEquals(fakeUsers, finalState.usersData)

        job.cancel()
    }

    // ✅ 4. Navigate to details effect
    @Test
    fun `ShowUserDetailsIntent triggers NavigateToUserDetail effect`() = runTest {
        val user = fakeUsers.first()
        coEvery { getUsersUseCase.invoke(Unit) } returns flow { emit(ResultModel.Success(fakeUsers)) }

        viewModel = UserListViewModel(getUsersUseCase, coroutineContextProvider)

        viewModel.onIntent(UserListIntent.ShowUserDetailsIntent(user))

        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is UserListEffect.NavigateToUserDetail)
            assertEquals(user, (effect as UserListEffect.NavigateToUserDetail).user)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
