package ghanshyam.demo.anzusersapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersEntity(
    val id: Int,
    val name: String,
    val username: String,
    val photo: String,
    val company: String,
    val email: String,
    val address: String,
    val zip: String,
    val state: String,
    val country: String
): Parcelable