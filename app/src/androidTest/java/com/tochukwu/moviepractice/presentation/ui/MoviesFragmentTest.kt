package com.tochukwu.moviepractice.presentation.ui



import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.*
import com.tochukwu.moviepractice.FakeMovieDataAndroidTest
import com.tochukwu.moviepractice.FakeMovieDataAndroidTest.movies
import com.tochukwu.moviepractice.R
import com.tochukwu.moviepractice.launchFragmentInHiltsContainer
import com.tochukwu.moviepractice.presentation.ToastMatcher
import com.tochukwu.moviepractice.presentation.movies.MovieFragment
import com.tochukwu.moviepractice.presentation.movies.MovieFragmentDirections
import com.tochukwu.moviepractice.presentation.movies.MovieViewModel
import com.tochukwu.moviepractice.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MoviesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddMovieButton_navigateToAddMovieFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltsContainer<MovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddMovie)).perform(click())

        verify(navController).navigate(
            MovieFragmentDirections.actionMovieFragmentToAddMovieFragment()
        )
    }

    @Test
    fun test_isRecyclerViewVisible_whenMoviesFragmentLaunches() {
        launchFragmentInHiltsContainer<MovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            movieListAdapter.submitList(movies)
        }
        onView(withId(R.id.rvMovieItems)).check(matches(isDisplayed()))
    }

    @Test
    fun swipeMovieItem_deleteMovieInDB() {
        var testMovieViewModel: MovieViewModel? = null
        launchFragmentInHiltsContainer<MovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testMovieViewModel = viewModel
            testMovieViewModel?.insertMovie(FakeMovieDataAndroidTest.movie)
        }

        onView(withId(R.id.rvMovieItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, swipeLeft()
            )
        )

        assertThat(testMovieViewModel?.moviesFromDb?.value).doesNotContain(FakeMovieDataAndroidTest.movie)

        onView(ViewMatchers.withText(Constants.DELETED_MOVIE_FROM_DB))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

}