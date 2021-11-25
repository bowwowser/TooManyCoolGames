package com.example.toomanycoolgames.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.api.ApiWrapper
import com.example.toomanycoolgames.data.api.FakeApiWrapper
import com.example.toomanycoolgames.data.db.DBWrapper
import com.example.toomanycoolgames.data.db.FakeDBWrapper
import com.example.toomanycoolgames.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TMKGGameRepositoryTest {

    companion object {
        const val TEST_API_ID: Long = 1

        val FIVE_RELEASES =
            LongRange(1, 5)
                .associateWith { createGameRelease(gameId = it, apiId = it) }
                .toMutableMap()
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val game = createGameRelease(TEST_API_ID, TEST_API_ID)
    private lateinit var dbWrapper: DBWrapper
    private lateinit var apiWrapper: ApiWrapper

    private lateinit var repo: TMKGGameRepository

    @Before
    fun setUp() = runBlocking {
        dbWrapper = prepareDbFake()
        apiWrapper = prepareApiFake()

        repo = TMKGGameRepository(dbWrapper, apiWrapper, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun allTrackedGames_oneGameFetched() = runBlockingTest {
        repo.observeGameRelease(TEST_API_ID)
        val trackedGames = computeResult(repo.allTrackedGames.getOrAwaitValue()) ?: emptyList()
        assertThat(trackedGames.size, equalTo(1))
        assertThat(trackedGames[0].releaseDates.size, equalTo(3))
    }

    @Test
    fun allTrackedGames_gamesSearchedButNotCached() = runBlockingTest {
        repo.searchApiForGames("test")
        val trackedGames = computeResult(repo.allTrackedGames.getOrAwaitValue()) ?: emptyList()
        assertThat(trackedGames.size, equalTo(0))
    }

    private fun prepareApiFake(): ApiWrapper {
        return FakeApiWrapper(FIVE_RELEASES)
    }

    private fun prepareDbFake(): DBWrapper {
        return FakeDBWrapper(HashMap())
    }

    private suspend fun prepareApiMock(apiWrapper: ApiWrapper) {
        whenever(apiWrapper.getGameReleaseByApiId(TEST_API_ID)).thenReturn(Success(game))
        whenever(apiWrapper.getGameReleasesBySearchQuery(any())).thenReturn(Success(listOf(game)))
    }

    private suspend fun prepareDbMock(gameDao: DBWrapper) {
        whenever(gameDao.isInfoCached(TEST_API_ID)).thenReturn(false)
        whenever(gameDao.observeAllTrackedGames()).thenReturn(liveData { FIVE_RELEASES.values.toList() })
    }
}
