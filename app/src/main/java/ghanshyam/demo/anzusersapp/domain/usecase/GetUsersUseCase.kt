package ghanshyam.demo.anzusersapp.domain.usecase

import ghanshyam.demo.anzusersapp.core.usecase.BaseUseCase
import ghanshyam.demo.anzusersapp.core.util.ResultModel
import ghanshyam.demo.anzusersapp.core.util.mapResult
import ghanshyam.demo.anzusersapp.data.mapper.UsersMapper
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import ghanshyam.demo.anzusersapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository,
    private  val usersMapper: UsersMapper
): BaseUseCase<Unit, Flow<ResultModel<List<UsersEntity?>>>> {
    override fun invoke(param: Unit): Flow<ResultModel<List<UsersEntity?>>> {
        return repository.getUsers().map {
            it.mapResult { response -> usersMapper.mapList(response) }
        }
    }
}