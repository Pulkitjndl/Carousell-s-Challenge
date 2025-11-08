package com.carousellnews.composeuichallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carousellnews.composeuichallenge.ui.details.BookDetailsScreen
import com.carousellnews.composeuichallenge.ui.details.BookDetailsViewModel
import com.carousellnews.composeuichallenge.ui.news.NewsScreen
import com.carousellnews.composeuichallenge.ui.news.NewsViewModel
import com.carousellnews.composeuichallenge.ui.search.SearchBooksScreen
import com.carousellnews.composeuichallenge.ui.search.SearchBooksViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.NewsScreen
    ) {

        composable<Routes.SearchScreen> {
            val viewModel: SearchBooksViewModel = hiltViewModel()
            SearchBooksScreen(
                viewModel = viewModel,
                onBookClick = { bookId ->
                    navController.navigate(Routes.DetailsScreen(bookId))
                }
            )
        }

        composable<Routes.DetailsScreen>(
        ) {
            val viewModel: BookDetailsViewModel = hiltViewModel()
            BookDetailsScreen(viewModel = viewModel)
        }

        composable<Routes.NewsScreen>{
            val viewModel: NewsViewModel = hiltViewModel()
            NewsScreen(viewModel = viewModel)
        }
    }
}
