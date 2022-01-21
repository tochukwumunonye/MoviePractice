package com.tochukwu.moviepractice.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.tochukwu.moviepractice.presentation.addMovies.AddMovieFragment
import com.tochukwu.moviepractice.presentation.addMovies.AddMovieListAdapter
import com.tochukwu.moviepractice.presentation.movies.MovieAdapter
import com.tochukwu.moviepractice.presentation.movies.MovieFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val movieListAdapter: MovieAdapter,
    private val addMovieListAdapter: AddMovieListAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MovieFragment::class.java.name -> MovieFragment(movieListAdapter)
            AddMovieFragment::class.java.name -> AddMovieFragment(addMovieListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}