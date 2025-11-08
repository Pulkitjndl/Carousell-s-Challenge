package com.carousellnews.composeuichallenge.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

///////////////////////////////////////////////////////
//                                                   //
//              DO NOT MODIFY THIS FILE              //
//                                                   //
///////////////////////////////////////////////////////
@Serializable
data class Product(
    val id: String,
    val name: String,
    val status: String,
    val type: String,
    val supplier: String,
    val details: Details,
    val thumbnails: Thumbnails,
    val description: String?
)

@Serializable
data class Details(
    val authors: List<String>,
    val goodreadsRating: GoodreadsRating,
    val chapters: List<Chapter>,
    val publicationDate: String?
)

@Serializable
data class GoodreadsRating(
    val rating: Double,
    val count: Int
)

@Serializable
data class Chapter(
    val title: String
)

@Serializable
data class Thumbnails(
    val small: String,
    val large: String
)

@Serializable
data class ArticleDto(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("banner_url") val bannerUrl: String,
    @SerialName("time_created") val timeCreated: Long,
    val rank: Int
)