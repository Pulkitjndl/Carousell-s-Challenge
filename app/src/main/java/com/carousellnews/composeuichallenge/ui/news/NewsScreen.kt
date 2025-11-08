package com.carousellnews.composeuichallenge.ui.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.carousellnews.composeuichallenge.data.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val sortType by viewModel.sortType.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carousell News") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    DropdownMenuWithSort(
                        current = sortType,
                        onSelect = viewModel::onSortChange
                    )
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(articles, key = { _, article -> article.id }) { index, article ->
                        ArticleItem(
                            article, modifier = Modifier.padding(
                                top = if (index == 0) 16.dp else 0.dp,
                                bottom = if (index == articles.lastIndex) 16.dp else 0.dp
                            )
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ArticleItem(article: Article,modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Column {
            AsyncImage(
                model = article.bannerUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Column(Modifier.padding(12.dp)) {
                Text(
                    text = article.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = article.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))
                Text(
                    text = formatRelativeTime(article.timeCreated),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun DropdownMenuWithSort(
    current: NewsViewModel.SortType,
    onSelect: (NewsViewModel.SortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreHoriz, contentDescription = "Menu")
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text("Recent") },
            onClick = {
                onSelect(NewsViewModel.SortType.RECENT)
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Popular") },
            onClick = {
                onSelect(NewsViewModel.SortType.POPULAR)
                expanded = false
            }
        )
    }
}

fun formatRelativeTime(timestamp: Long): String {
    val now = System.currentTimeMillis() / 1000
    val diff = now - timestamp
    val days = diff / (60 * 60 * 24)

    return when {
        days < 1 -> "Today"
        days < 7 -> "$days days ago"
        days < 30 -> "${days / 7} week${if (days / 7 > 1) "s" else ""} ago"
        days < 365 -> "${days / 30} month${if (days / 30 > 1) "s" else ""} ago"
        else -> "${days / 365} year${if (days / 365 > 1) "s" else ""} ago"
    }
}

