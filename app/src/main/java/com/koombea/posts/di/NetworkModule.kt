package com.koombea.posts.di

import android.content.Context
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.koombea.posts.BuildConfig
import com.koombea.posts.data.repository.PostsRepositoryImpl
import com.koombea.posts.data.source.local.LocalDataSource
import com.koombea.posts.data.source.remote.ApiService
import com.koombea.posts.domain.repository.PostsRepository
import com.koombea.posts.domain.usecase.GetAllPostsUseCase
import com.koombea.posts.domain.usecase.GetPostsUseCase
import com.koombea.posts.domain.usecase.IsEmptyUseCase
import com.koombea.posts.domain.usecase.SavePostsUseCase
import com.squareup.moshi.Moshi
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { MoshiConverterFactory.create() }

    single { Moshi.Builder().build() }

    single { createData() }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://mock.koombea.io/mt/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createPostRepository(apiService: ApiService, localDataSource: LocalDataSource): PostsRepository {
    return PostsRepositoryImpl(apiService, localDataSource)
}

fun createData(): LocalDataSource {
    return LocalDataSource(Database("posts", DatabaseConfiguration()))
}

//UseCases
fun createGetPostsUseCase(postsRepository: PostsRepository): GetPostsUseCase {
    return GetPostsUseCase(postsRepository)
}

fun createSavePostsUseCase(postsRepository: PostsRepository): SavePostsUseCase {
    return SavePostsUseCase(postsRepository)
}

fun createGetAllPostsUseCase(postsRepository: PostsRepository): GetAllPostsUseCase {
    return GetAllPostsUseCase(postsRepository)
}

fun createIsEmptyUseCase(postsRepository: PostsRepository): IsEmptyUseCase{
    return IsEmptyUseCase(postsRepository)
}
