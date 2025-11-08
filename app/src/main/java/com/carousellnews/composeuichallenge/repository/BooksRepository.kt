package com.carousellnews.composeuichallenge.repository

import android.content.Context
import com.carousellnews.composeuichallenge.R
import com.carousellnews.composeuichallenge.data.Article
import com.carousellnews.composeuichallenge.data.Book
import com.carousellnews.composeuichallenge.data.db.ArticleDao
import com.carousellnews.composeuichallenge.data.db.ArticleEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


///////////////////////////////////////////////////////
//                                                   //
//              DO NOT MODIFY THIS FILE              //
//                                                   //
///////////////////////////////////////////////////////
interface BooksRepository {
    suspend fun getBooks(): List<Book>
    suspend fun getBook(id: String): Book?
    suspend fun searchBook(query: String): List<Book>
}

@Singleton
class BooksRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : BooksRepository {

    private val books by lazy { parseBooksList() }

    override suspend fun getBooks(): List<Book> {
        delay(1500)
        return books
    }

    override suspend fun getBook(id: String): Book? {
        delay(1000)
        return books.find { it.id == id }
    }

    override suspend fun searchBook(query: String): List<Book> {
        delay(100)
        return books.filter { it.name.contains(query, ignoreCase = true) }
    }

    private fun parseBooksList(): List<Book> {
        val inputStream = context.resources.openRawResource(R.raw.books_information)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Json.decodeFromString(
            deserializer = ListSerializer(Product.serializer()),
            string = jsonString
        ).map {
            Book(
                id = it.id,
                name = it.name,
                imageUrl = it.thumbnails.large,
                author = it.details.authors.first(),
                description = it.description.orEmpty(),
                rating = it.details.goodreadsRating.rating.toFloat(),
                reviewCount = it.details.goodreadsRating.count.toString().take(3).toInt(),
            )
        }
    }
}

interface NewsRepository {
    suspend fun getArticles(): List<Article>
}

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val dao: ArticleDao,
    @param:ApplicationContext private val context: Context
) : NewsRepository {

    override suspend fun getArticles(): List<Article> = withContext(Dispatchers.IO) {
        try {
            val url =
                "https://storage.googleapis.com/carousell-interview-assets/android/carousell_news.json"
            val json = URL(url).readText()
            val remoteArticles =
                Json.decodeFromString(ListSerializer(ArticleDto.serializer()), json)
            dao.clearAll()
            dao.insertArticles(remoteArticles.map { it.toEntity() })

            dao.getAllArticles().map { it.toDomain() }
        } catch (e: Exception) {
            dao.getAllArticles().map { it.toDomain() }
        }
    }
}

fun ArticleDto.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        description = description,
        bannerUrl = bannerUrl,
        timeCreated = timeCreated,
        rank = rank
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title.trim(),
        description = description.trim(),
        bannerUrl = bannerUrl,
        rank = rank,
        timeCreated = timeCreated
    )
}
