package com.example.toomanycoolgames.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.api.NoOpApiWrapper
import com.example.toomanycoolgames.data.db.FakeDBWrapper
import com.example.toomanycoolgames.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Based on Android codelab:
 * https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-basics
 */
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val gameRepository = TMKGGameRepository(
        dbWrapper = FakeDBWrapper(hashMapOf()),
        apiWrapper = NoOpApiWrapper()
    )

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setupViewModel() {
        homeViewModel = HomeViewModel(gameRepository)
    }

    @Suppress("UNUSED_VARIABLE")
    @Test
    fun trackedGames_getAllTrackedGamesFromRepo() {

        // When getting all tracked games from the repo
        val games = homeViewModel.trackedGames.getOrAwaitValue()

        // Then return a LiveData list of games for access
        assertThat(games.size, `is`(0))
    }
}
