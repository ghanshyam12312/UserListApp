package ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel

import ghanshyam.demo.anzusersapp.core.viewmodel.ViewIntent
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity

sealed class UserListIntent : ViewIntent {
    object LoadUserListIntent: UserListIntent()
    data class ShowUserDetailsIntent(val user: UsersEntity?) : UserListIntent()
}