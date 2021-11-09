package com.example.toomanycoolgames.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.api.ApiResult
import com.example.toomanycoolgames.data.api.ApiWrapper
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.room.TMKGDatabase
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

    val gameRepository = GameRepository(
        tmkgGameDao = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TMKGDatabase::class.java
        ).build().gameDao(),
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

class NoOpApiWrapper : ApiWrapper {
    override suspend fun getGameReleaseByApiId(apiId: Long): ApiResult<TMKGGameRelease> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameReleasesBySearchQuery(query: String): ApiResult<List<TMKGGameRelease>> {
        TODO("Not yet implemented")
    }

}