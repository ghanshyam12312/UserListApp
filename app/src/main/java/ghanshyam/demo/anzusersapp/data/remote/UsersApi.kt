package ghanshyam.demo.anzusersapp.data.remote

import ghanshyam.demo.anzusersapp.data.models.UsersModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {
    @GET("users")
    suspend fun getUsers(): List<UsersModel>
}