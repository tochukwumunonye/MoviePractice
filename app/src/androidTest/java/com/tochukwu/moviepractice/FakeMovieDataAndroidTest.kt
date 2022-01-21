package com.tochukwu.moviepractice

import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.repository.MovieRepository
import com.tochukwu.moviepractice.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeMovieDataAndroidTest {

    val movies = listOf(
        Movie(
            0,
            "Rush",
            "2013",
            "https://"
        ),
        Movie(
            1,
            "Cars",
            "2006",
            "https://"
        )
    )

    val movie = Movie(0, "Matrix", "1999", "https://")

}
