package com.koombea.posts.presentation.post

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koombea.posts.domain.model.ApiError
import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.usecase.GetAllPostsUseCase
import com.koombea.posts.domain.usecase.GetPostsUseCase
import com.koombea.posts.domain.usecase.IsEmptyUseCase
import com.koombea.posts.domain.usecase.SavePostsUseCase
import com.koombea.posts.domain.usecase.base.UseCaseResponse
import com.koombea.posts.utils.extratNameUrl
import com.koombea.posts.utils.saveImgToCache
import com.squareup.picasso.Picasso
import kotlinx.coroutines.cancel

class PostsViewModel constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val savePostUseCase: SavePostsUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val isEmptyUseCase: IsEmptyUseCase) : ViewModel(){

    private val _postsData: MutableLiveData<List<User>> = MutableLiveData()
    private val _showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val _messageData: MutableLiveData<String> = MutableLiveData()
    private val _swipeRefreshLayout: MutableLiveData<Boolean> = MutableLiveData()

    val postsData: LiveData<List<User>> = _postsData
    val showProgressBar: LiveData<Boolean> = _showProgressBar
    val messageData: LiveData<String> = _messageData
    val swipeRefreshLayout: LiveData<Boolean> = _swipeRefreshLayout


    fun getPosts() {
        _showProgressBar.value = true
        getPostsUseCase.invoke(viewModelScope, null, object : UseCaseResponse<List<User>> {
            override fun onSuccess(result: List<User>) {
                savePostLocal(result)
                _postsData.value = result
                _showProgressBar.value = false
                _swipeRefreshLayout.value = false

            }

            override fun onError(apiError: ApiError?) {
                _messageData.value = apiError?.getErrorMessage()
                _showProgressBar.value = false
                _swipeRefreshLayout.value = false
            }
        })
    }

    fun savePostLocal(listUser: List<User>){
        savePostUseCase.invoke(viewModelScope, listUser, object : UseCaseResponse<Boolean> {
            override fun onSuccess(result: Boolean) {
                Log.i(TAG, "Datos guardados correctamente")
            }

            override fun onError(apiError: ApiError?) {
                _messageData.value = apiError?.getErrorMessage()
            }
        })
    }
    fun getAllPosts(){
        _showProgressBar.value = true
        getAllPostsUseCase.invoke(viewModelScope, null, object : UseCaseResponse<List<User>> {
            override fun onSuccess(result: List<User>) {
                _postsData.value = result
                _showProgressBar.value = false
                _swipeRefreshLayout.value = false
            }

            override fun onError(apiError: ApiError?) {
                _messageData.value = apiError?.getErrorMessage()
                _showProgressBar.value = false
                _swipeRefreshLayout.value = false
            }
        })
    }


    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = PostsViewModel::class.java.name
    }
}