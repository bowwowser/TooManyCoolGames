package com.example.toomanycoolgames.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.example.toomanycoolgames.data.room.TMKGGame
import com.example.toomanycoolgames.data.room.TMKGGameDao
import com.example.toomanycoolgames.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject

sealed class TMKGResult<out R> {
    data class Success<out T>(val data: T) : TMKGResult<T>()
    data class Error(val exception: RequestException) : TMKGResult<Nothing>()
}

enum class Fields(val value: String) {
    ALL("*"),
    NAME("name"),
    COVER_IMAGE_ID("cover.image_id");

    companion object {
        val gameInfoFields get() = arrayOf(ALL.value, COVER_IMAGE_ID.value).joinToString()
        val gameSearchFields get() = arrayOf(NAME.value, COVER_IMAGE_ID.value).joinToString()
    }
}

class GameRepository @Inject constructor(
    private val tmkgGameDao: TMKGGameDao
) {

    /**
     * Initialize wrapper for IGDB API calls.
     */
    init {
        IGDBWrapper.setCredentials(IGDB_CLIENT_ID, IGDB_ACCESS_TOKEN)
    }

    val allTrackedGames = tmkgGameDao.getAllTrackedGames()

    /**
     * Get search results for a query string.
     */
    suspend fun searchIgdbForGames(query: String): TMKGResult<List<Game>> =
        withContext(Dispatchers.IO) {
            try {
                val searchResult: List<Game> = IGDBWrapper.games(gameSearchQuery(query))
                TMKGResult.Success(searchResult)
            } catch (e: RequestException) {
                logError(e) { "Error fetching games for query \"$query\": ${e.message}" }
                TMKGResult.Error(e)
            }
        }

    /**
     * Get cached game info for the requested IGDB game.
     *
     * @param igdbId IGDB game id
     */
    @WorkerThread
    suspend fun getGameInfo(igdbId: Long): LiveData<TMKGGame> {
        if (!tmkgGameDao.isGameInfoCached(igdbId)) {
            cacheIgdbGameInfo(igdbId)
        }
        return tmkgGameDao.getGameInfo(igdbId)
    }

    /**
     * Changes the track status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun changeGameTrackStatus(gameId: Long, isNowTracked: Boolean) {
        tmkgGameDao.updateGameTrackStatus(gameId, isNowTracked)
    }

    /**
     * Fetch and cache game info from IGDB.
     *
     * @param igdbId IGDB game id
     */
    @WorkerThread
    suspend fun cacheIgdbGameInfo(igdbId: Long) = withContext(Dispatchers.IO) {
        val searchResult: List<Game> = try {
            IGDBWrapper.games(gameInfoQuery(igdbId))
        } catch (e: RequestException) {
            logError(e) { "Error caching IGDB game [$igdbId]: ${e.message}" }
            throw e
        }

        searchResult.first { game ->
            val tmkgGame = TMKGGame(
                id = 0,
                isTracked = false,
                game.id,
                game.name,
                game.cover.imageId,
                game.summary
            )
            tmkgGameDao.cacheGameInfo(tmkgGame)
            return@withContext
        }
    }

    companion object {
        private const val IGDB_CLIENT_ID = "j3fqgr3dxzsnzzwrzd5um01o63k9gi"
        private const val IGDB_ACCESS_TOKEN = "hue5mbidjtu1ygfchevoaav8ruubwx"

        fun gameInfoQuery(id: Long): APICalypse {
            return APICalypse().fields(Fields.gameInfoFields).where("id = $id")
        }

        fun gameSearchQuery(query: String): APICalypse {
            return APICalypse().fields(Fields.gameSearchFields).search(query)
        }
    }
}