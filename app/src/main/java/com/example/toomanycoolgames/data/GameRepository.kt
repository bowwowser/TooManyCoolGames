package com.example.toomanycoolgames.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.toomanycoolgames.data.api.ApiResult
import com.example.toomanycoolgames.data.api.IGDBApiWrapper
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.room.TMKGGameDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class TMKGResult<out R> {
    data class Success<out T>(val data: T) : TMKGResult<T>()
    data class Error(val exception: Exception) : TMKGResult<Nothing>()
}

class GameRepository @Inject constructor(
    private val tmkgGameDao: TMKGGameDao,
    private val apiWrapper: IGDBApiWrapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val allTrackedGames = tmkgGameDao.getAllTrackedGames()

    /**
     * Get search results for a query string.
     */
    suspend fun searchApiForGames(query: String): TMKGResult<List<TMKGGameRelease>> =
        withContext(dispatcher) {
            return@withContext when (val result = apiWrapper.getGameReleasesBySearchQuery(query)) {
                is ApiResult.Success -> TMKGResult.Success(result.data)
                is ApiResult.Error -> TMKGResult.Error(result.exception)
            }
        }

    suspend fun getGameReleases(apiId: Long): LiveData<TMKGGameRelease> {
        if (!tmkgGameDao.isInfoCached(apiId)) {
            cacheInfoFromApi(apiId)
        }
        return tmkgGameDao.getGameReleases(apiId)
    }

    /**
     * Fetch and cache game info using the API wrapper.
     *
     * @param apiId API game id
     */
    @WorkerThread
    private suspend fun cacheInfoFromApi(apiId: Long) = withContext(dispatcher) {
        val gameRelease = when (val result = apiWrapper.getGameReleaseByApiId(apiId)) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> throw result.exception
        }
        tmkgGameDao.cacheInfoAndReleaseDates(gameRelease.game, gameRelease.releaseDates)
    }

    /**
     * Changes the track status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun changeTrackStatus(gameId: Long, isNowTracked: Boolean) {
        tmkgGameDao.updateTrackStatus(gameId, isNowTracked)
    }

    /**
     * Changes the play status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun changePlayStatus(gameId: Long, playStatusPosition: Int) {
        tmkgGameDao.updatePlayStatus(gameId, playStatusPosition)
    }

    /**
     * Updates the notes for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun updateNotes(gameId: Long, notes: String) {
        tmkgGameDao.updateNotes(gameId, notes)
    }
}

// TODO reorganize
val isMainGame: (TMKGGameRelease) -> Boolean = { game -> game.game.category == 0 }