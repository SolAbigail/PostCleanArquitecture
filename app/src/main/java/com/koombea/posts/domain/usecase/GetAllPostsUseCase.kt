package com.koombea.posts.domain.usecase

import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.repository.PostsRepository
import com.koombea.posts.domain.usecase.base.UseCase

class GetAllPostsUseCase constructor(
    private val postsRepository: PostsRepository
) : UseCase<List<User>, Any?>() {

    override suspend fun run(params: Any?): List<User> {
        return postsRepository.getAllPost()
    }
}