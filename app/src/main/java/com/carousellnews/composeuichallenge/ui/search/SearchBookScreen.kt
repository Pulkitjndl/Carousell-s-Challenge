package com.carousellnews.composeuichallenge.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.carousellnews.composeuichallenge.data.Book

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchBooksScreen(
    viewModel: SearchBooksViewModel = hiltViewModel(),
    onBookClick: (String) -> Unit
) {
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery = viewModel.searchQuery

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    placeholder = { Text("Search by name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = books,
                        key = { it.id }
                    ) { book ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            BookListItem(book, onBookClick)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun BookListItem(book: Book, onBookClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.name,
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(book.name, style = MaterialTheme.typography.titleMedium)
            Text(book.author, style = MaterialTheme.typography.bodyMedium)

            Button(onClick = { onBookClick(book.id) }) {
                Text("Details")
            }
        }
    }
}