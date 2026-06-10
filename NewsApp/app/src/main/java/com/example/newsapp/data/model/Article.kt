package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Source(
    val id: String?,
    val name: String?
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String,
    val description: String?,
    @SerializedName("url") val articleUrl: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)
