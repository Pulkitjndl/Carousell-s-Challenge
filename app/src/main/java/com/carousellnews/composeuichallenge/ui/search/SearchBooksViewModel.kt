package com.carousellnews.composeuichallenge.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carousellnews.composeuichallenge.data.Book
import com.carousellnews.composeuichallenge.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _books.value = repository.getBooks()

            } catch (e: Exception) {
                Log.e("SearchBooksVM", "Failed to load books", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        viewModelScope.launch {
            if (query.isBlank()) {
                _books.value = repository.getBooks()
            } else {
                _books.value = repository.searchBook(query)
            }
        }
    }
}