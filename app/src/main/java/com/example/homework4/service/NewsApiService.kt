package com.example.homework4.service

import com.example.homework4.entity.NewsResponse
import com.example.homework4.util.Constants.API_KEY
import com.example.homework4.util.Constants.VERSION
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("$VERSION/top-headlines")
    suspend fun fetchNews(
        @Query("country") countryCode: String = "us",
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}