package com.example.toomanycoolgames.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.toomanycoolgames.data.Result.Error
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.api.ApiWrapper
import com.example.toomanycoolgames.data.db.DBWrapper
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMKGGameRepository @Inject constructor(
    private val dbWrapper: DBWrapper,
    private val apiWrapper: ApiWrapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameRepository {

    override val allTrackedGames = dbWrapper.observeAllTrackedGames()

    /**
     * Get search results for a query string.
     */
    override suspend fun searchApiForGames(query: String): Result<List<TMKGGameRelease>> =
        withContext(dispatcher) {
            return@withContext when (val result = apiWrapper.getGameReleasesBySearchQuery(query)) {
                is Success -> Success(result.data)
                is Error -> Error(result.exception)
                Result.Loading -> Result.Loading
            }
        }

    override suspend fun observeGameRelease(apiId: Long): LiveData<Result<TMKGGameRelease>> {
        if (!dbWrapper.isInfoCached(apiId)) {
            cacheInfoFromApi(apiId)
        }
        return dbWrapper.observeGameRelease(apiId)
    }

    /**
     * Fetch and cache game info using the API wrapper.
     *
     * @param apiId API game id
     */
    @WorkerThread
    private suspend fun cacheInfoFromApi(apiId: Long) = withContext(dispatcher) {
        val gameRelease = when (val result = apiWrapper.getGameReleaseByApiId(apiId)) {
            is Success -> result.data
            is Error -> throw result.exception
            Result.Loading -> return@withContext Result.Loading
        }
        dbWrapper.cacheInfoAndReleaseDates(gameRelease.game, gameRelease.releaseDates)
    }

    /**
     * Changes the track status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    override suspend fun changeTrackStatus(gameId: Long, isNowTracked: Boolean) {
        dbWrapper.updateTrackStatus(gameId, isNowTracked)
    }

    /**
     * Changes the play status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    override suspend fun changePlayStatus(gameId: Long, playStatusPosition: Int) {
        dbWrapper.updatePlayStatus(gameId, playStatusPosition)
    }

    /**
     * Updates the notes for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    override suspend fun updateNotes(gameId: Long, notes: String) {
        dbWrapper.updateNotes(gameId, notes)
    }
}

// TODO reorganize
val isMainGame: (TMKGGameRelease) -> Boolean = { game -> game.game.category == 0 }