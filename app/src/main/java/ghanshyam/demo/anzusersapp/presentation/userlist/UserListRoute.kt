package ghanshyam.demo.anzusersapp.presentation.userlist


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ghanshyam.demo.anzusersapp.core.ui.scafold.BaseScaffold
import ghanshyam.demo.anzusersapp.core.ui.scafold.CommonTopAppBar
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import ghanshyam.demo.anzusersapp.presentation.userlist.view.UserListScreen
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListEffect
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListViewModel
import kotlinx.coroutines.launch
import com.demo.anzuserapp.R

object UserListRoute{
    const val ROUTE = "userList"

    @Composable
    fun Screen(
        onNavigateToUserDetails: (user: UsersEntity?) -> Unit
    ) {
        val userListViewModel: UserListViewModel = hiltViewModel()
        val snackBarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()


        LaunchedEffect(Unit) {
            userListViewModel.effect.collect { effect ->
                when (effect) {
                    is UserListEffect.NavigateToUserDetail -> { onNavigateToUserDetails(effect.user)}
                    is UserListEffect.ShowErrorMessage -> coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
        BaseScaffold(
            topBar = @Composable { CommonTopAppBar(title = stringResource(R.string.app_name),) },
            snackBarHostState = snackBarHostState
        ) { padding ->
            UserListScreen(
                userListViewModel = userListViewModel, padding = padding
            )
        }

    }
}