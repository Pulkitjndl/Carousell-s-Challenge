package com.carousellnews.composeuichallenge.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val timeCreated: Long,
    val rank: Int
)