package com.speechify.composeuichallenge.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speechify.composeuichallenge.data.Book
import com.speechify.composeuichallenge.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
//class SearchBooksViewModel @Inject constructor() : ViewModel() {
//
//    private val _allBooks = MutableStateFlow<List<Book>>(emptyList())

//
//    var isLoading by mutableStateOf(true)
//        private set
//
//    var searchQuery by mutableStateOf("")
//        private set
//
//    init {
//        viewModelScope.launch {
//            delay(1500) // simulate loading
//            val dummyBooks = List(10) {
//                Book(
//                    id = it.toString(),
//                    name = "Book Title $it",
//                    author = "Author $it",
//                    imageUrl = "https://picsum.photos/200/300?random=$it",
//                    description = "",
//                    rating = 1f,
//                    reviewCount = 10
//                )
//            }
//            _allBooks.value = dummyBooks
//            _filteredBooks.value = dummyBooks
//            isLoading = false
//        }
//    }
//
//    fun onSearchQueryChange(query: String) {
//        searchQuery = query
//        _filteredBooks.value = if (query.isBlank()) {
//            _allBooks.value
//        } else {
//            _allBooks.value.filter { it.name.contains(query, ignoreCase = true) }
//        }
//    }
//}

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
            _books.value = repository.getBooks()
            _isLoading.value = false
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