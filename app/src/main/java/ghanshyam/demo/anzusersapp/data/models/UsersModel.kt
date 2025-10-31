package ghanshyam.demo.anzusersapp.data.models

import com.google.gson.annotations.SerializedName

data class UsersModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("company") val company: String? = null,
    @SerializedName("username") val userName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("zip") val zip: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("photo") val photo: String? = null
)