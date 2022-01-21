package com.tochukwu.moviepractice.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.repository.MovieRepository
import com.tochukwu.moviepractice.util.Constants
import com.tochukwu.moviepractice.util.Event
import com.tochukwu.moviepractice.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private val _movies = MutableLiveData<Event<Resource<List<Movie>>>>()
    val movies: LiveData<Event<Resource<List<Movie>>>> get() = _movies


    private val _moviesFromDb = MutableLiveData<List<Movie>>()
    val moviesFromDb: LiveData<List<Movie>> get() = _moviesFromDb





    fun getMovies(searchQuery: String){
        _movies.postValue(Event(Resource.loading(null)))
        Timber.d("getMovies...")
        if(searchQuery.isBlank()){
            _movies.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        if(searchQuery.length > Constants.MAX_MOVIE_TITLE_LENGTH){

            _movies.postValue(Event(Resource.error("The title can't be longer than ${Constants.MAX_MOVIE_TITLE_LENGTH}", null)))
            return
        }
        getMoviesFromNetwork(searchQuery)
    }

    private fun getMoviesFromNetwork(searchQuery: String) = viewModelScope.launch {
        movieRepository.getMovies(searchQuery).collectLatest{
            _movies.postValue(Event(it))
        }
    }


    fun returnAllMoviesFromDb() = viewModelScope.launch {
        movieRepository.returnAllMovies().collectLatest {
            _moviesFromDb.postValue(it)
        }
    }

    fun insertMovieList(movieList: List<Movie>) = viewModelScope.launch {
        movieRepository.insertMovieList(movieList = movieList)
    }

    fun insertMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.deleteMovie(movie)
    }

    fun deleteMovieList(movieList: List<Movie>) = viewModelScope.launch {
        movieRepository.deleteMovieList(movieList)
    }
}


/**

private val _movies = MutableLiveData<Event<Resource<List<Movie>>>>()
val movies: LiveData<Event<Resource<List<Movie>>>> get() = _movies

private val _moviesFromDb = MutableLiveData<List<Movie>>()
val moviesFromDb: LiveData<List<Movie>> get() = _moviesFromDb




fun returnAllMoviesFromDb() = viewModelScope.launch {
movieRepository.returnAllMovies().collect {
_moviesFromDb.postValue(it)
}
}

fun insertMovieList(movieList: List<Movie>) = viewModelScope.launch {
movieRepository.insertMovieList(movieList = movieList)
}

fun insertMovie(movie: Movie) = viewModelScope.launch {
movieRepository.insertMovie(movie)
}

fun deleteMovie(movie: Movie) = viewModelScope.launch {
movieRepository.deleteMovie(movie)
}

fun deleteMovieList(movieList: List<Movie>) = viewModelScope.launch {
movieRepository.deleteMovieList(movieList)
}

}   **/