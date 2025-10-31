package ghanshyam.demo.anzusersapp.data.repository

import ghanshyam.demo.anzusersapp.core.errorhandling.AppExceptions
import ghanshyam.demo.anzusersapp.core.errorhandling.ErrorType
import ghanshyam.demo.anzusersapp.core.errorhandling.toCustomError
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProvider
import ghanshyam.demo.anzusersapp.data.models.UsersModel
import ghanshyam.demo.anzusersapp.data.remote.UsersApi
import ghanshyam.demo.anzusersapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private  val usersApi: UsersApi,
    private val coroutineContextProvider: CoroutineContextProvider
): UsersRepository {
    override fun getUsers(): Flow<ResultModel<List<UsersModel>>> = flow {
        try {
            val response = usersApi.getUsers()
            emit(ResultModel.Success(response))
        } catch (e: Exception) {
            emit(ResultModel.Failure(exceptions = AppExceptions(
                e.toCustomError(),
                ErrorType.NETWORK_ERROR
            )))
        }
    }.flowOn(coroutineContextProvider.io)

}