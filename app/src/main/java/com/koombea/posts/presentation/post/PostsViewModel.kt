package com.koombea.posts.presentation.post

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koombea.posts.domain.model.ApiError
import com.koombea.posts.domain.model.Data
import com.koombea.posts.domain.model.User
import com.koombea.posts.domain.usecase.GetPostsUseCase
import com.koombea.posts.domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel

class PostsViewModel constructor(private val getPostsUseCase: GetPostsUseCase) : ViewModel(){

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
                Log.i(TAG, "result: $result")
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

    fun onItemClick(url: String){
        Log.i(TAG, "result: $url")
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = PostsViewModel::class.java.name
    }
}