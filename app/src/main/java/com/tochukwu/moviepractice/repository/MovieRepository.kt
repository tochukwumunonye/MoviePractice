package com.tochukwu.moviepractice.repository

import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(
        movieSearchQuery: String
    ): Flow<Resource<List<Movie>>>

    suspend fun insertMovieList(movieList: List<Movie>)

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    fun returnAllMovies(): Flow<List<Movie>>


    suspend fun deleteMovieList(movieList: List<Movie>)
}


/**

interface MovieRepository {

suspend fun insertMovieList(movieList: List<Movie>)

suspend fun insertMovie(movie: Movie)

suspend fun deleteMovie(movie: Movie)

suspend fun deleteMovieList(movieList: List<Movie>)

fun returnAllMovies(): Flow<List<Movie>>

     **/