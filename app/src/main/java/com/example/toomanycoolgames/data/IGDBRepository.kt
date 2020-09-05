package com.example.toomanycoolgames.data

import APICalypse
import IGDBWrapper
import RequestException
import com.example.toomanycoolgames.logError
import games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Game

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
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

class IGDBRepository {
    init {
        IGDBWrapper.userkey = IGDB_KEY
    }

    /**
     * Get search results for a query string.
     */
    suspend fun getSearchResults(query: String): Result<List<Game>> = withContext(Dispatchers.IO) {
        try {
            val searchResult: List<Game> = IGDBWrapper.games(gameSearchQuery(query))
            Result.Success(searchResult)
        } catch (e: RequestException) {
            logError(e) { "Error fetching games for query \"$query\": ${e.message}" }
            Result.Error(e)
        }
    }

    /**
     * Get game info for a given game id.
     */
    suspend fun getGameInfo(id: Long): Result<Game> = withContext(Dispatchers.IO) {
        try {
            val searchResult: List<Game> = IGDBWrapper.games(gameInfoQuery(id))
            Result.Success(searchResult[0])
        } catch (e: RequestException) {
            logError(e) { "Error fetching game $id: ${e.message}" }
            Result.Error(e)
        }
    }

    companion object {
        private const val IGDB_KEY = "7e6eee9293a968a5273b626d3c4b65ea"

        fun gameInfoQuery(id: Long): APICalypse {
            return APICalypse().fields(Fields.gameInfoFields).where("id = $id")
        }

        fun gameSearchQuery(query: String): APICalypse {
            return APICalypse().fields(Fields.gameSearchFields).search(query)
        }
    }
}