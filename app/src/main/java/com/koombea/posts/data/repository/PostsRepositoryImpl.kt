package com.koombea.posts.data.repository

import com.koombea.posts.data.source.local.LocalDataSource
import com.koombea.posts.data.source.remote.ApiService
import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.repository.PostsRepository

class PostsRepositoryImpl(private val apiService: ApiService, private val localDataSource: LocalDataSource) : PostsRepository {


    override suspend fun getUsers(): List<User> {
        return apiService.getPosts().data
    }

    override suspend fun saveUsers(userlist: List<User>?): Boolean {
        return localDataSource.savePost(userlist)
    }

    override suspend fun getAllPost(): List<User> {
        return localDataSource.getAllPost()
    }

    override suspend fun isEmpty(): Boolean {
        return localDataSource.isEmpty()
    }


}