package com.koombea.posts.data.source.local

import com.koombea.posts.domain.model.User

interface iLocalDataSource {

    suspend fun isEmpty(): Boolean
    suspend fun savePost(listUser: List<User>?): Boolean
    suspend fun getAllPost(): List<User>
}