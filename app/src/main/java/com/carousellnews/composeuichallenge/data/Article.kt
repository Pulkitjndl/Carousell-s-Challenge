package com.carousellnews.composeuichallenge.data

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val timeCreated: Long,
    val rank: Int
)