package ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProvider
import ghanshyam.demo.anzusersapp.core.viewmodel.MviViewModel
import ghanshyam.demo.anzusersapp.domain.usecase.GetUsersUseCase
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase,
    coroutineContextProvider: CoroutineContextProvider
):
    MviViewModel<UserListIntent, UserListState, UserListEffect>(coroutineContextProvider) {
    override fun initialState(): UserListState = UserListState()

    init {
        onIntent(UserListIntent.LoadUserListIntent)
    }

    override fun onIntent(intent: UserListIntent) {
        when(intent) {
            is UserListIntent.LoadUserListIntent -> getUsers()
            is UserListIntent.ShowUserDetailsIntent ->  launchSafe {
                sendEffect(UserListEffect.NavigateToUserDetail(user = intent.user))
            }
        }
    }

    private fun getUsers() {
        launchWithProgressOnMain(
            handleProgress = true,
            progressDelegate = {
                setState {
                    copy(isLoading = it)
                }
            },
            block = {
                getUsersUseCase(Unit).collect { result ->
                    when(result) {
                        is ResultModel.Failure<*> -> {
                            sendEffect(
                                UserListEffect.ShowErrorMessage(
                                    result.exceptions.localizedMessage ?: "Something went wrong"
                                )
                            )
                        }
                        is ResultModel.Success -> {
                            setState { copy(isLoading = false , usersData = result.response) }
                        }
                        else -> {
                            sendEffect(
                                UserListEffect.ShowErrorMessage(
                                    "Something went wrong"
                                )
                            )
                        }
                    }

                }
            }
        )

    }

}