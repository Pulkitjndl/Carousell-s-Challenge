package com.carousellnews.composeuichallenge.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carousellnews.composeuichallenge.data.Article
import com.carousellnews.composeuichallenge.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.RECENT)
    val sortType = _sortType.asStateFlow()

    init {
        loadArticles()
    }

    fun onSortChange(type: SortType) {
        _sortType.value = type
        sortArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _articles.value = repository.getArticles().sortedByDescending { it.timeCreated }
            _isLoading.value = false
        }
    }

    private fun sortArticles() {
        _articles.update { list ->
            when (_sortType.value) {
                SortType.RECENT -> list.sortedByDescending { it.timeCreated }
                SortType.POPULAR -> list.sortedWith(compareByDescending<Article> { it.rank }
                    .thenByDescending { it.timeCreated })
            }
        }
    }

    enum class SortType { RECENT, POPULAR }
}
