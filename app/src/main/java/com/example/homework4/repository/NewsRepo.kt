package com.example.homework4.repository

import com.example.homework4.entity.Article
import com.example.homework4.service.NewsApiService

class NewsRepo(private val apiService: NewsApiService) {
    suspend fun loadNews(
        category: String? = null,
        query: String? = null
    ): List<Article> {
        return apiService.fetchNews(category = category, query = query).articles
    }
}