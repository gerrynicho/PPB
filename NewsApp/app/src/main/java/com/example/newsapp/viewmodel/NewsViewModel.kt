package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _searchState = MutableStateFlow<NewsUiState>(NewsUiState.Success(emptyList()))
    val searchState: StateFlow<NewsUiState> = _searchState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles: StateFlow<List<Article>> = _savedArticles.asStateFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                val articles = repository.getTopHeadlines()
                _uiState.value = NewsUiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchNews(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchState.value = NewsUiState.Success(emptyList())
            return
        }
        viewModelScope.launch {
            _searchState.value = NewsUiState.Loading
            try {
                val articles = repository.searchNews(query)
                _searchState.value = NewsUiState.Success(articles)
            } catch (e: Exception) {
                _searchState.value = NewsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleSave(article: Article) {
        val current = _savedArticles.value.toMutableList()
        if (current.contains(article)) current.remove(article) else current.add(article)
        _savedArticles.value = current.toList()
    }

    fun isSaved(article: Article): Boolean = _savedArticles.value.contains(article)

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }
}
