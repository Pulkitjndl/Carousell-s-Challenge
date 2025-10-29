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
        startDestination = Screen.Search.route
    ) {

        composable(route = Screen.Search.route) {
            val viewModel: SearchBooksViewModel = hiltViewModel()
            SearchBooksScreen(
                viewModel = viewModel,
                onBookClick = { bookId ->
                    navController.navigate(Screen.Details.createRoute(bookId))
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) {
            val viewModel: BookDetailsViewModel = hiltViewModel()
            BookDetailsScreen(viewModel = viewModel)
        }
    }
}
