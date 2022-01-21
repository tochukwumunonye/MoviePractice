package com.tochukwu.moviepractice.presentation.ui

import com.google.common.truth.Truth.assertThat
import androidx.fragment.app.testing.launchFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.tochukwu.moviepractice.FakeMovieDataAndroidTest
import com.tochukwu.moviepractice.FakeMovieDataAndroidTest.movies
import com.tochukwu.moviepractice.R
import com.tochukwu.moviepractice.launchFragmentInHiltsContainer
import com.tochukwu.moviepractice.presentation.ToastMatcher
import com.tochukwu.moviepractice.presentation.addMovies.AddMovieFragment
import com.tochukwu.moviepractice.presentation.clickRecyclerViewFabAction
import com.tochukwu.moviepractice.presentation.hasItemAtPosition
import com.tochukwu.moviepractice.presentation.isFabEnabled
import com.tochukwu.moviepractice.presentation.movies.MovieViewModel
import com.tochukwu.moviepractice.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddMovieFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltsContainer<AddMovieFragment> (
            fragmentFactory = testFragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()
        verify(navController).popBackStack()
        }


    @Test
    fun test_isRecyclerViewVisible_whenAddMovieFragmentLaunches(){
        launchFragmentInHiltsContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ){
            addMovieListAdapter.submitList(movies)
            //addMovieListAdapter. = FakeMovieDataAndroidTest.movies
        }
        onView(withId(R.id.searchedMoviesRv)).check(matches(isDisplayed()))
    }

    @Test
    fun pressAddMovieButton_insertMovieIntoDb(){

        var testMovieViewModel: MovieViewModel? = null
        launchFragmentInHiltsContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testMovieViewModel = viewModel
            addMovieListAdapter.submitList(movies)
            testMovieViewModel?.returnAllMoviesFromDb()
        }

        onView(withId(R.id.searchedMoviesRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, clickRecyclerViewFabAction(R.id.fabAddMovie)
            )
        )

        assertThat(testMovieViewModel?.moviesFromDb?.value)
            .contains(FakeMovieDataAndroidTest.movies[0])
    }

    @Test
    fun insertMovieIntoDB_doesElementMatchFromRecyclerView(){
        var testMovieViewModel: MovieViewModel? = null

        launchFragmentInHiltsContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ){
            testMovieViewModel = viewModel
            addMovieListAdapter.submitList(movies)
            testMovieViewModel?.returnAllMoviesFromDb()
        }

        onView(withId(R.id.searchedMoviesRv)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>
                    (0, clickRecyclerViewFabAction(R.id.fabAddMovie)))

        onView(withId(R.id.searchedMoviesRv)).check(matches(not((isFabEnabled(0)))))

        onView(withText(Constants.ADDED_MOVIE_TO_DB)).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        val moviesFromDb = testMovieViewModel?.moviesFromDb?.value

        onView(withId(R.id.searchedMoviesRv)).check(matches(hasItemAtPosition(0, hasDescendant(withText(moviesFromDb?.get(0)?.title)))))
        onView(withId(R.id.searchedMoviesRv)).check(matches(hasItemAtPosition(0, hasDescendant(withText(moviesFromDb?.get(0)?.year)))))
    }

    @Test
    fun clickSearchButtonWithEmptyEditText_isToastShown() {
        launchFragmentInHiltsContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        )

        onView(withId(R.id.etMovieTitle)).perform(typeText(""))

        onView(withId(R.id.btnSearchMovie)).perform(click())

        onView(withText(Constants.EMPTY_MOVIE_SEARCH))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }

}