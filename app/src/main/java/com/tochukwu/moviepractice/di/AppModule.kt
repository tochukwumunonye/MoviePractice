package com.tochukwu.moviepractice.di

import com.tochukwu.moviepractice.data.local.MoviesDao
import com.tochukwu.moviepractice.data.local.model.MovieEntityMapper
import com.tochukwu.moviepractice.data.remote.MovieAPI
import com.tochukwu.moviepractice.data.remote.model.MovieDtoMapper
import com.tochukwu.moviepractice.repository.MovieRepository
import com.tochukwu.moviepractice.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        dao: MoviesDao,
        movieApi: MovieAPI,
        movieDtoMapper:MovieDtoMapper,
        movieEntityMapper: MovieEntityMapper,
    ) = MovieRepositoryImpl(

        movieEntityMapper = movieEntityMapper,
        movieDtoMapper = movieDtoMapper,
        moviesDao = dao,
        movieApi = movieApi,

    ) as MovieRepository
}
