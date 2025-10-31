package ghanshyam.demo.anzusersapp.utils


import ghanshyam.demo.anzusersapp.data.models.UsersModel
import ghanshyam.demo.anzusersapp.domain.entity.UsersEntity

object UserDummyData {

    val validUser = UsersModel(
        id = 1,
        name = "John Doe",
        userName = "johnd",
        photo = "https://example.com/john.png",
        company = "Company Inc.",
        email = "john@example.com",
        address = "123 Main St",
        zip = "12345",
        state = "CA",
        country = "USA",
        phone = "1234567890"
    )

    val inValidUser = UsersModel(
        id = null,
        name = null,
        userName = "invalid",
        photo = "url",
    )
    val usermodelList = listOf(validUser, inValidUser)


    val userEntity = UsersEntity(
        id = 1,
        name = "John Doe",
        username = "johnd",
        photo = "https://example.com/john.png",
        company = "Company Inc.",
        email = "john@example.com",
        address = "123 Main St",
        zip = "12345",
        state = "CA",
        country = "USA",

    )

    val usersEntity = listOf(userEntity)


}