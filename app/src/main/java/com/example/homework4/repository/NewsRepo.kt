package com.example.homework4.repository

import com.example.homework4.entity.Article
import com.example.homework4.service.NewsApiService

class NewsRepo (private val apiService: NewsApiService) {
    suspend fun loadNews(): List<Article> {
        return apiService.fetchNews().articles
    }
}