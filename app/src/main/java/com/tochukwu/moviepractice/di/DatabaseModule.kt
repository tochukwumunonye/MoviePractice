package com.tochukwu.moviepractice.di

import android.content.Context
import androidx.room.Room
import com.tochukwu.moviepractice.data.local.MoviesDatabase
import com.tochukwu.moviepractice.data.local.model.MovieEntityMapper
import com.tochukwu.moviepractice.util.Constants.Companion.MOVIE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideMovieEntityMapper(): MovieEntityMapper = MovieEntityMapper()

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context)
    = Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIE_DATABASE_NAME ).build()



    @Singleton
    @Provides
    fun provideMovieDao(db: MoviesDatabase) = db.moviesDao()

}

