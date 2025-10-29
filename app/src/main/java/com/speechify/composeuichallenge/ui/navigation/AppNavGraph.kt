package com.speechify.composeuichallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.speechify.composeuichallenge.ui.details.BookDetailsScreen
import com.speechify.composeuichallenge.ui.details.BookDetailsViewModel
import com.speechify.composeuichallenge.ui.search.SearchBooksScreen
import com.speechify.composeuichallenge.ui.search.SearchBooksViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SearchScreen
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
    }
}
