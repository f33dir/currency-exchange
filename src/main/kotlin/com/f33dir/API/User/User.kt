package com.f33dir.API.User

import kotlinx.serialization.Serializable

@Serializable
data class User (val id : Int?,
                 val login : String?,
                 val password : String?,
                 val isAdmin : Boolean?) {
}