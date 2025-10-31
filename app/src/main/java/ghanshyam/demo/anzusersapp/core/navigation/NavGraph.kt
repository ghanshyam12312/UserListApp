package ghanshyam.demo.anzusersapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import ghanshyam.demo.anzusersapp.presentation.userdetail.UserDetailRoute
import ghanshyam.demo.anzusersapp.presentation.userlist.UserListRoute


@Composable
fun NavGraph(
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = UserListRoute.ROUTE) {
        composable(UserListRoute.ROUTE) {
            UserListRoute.Screen(
                onNavigateToUserDetails = { it ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", it)
                    navController.navigate(UserDetailRoute.ROUTE)

                }
            )
        }
        composable(UserDetailRoute.ROUTE) {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<UsersEntity>("user")
            UserDetailRoute.Screen(
                user = user,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}
