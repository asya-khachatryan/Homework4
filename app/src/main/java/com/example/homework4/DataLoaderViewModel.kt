package com.example.homework4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework4.data.RetrofitHelper
import com.example.homework4.entity.Article
import com.example.homework4.exeption.NoResultsFoundException
import com.example.homework4.repository.NewsRepo
import com.example.homework4.service.NewsApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {
    private val _news = MutableLiveData<Result<List<Article>>>()
    val news: LiveData<Result<List<Article>>> = _news
    private val apiService: NewsApiService =
        RetrofitHelper.getInstance().create(NewsApiService::class.java)
    private val _isInRefreshState = MutableStateFlow(false)
    val isInRefreshState: StateFlow<Boolean>
        get() = _isInRefreshState.asStateFlow()

    fun loadNews() {
        viewModelScope.launch {
            try {
                _news.postValue(Result.loading())
                val response = NewsRepo(apiService).loadNews()
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                _news.postValue(Result.error(e))
            }
        }
        _isInRefreshState.tryEmit(false)
    }

    fun loadNewsWithFilter(
        category: String? = null
    ) {
        viewModelScope.launch {
            try {
                _news.postValue(Result.loading())
                val response =
                    NewsRepo(apiService).loadNews(category = category)
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
            }
        }
    }

    fun loadNewsWithSearch(
        searchTerm: String? = null
    ) {
        viewModelScope.launch {
            try {
                _news.postValue(Result.loading())
                val response = NewsRepo(apiService).loadNews(query = searchTerm)
                if (response.isEmpty()) {
                    throw NoResultsFoundException("No results found")
                }
                _news.postValue(Result.success(response))

            } catch (e: NoResultsFoundException) {
                _news.postValue(Result.error(e))
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
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