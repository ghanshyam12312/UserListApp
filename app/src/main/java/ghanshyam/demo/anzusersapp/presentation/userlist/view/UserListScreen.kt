package ghanshyam.demo.anzusersapp.presentation.userlist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ghanshyam.demo.anzusersapp.presentation.userlist.view.components.UserRowView
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListIntent
import ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel.UserListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    userListViewModel: UserListViewModel
) {
    val state = userListViewModel.state.collectAsState()


    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            state.value.isLoading-> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.value.usersData) { user ->
                        UserRowView (user = user,  onClick = { userListViewModel.onIntent(
                            UserListIntent.ShowUserDetailsIntent(user = user)) })
                    }
                }
            }
        }
    }
}