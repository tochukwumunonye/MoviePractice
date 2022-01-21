package com.tochukwu.moviepractice.repository

import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepositoryAndroidTest  : MovieRepository {

    private val movies: MutableList<Movie> = mutableListOf()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertMovieList(movieList: List<Movie>) {
        for (movie in movieList) {
            movies.add(movie)
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        movies.add(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movies.remove(movie)
    }

    override suspend fun deleteMovieList(movieList: List<Movie>) = movies.clear()

    override fun returnAllMovies(): Flow<List<Movie>> = flow {
        emit(movies)
    }

    override fun getMovies(movieSearchQuery: String): Flow<Resource<List<Movie>>> = flow {
        if (shouldReturnNetworkError) {
            emit(Resource.error("Error", null))
        } else {
            emit(Resource.success(movies))
        }
    }
}
