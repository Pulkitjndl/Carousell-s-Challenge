package com.speechify.composeuichallenge.ui.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Details : Screen("details/{bookId}") {
        fun createRoute(bookId: String) = "details/$bookId"
    }
}