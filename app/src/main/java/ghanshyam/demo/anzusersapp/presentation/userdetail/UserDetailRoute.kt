package ghanshyam.demo.anzusersapp.presentation.userdetail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ghanshyam.demo.anzusersapp.core.ui.scafold.BaseScaffold
import ghanshyam.demo.anzusersapp.core.ui.scafold.CommonTopAppBar
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import ghanshyam.demo.anzusersapp.presentation.userdetail.view.UserDetailsScreen

object UserDetailRoute{
    const val ROUTE = "userDetail"

    @Composable
    fun Screen(
        user: UsersEntity?,
        onNavigateBack: () -> Unit
    ) {
        val snackBarHostState = remember { SnackbarHostState() }

        BaseScaffold(
            topBar = { CommonTopAppBar(title = user?.name ?: "", showNavigationIcon = true, onNavigationIconClick = {
                onNavigateBack
            }) },
            snackBarHostState = snackBarHostState
        ) { innerPadding ->
            UserDetailsScreen(
                padding = innerPadding,
               user = user
            )
        }

    }
}