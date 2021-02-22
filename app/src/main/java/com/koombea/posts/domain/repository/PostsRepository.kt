package com.koombea.posts.domain.repository

import com.koombea.posts.domain.model.Data
import com.koombea.posts.domain.model.User

interface PostsRepository {

    suspend fun getUsers(): List<User>
}