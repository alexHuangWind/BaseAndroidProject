package com.publica.baseproject.data.remote.api

import com.publica.baseproject.model.Post
import retrofit2.Response
import retrofit2.http.GET


interface BaseApiService {

    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): Response<List<Post>>

    companion object {
        const val BASE_API_URL = "https://patilshreyas.github.io/"
    }

}