package com.koombea.posts.di

import com.koombea.posts.presentation.post.PostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { PostsViewModel(get(), get(), get(), get()) }

    single { createGetPostsUseCase(get()) }

    single { createSavePostsUseCase(get()) }

    single { createGetAllPostsUseCase(get()) }

    single { createIsEmptyUseCase(get() ) }

    single { createPostRepository(get(), get()) }
}