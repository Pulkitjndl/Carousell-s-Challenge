package com.speechify.composeuichallenge.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speechify.composeuichallenge.data.Book
import com.speechify.composeuichallenge.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repository: BooksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var book by mutableStateOf<Book?>(null)
        private set

    init {
        val bookId = savedStateHandle.get<String>("bookId")
        if (bookId != null) {
            loadBook(bookId)
        }
    }

    private fun loadBook(id: String) {
        viewModelScope.launch {
            isLoading = true
            book = repository.getBook(id)
            isLoading = false
        }
    }
}
