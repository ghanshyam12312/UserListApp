package ghanshyam.demo.anzusersapp.domain.repository

import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.data.models.UsersModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(): Flow<ResultModel<List<UsersModel>>>
}