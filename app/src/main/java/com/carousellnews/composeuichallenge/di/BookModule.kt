package com.carousellnews.composeuichallenge.di

import android.content.Context
import androidx.room.Room
import com.carousellnews.composeuichallenge.data.db.ArticleDao
import com.carousellnews.composeuichallenge.data.db.NewsDatabase
import com.carousellnews.composeuichallenge.repository.BooksRepository
import com.carousellnews.composeuichallenge.repository.BooksRepositoryImpl
import com.carousellnews.composeuichallenge.repository.NewsRepository
import com.carousellnews.composeuichallenge.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BookModule {

    @Binds
    fun bindBooksRepository(
        implementation: BooksRepositoryImpl
    ): BooksRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: NewsDatabase): ArticleDao {
        return database.articleDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}
