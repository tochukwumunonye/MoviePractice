package com.tochukwu.moviepractice.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.tochukwu.moviepractice.presentation.addMovies.AddMovieFragment
import com.tochukwu.moviepractice.presentation.addMovies.AddMovieListAdapter
import com.tochukwu.moviepractice.presentation.movies.MovieAdapter
import com.tochukwu.moviepractice.presentation.movies.MovieFragment
import com.tochukwu.moviepractice.presentation.movies.MovieViewModel
import com.tochukwu.moviepractice.repository.FakeMovieRepositoryAndroidTest
import javax.inject.Inject

class TestFragmentFactory @Inject constructor(
    private val addMovieListAdapter: AddMovieListAdapter,
    private val movieAdapter: MovieAdapter) : FragmentFactory(){


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){

            MovieFragment::class.java.name -> MovieFragment(
                movieAdapter, MovieViewModel(FakeMovieRepositoryAndroidTest())
            )
            AddMovieFragment::class.java.name -> AddMovieFragment(
                addMovieListAdapter, MovieViewModel(FakeMovieRepositoryAndroidTest())
            )

            else -> super.instantiate(classLoader, className)
        }
    }
}

