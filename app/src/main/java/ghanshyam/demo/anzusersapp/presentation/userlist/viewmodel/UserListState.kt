package ghanshyam.demo.anzusersapp.presentation.userlist.viewmodel

import ghanshyam.demo.anzusersapp.core.viewmodel.ViewState
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity

data class UserListState(val isLoading: Boolean = false,
    val usersData: List<UsersEntity?> = listOf<UsersEntity>()): ViewState