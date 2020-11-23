package com.kabindra.sample

import com.kabindra.sample.db.model.UserData
import retrofit2.Response
import retrofit2.http.GET

interface SampleApi {

    @GET("/api/v1")
    suspend fun getUser(): Response<List<UserData>>
}