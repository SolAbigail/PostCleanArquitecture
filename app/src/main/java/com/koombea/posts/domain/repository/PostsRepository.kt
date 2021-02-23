package com.koombea.posts.domain.repository

import com.koombea.posts.domain.model.User

interface PostsRepository {

    suspend fun getUsers(): List<User>

    suspend fun saveUsers(userlist: List<User>?): Boolean

    suspend fun getAllPost(): List<User>

    suspend fun isEmpty(): Boolean
}