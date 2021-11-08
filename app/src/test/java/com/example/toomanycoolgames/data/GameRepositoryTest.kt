package com.example.toomanycoolgames.data

import com.example.toomanycoolgames.data.api.ApiResult
import com.example.toomanycoolgames.data.api.ApiWrapper
import com.example.toomanycoolgames.data.room.TMKGGameDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GameRepositoryTest {

    companion object {
        const val TEST_API_ID: Long = 999
    }

    private lateinit var repo: GameRepository
    private val gameDao: TMKGGameDao = mock()
    private val apiWrapper: ApiWrapper = mock()

    private val gameId = 1L
    private val game = createGameRelease(gameId, TEST_API_ID)

    @Before
    fun setUp() = runBlocking {
        prepareDbMock(gameDao)
        prepareApiMock(apiWrapper)

        // Initialize the database with some data
        repo = GameRepository(gameDao, apiWrapper, Dispatchers.Unconfined)
    }

    private suspend fun prepareApiMock(apiWrapper: ApiWrapper) {
        whenever(apiWrapper.getGameReleaseByApiId(TEST_API_ID)).thenReturn(ApiResult.Success(game))
//        whenever(apiWrapper.getGameReleasesBySearchQuery())
    }

    private suspend fun prepareDbMock(gameDao: TMKGGameDao) {
        whenever(gameDao.isInfoCached(TEST_API_ID)).thenReturn(false)
        whenever(gameDao.getAllTrackedGames()).thenReturn(flowOf(listOf(game)))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun allTrackedGames_getOneGame() = runBlocking {
        repo.getGameReleases(TEST_API_ID)
        repo.allTrackedGames.collect { games ->
            assertThat(games.size, equalTo(1))
            assertThat(games[0].releaseDates.size, equalTo(3))
        }
        return@runBlocking
    }

    // TODO make more meaningful?
    //  need to find some way to check the DB (which is mocked)
    @Test
    fun updateNotes() = runBlocking {
        repo.getGameReleases(TEST_API_ID)
        repo.updateNotes(TEST_API_ID, "New notes")
        return@runBlocking
    }
}