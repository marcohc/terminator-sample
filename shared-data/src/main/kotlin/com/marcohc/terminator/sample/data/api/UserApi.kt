package com.marcohc.terminator.sample.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api/")
    fun getUsers(@Query("results") results: Int? = 50): Single<GetUsersResponse>
}

data class GetUsersResponse(
    val results: List<UserEntity>
)

data class UserEntity(
    val email: String,
    val name: NameResponse,
    val picture: PictureResponse,
)

data class NameResponse(
    val title: String,
    val first: String,
    val last: String,
)

data class PictureResponse(
    val large: String,
    val medium: String,
    val thumbnail: String,
)
