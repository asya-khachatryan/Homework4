package com.example.homework4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework4.data.RetrofitHelper
import com.example.homework4.entity.Article
import com.example.homework4.repository.NewsRepo
import com.example.homework4.service.NewsApiService
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {
    private val _news = MutableLiveData<Result<List<Article>>>()
    val news: LiveData<Result<List<Article>>> = _news

    fun loadNews() {
        viewModelScope.launch {
            try {
                val response = NewsRepo(RetrofitHelper.getInstance().create(NewsApiService::class.java)).loadNews()
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                _news.postValue(Result.error(e))
            }
        }
    }
}

sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}