package com.carousellnews.composeuichallenge.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object SearchScreen : Routes()

    @Serializable
    data class DetailsScreen(val bookId: String) : Routes()

    @Serializable
    object NewsScreen:Routes()
}