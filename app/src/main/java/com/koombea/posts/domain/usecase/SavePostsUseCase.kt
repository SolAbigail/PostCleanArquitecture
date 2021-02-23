package com.koombea.posts.domain.usecase

import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.repository.PostsRepository
import com.koombea.posts.domain.usecase.base.UseCase

class SavePostsUseCase constructor(
    private val postsRepository: PostsRepository
) : UseCase<Boolean, List<User>?>() {

    override suspend fun run(params: List<User>?): Boolean {
        return postsRepository.saveUsers(params)
    }
}