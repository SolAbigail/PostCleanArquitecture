package com.koombea.posts.data.source.remote

import com.koombea.posts.domain.model.Data
import com.koombea.posts.domain.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Data
}