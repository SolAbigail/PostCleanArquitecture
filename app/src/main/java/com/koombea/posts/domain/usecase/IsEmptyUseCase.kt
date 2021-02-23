package com.koombea.posts.domain.usecase

import com.koombea.posts.domain.repository.PostsRepository

class IsEmptyUseCase(private val postsRepository: PostsRepository) {

     suspend fun execute(): Boolean{
        return postsRepository.isEmpty()
    }
}