package ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel

import ghanshyam.demo.anzusersapp.core.viewmodel.ViewEffect
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity

sealed class UserListEffect : ViewEffect {
    data class ShowErrorMessage(val message: String) : UserListEffect()
    data class NavigateToUserDetail(val user: UsersEntity?) : UserListEffect()
}