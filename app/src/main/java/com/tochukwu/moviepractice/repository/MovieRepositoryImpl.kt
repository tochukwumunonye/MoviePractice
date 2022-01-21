package com.tochukwu.moviepractice.repository

import com.tochukwu.moviepractice.data.local.MoviesDao
import com.tochukwu.moviepractice.data.local.model.MovieEntityMapper
import com.tochukwu.moviepractice.data.remote.MovieAPI
import com.tochukwu.moviepractice.data.remote.model.MovieDtoMapper
import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi :MovieAPI,
    private val movieEntityMapper: MovieEntityMapper,
    private val movieDtoMapper: MovieDtoMapper,
    private val moviesDao: MoviesDao
) :MovieRepository{
    override fun getMovies(movieSearchQuery: String):
            Flow<Resource<List<com.tochukwu.moviepractice.domain.model.Movie>>> = flow {

        try{
            val response = movieApi.getMovies(movieSearchQuery)
            Timber.d("getMovies: ${response.body()}")
            if(response.isSuccessful){
                response.body()?.let{movieResultDto->
                    val movieResult =  movieResultDto.search?.let{
                        movieDtoMapper.fromDtoListToDomain(it)
                    }

                    emit(Resource.success(movieResult))

                } ?: emit(Resource.error("An unknown error occurred", null))
            }
        } catch(e: Exception){
            emit(Resource.error("Couldn't reach the server. Check internet connection", null))
        }
    }



    override suspend fun insertMovieList(movieList: List<Movie>) {
        moviesDao.insertMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
    }

    override suspend fun insertMovie(movie: Movie) {
        moviesDao.insertMovie(movieEntityMapper.mapFromDomainModel(movie))
    }

    override suspend fun deleteMovie(movie: Movie) {
        moviesDao.deleteMovie(movieEntityMapper.mapFromDomainModel(movie))
    }

    override suspend fun deleteMovieList(movieList: List<Movie>) {
        moviesDao.deleteMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
    }

    override fun returnAllMovies(): Flow<List<Movie>> {
        return moviesDao.returnAllMovies().map {
            movieEntityMapper.fromEntityListToDomain(it)
        }
    }

    private fun returnUnknownError(): Resource<Flow<List<Movie>>> {
        return Resource.error(
            "An unknown error occurred",
            null
        )
    }



}

/**






class MovieRepositoryImpl @Inject constructor(
private val moviesDao: MoviesDao,
private val movieApiService: MovieApiService,
private val movieEntityMapper: MovieEntityMapper,
private val movieDtoMapper: MovieDtoMapper
) : MovieRepository {

override suspend fun insertMovieList(movieList: List<Movie>) {
moviesDao.insertMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
}

override suspend fun insertMovie(movie: Movie) {
moviesDao.insertMovie(movieEntityMapper.mapFromDomainModel(movie))
}

override suspend fun deleteMovie(movie: Movie) {
moviesDao.deleteMovie(movieEntityMapper.mapFromDomainModel(movie))
}

override suspend fun deleteMovieList(movieList: List<Movie>) {
moviesDao.deleteMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
}

override fun returnAllMovies(): Flow<List<Movie>> {
return moviesDao.returnAllMovies().map {
movieEntityMapper.fromEntityListToDomain(it)
}
}

private fun returnUnknownError(): Resource<Flow<List<Movie>>> {
return Resource.error(
"An unknown error occurred",
null
)
}

}   **/