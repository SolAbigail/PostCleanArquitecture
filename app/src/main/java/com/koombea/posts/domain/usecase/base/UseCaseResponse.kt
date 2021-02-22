package com.koombea.posts.domain.usecase.base

import com.koombea.posts.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}
