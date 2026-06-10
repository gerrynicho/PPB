package com.example.newsapp.data.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.api.RetrofitClient
import com.example.newsapp.data.model.Article

class NewsRepository {
    private val apiKey = BuildConfig.NEWS_API_KEY

    suspend fun getTopHeadlines(): List<Article> =
        RetrofitClient.apiService.getTopHeadlines(apiKey = apiKey).articles

    suspend fun searchNews(query: String): List<Article> =
        RetrofitClient.apiService.searchNews(query = query, apiKey = apiKey).articles
}
