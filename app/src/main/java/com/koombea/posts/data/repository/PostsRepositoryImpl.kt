package com.koombea.posts.data.repository

import com.koombea.posts.data.source.remote.ApiService
import com.koombea.posts.domain.model.Data
import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.repository.PostsRepository

class PostsRepositoryImpl(private val apiService: ApiService) : PostsRepository {


    override suspend fun getUsers(): List<User> {
        return apiService.getPosts().data
    }


}