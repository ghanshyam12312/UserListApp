package ghanshyam.demo.anzusersapp.data.mapper

import ghanshyam.demo.anzusersapp.core.mappers.BaseMappers
import ghanshyam.demo.anzusersapp.data.models.UsersModel
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity
import jakarta.inject.Inject

class UsersMapper @Inject constructor() : BaseMappers<UsersModel, UsersEntity?> {
    override fun mapFromModel(model: UsersModel): UsersEntity? {
        if (model.id == null || model.name.isNullOrBlank() || model.userName.isNullOrBlank()) return null
        return UsersEntity(
            id = model.id,
            username = model.userName,
            name = model.name,
            photo = model.photo ?: "",
            company = model.company ?: "",
            email = model.email ?: "",
            address =  model.address ?: "",
            zip = model.zip ?: "",
            state = model.state ?: "",
            country = model.country ?: ""
        )
    }
}